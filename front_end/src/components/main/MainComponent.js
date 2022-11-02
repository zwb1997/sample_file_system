import React from "react";
import "./MainComponent.css";
import axios from "axios";
import {
  initializeFileTypeIcons,
  getFileTypeIconProps
} from "@fluentui/react-file-type-icons";
import app_cofig from '../../config/Config.js';
import moment from "moment";
import { filesize } from "filesize";
import {
  Layout,
  Nav,
  Avatar,
  AutoComplete,
  Table,
  Descriptions,
  Tag,
  Button
} from "@douyinfe/semi-ui";
import {
  IconSemiLogo,
  IconBytedanceLogo,
  IconMore
} from "@douyinfe/semi-icons";
import { Icon as Ficon } from "@fluentui/react";
import ContentSiderComponent from './content_sider/ContentSiderComponent.js';
import LoadFileComponment from './upload_component/LoadFileComponment.js';

initializeFileTypeIcons();
const DISABLE_FILE = "DISABLE";
const UPDATE_FILE = "UPDATE";
const DEFAULT_FICON_IMAGE_TYPE = "png";
export default class MainComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      villageLoadingFlag: true,
      currentVillageTypeKey: "",
      currentUser: {
        name: "YD",
      },
      loadingFlag: false,
      headColumns: [],
      renderItems: [
        {
          fileName: "A.png",
          fileType: "png",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.txt",
          fileType: "txt",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.docx",
          fileType: "docx",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.doc",
          fileType: "doc",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.xlsx",
          fileType: "xlsx",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.doc",
          fileType: "doc",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.docx",
          fileType: "docx",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
        {
          fileName: "A.pdf",
          fileType: "pdf",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
          viliage_type: "",
        },
      ],

      tableLoadingFlag: true,
      tableColumns: [
        {
          align: "center",
          title: "文件名",
          // width: 500,
          dataIndex: "fileName",
          render: (text, record, index) => {
            return (
              <div className="table-icon-column">
                <Ficon
                  className="table-icon-column-img"
                  {...getFileTypeIconProps({
                    extension: `${record.fileType}`,
                    size: 40,
                    imageFileType: DEFAULT_FICON_IMAGE_TYPE,
                  })}
                />
                <div className="table-icon-column-text">
                  <span>{record.fileName}</span>
                </div>
              </div>
            );
          },
        },
        {
          title: "文件大小",
          dataIndex: "fileSize",
          align: "center",
        },
        {
          title: "上传者",
          dataIndex: "fileUpdater",
          align: "center",
        },
        {
          title: "更新时间",
          dataIndex: "currentUpdatetime",
          align: "center",
        },
        {
          title: "操作",
          render: (text, record, index) => {
            return (
              <div>
                <Button
                  theme="solid"
                  type="tertiary"
                  style={{ marginRight: 8 }}
                  onClick={this.updateCurrentFile(
                    UPDATE_FILE,
                    text,
                    record,
                    index
                  )}
                >
                  更新
                </Button>
                <Button
                  theme="solid"
                  type="tertiary"
                  style={{ marginRight: 8 }}
                  onClick={this.updateCurrentFile(
                    DISABLE_FILE,
                    text,
                    record,
                    index
                  )}
                >
                  删除
                </Button>
              </div>
            );
          },
          align: "center",
        },
      ],
      rowSelection: {
        onSelect: (record, selected) => {
          console.log(`select row: ${selected}`, record);
        },
        onSelectAll: (selected, selectedRows) => {
          console.log(`select all rows: ${selected}`, selectedRows);
        },
        onChange: (selectedRowKeys, selectedRows) => {
          console.log(
            `selectedRowKeys: ${selectedRowKeys}`,
            "selectedRows: ",
            selectedRows
          );
        },
      },
      requestParams: {
        villageType: '',
        pageNum: 1,
        pageSize: 5,
      },
      responseData: {
        pageNum: 1,
        pageSize: 10,
        totalCount: 0,
        data: [],
      },
    };
  }

  componentDidUpdate() {

  }
  componentDidMount() {
    this.fetchCurrentVillageTypeData();
  }

  setCurrentVillageType = (villageType) => {
    console.log("village type change?", villageType);
    try {
      if (!villageType) {
        throw new Error('current village type is empty');
      }
      this.setState({
        currentVillageTypeKey: villageType,
        tableLoadingFlag: true
      }, () => {
        console.log(`current village type change! ->[${this.state.currentVillageTypeKey}]`);
        this.fetchCurrentVillageTypeData();
      })
    } catch (error) {
      console.error(error);
    }
  }

  randomFileType() {
    let ft = app_cofig.fileTypes;
    return ft[Math.floor(Math.random() * ft)].name;
  }
  whenSelectionChanged = () => {
    console.log("change");
  };

  renderUpLoadFileComponent = (currentVillageTypeKey) => {
    return <LoadFileComponment currentVillageTypeKey={currentVillageTypeKey} />
  }

  renderContentSiderComponent = (currentVillageTypeKey) => {

    return <ContentSiderComponent
      villageTypeList={currentVillageTypeKey}
      setCurrentVillageType={this.setCurrentVillageType}
    />
  }


  onPageChange = (currentPageNum) => {
    const { requestParams } = this.state;

    let newRequestParams = {
      villageType: requestParams.villageType,
      pageNum: currentPageNum,
      pageSize: requestParams.pageSize,
    };
    this.setState(
      {
        requestParams: newRequestParams,
        tableLoadingFlag: true,
      },
      () => {
        this.fetchCurrentVillageTypeData();
      }
    );
  };
  fetchCurrentVillageTypeData = () => {
    const { responseData, requestParams, currentVillageTypeKey } = this.state;

    // do get
    axios
      .get(app_cofig.fileListRequestUrl, {
        method: "GET",
        params: {
          villageKey: currentVillageTypeKey,
          pageNum: requestParams.pageNum,
          pageSize: requestParams.pageSize,
        },
      })
      .then((res) => {
        console.log("fetch file list", res);
        let resData = res.data.data;
        if (resData) {
          responseData.data = resData.list;
          responseData.data = responseData.data
            ? responseData.data.map((model) => {
              let fileStrings = model.fileName
                ? model.fileName.split(".")
                : ["未命名", ".txt"];
              let fName = fileStrings[0];
              let fType = fileStrings[1];
              return {
                dataId: model.uuid,
                fileName: fName,
                fileType: fType,
                fileOriginalType: model.fileType,
                fileSize: filesize(model.fileSize),
                fileUpdater: model.createCustomer,
                currentUpdatetime: model.createTime,
                villageKey: model.villageType,
                fileLocationPath: model.filePath,
              };
            })
            : [];
          responseData.pageNum = resData.pageNum;
          responseData.pageSize = resData.pageSize;
          responseData.totalCount = resData.total;
        }
      })
      .catch((error) => {
        console.error("fetch file list failed", error);
      })
      .finally(() => {
        this.setState({
          tableLoadingFlag: false,
        });
      });
  };

  updateCurrentFile = (actiontype, text, record, index) => {
    if (!actiontype) {
      throw new Error("action type is empty");
    }
    switch (actiontype) {
      case DISABLE_FILE:
        break;
      case UPDATE_FILE:
        break;
      default:
        break;
    }
  };

  render() {
    const
      { currentVillageTypeKey, tableColumns, responseData, tableLoadingFlag } = this.state;
    let state = this.state;
    const { Header, Footer, Content } = Layout;
    return (
      // 容器整体
      <Layout className="welcome-container">
        {/* 头部 */}
        <Header style={{ backgroundColor: "var(--semi-color-bg-1)" }}>
          <div>
            <Nav mode="horizontal" defaultSelectedKeys={[]}>
              <Nav.Header>
                <IconSemiLogo style={{ fontSize: 36 }} />
              </Nav.Header>
              <span className="span_1">
                <span className="span_2">村工作文件管理系统</span>
              </span>
              <Nav.Footer>
                <Avatar color="orange" size="small">
                  {state.currentUser.name}
                </Avatar>
              </Nav.Footer>
            </Nav>
          </div>
        </Header>

        {/* 内容区域 */}
        <Layout className="main-layout">

          {/*  ContentSiderComponent */}
          {this.renderContentSiderComponent()}

          <Content className="main-content">

            {this.renderUpLoadFileComponent(state.currentVillageTypeKey)}
            <div className="search-box">
              <AutoComplete placeholder="输入文件名" size="large" />
            </div>

            {/* 文件信息列表 */}
            {/* FileListComponent */}

            <Table
              loading={tableLoadingFlag}
              bordered={true}
              rowKey="name"
              columns={tableColumns}
              dataSource={responseData.data}
              rowSelection={this.rowSelection}
              scroll={{ y: "580px" }}
              pagination={{
                currentPage: responseData.pageNum,
                pageSize: responseData.pageSize,
                position: "top",
                total: responseData.totalCount,
                showTotal: true,
                onPageChange: this.onPageChange,
              }}
            />

          </Content>
        </Layout>

        {/* 尾部 */}
        <Footer className="footer-style">
          <span
            style={{
              display: "flex",
              alignItems: "center",
            }}
          >
            <IconBytedanceLogo size="large" style={{ marginRight: "8px" }} />
            <span>{app_cofig.footerContent}</span>
          </span>
        </Footer>
      </Layout >
    );
  }
}
