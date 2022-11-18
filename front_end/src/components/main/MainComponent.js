import React from "react";
import "./MainComponent.css";
import axios from "axios";

import path from "path-browserify";
import {
  initializeFileTypeIcons,
  getFileTypeIconProps,
} from "@fluentui/react-file-type-icons";
import app_cofig from "../../config/Config.js";
import moment from "moment";
import { getFileType } from "../../utils/AppUtils.js";
import { filesize } from "filesize";
import FileViewer from "react-file-viewer";
import {
  Layout,
  Nav,
  Avatar,
  AutoComplete,
  Table,
  Modal,
  Button,
  Toast,
  Spin,
  Tooltip,
  Empty,
  Popconfirm,
  Divider
} from "@douyinfe/semi-ui";
import {
  IllustrationConstruction,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";

import {
  IconSemiLogo,
  IconBytedanceLogo,
  IconMore,
  IconSearch,
} from "@douyinfe/semi-icons";
import { Icon as Ficon } from "@fluentui/react";
import ContentSiderComponent from "./content_sider/ContentSiderComponent.js";
import LoadFileComponment from "./upload_component/LoadFileComponment.js";

initializeFileTypeIcons();
const DISABLE_FILE = "DISABLE";
const DOWNLOAD_FILE = "DOWNLOAD";
const VIEW_FILE = "VIEW";
const DEFAULT_FICON_IMAGE_TYPE = "png";
export default class MainComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      spinObj: {
        isLoading: false,
        content: "loading...",
      },
      currentVillageTypeKey: "",
      currentUser: {
        name: "YD",
      },
      viewModel: {
        documentFlag: false,
        fileType: "",
        fileUrl: "",
      },
      autoModel: {
        isLoading: false,
        currentVillageKey: "",
        searchData: [],
        searchValue: "",
      },
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
                  <Tooltip content={record.fileName}>{record.fileName}</Tooltip>
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
                {/* <Button
                  theme="solid"
                  type="tertiary"
                  style={{ marginRight: 8 }}
                  onClick={() => {
                    this.currentFileAction(VIEW_FILE, text, record, index);
                  }}
                >
                  查看
                </Button> */}
                <Button
                  theme="solid"
                  type="tertiary"
                  style={{ marginRight: 8 }}
                  onClick={() =>
                    this.currentFileAction(DOWNLOAD_FILE, text, record, index)
                  }
                >
                  下载
                </Button>
                <Popconfirm
                  onConfirm={() => {
                    this.currentFileAction(DISABLE_FILE, text, record, index);
                  }}
                  title="确定是否要保存此修改？"
                  content="此操作将删除源文件"
                  onCancel={() => {
                    Toast.info("取消删除");
                  }}
                >
                  <Button
                    theme="solid"
                    type="tertiary"
                    style={{ marginRight: 8 }}
                  >
                    删除
                  </Button>
                </Popconfirm>
              </div>
            );
          },
          align: "center",
        },
      ],
      rowSelection: {
        onSelect: (record, selected) => {
          // console.log(`select row: ${selected}`, record);
        },
        onSelectAll: (selected, selectedRows) => {
          // console.log(`select all rows: ${selected}`, selectedRows);
        },
        onChange: (selectedRowKeys, selectedRows) => {
          // console.log(
          //   `selectedRowKeys: ${selectedRowKeys}`,
          //   "selectedRows: ",
          //   selectedRows
          // );
        },
      },
      tableRequestParams: {
        fileName: "",
        villageType: "",
        pageNum: 1,
        pageSize: 5,
      },
      responseTableData: {
        pageNum: 1,
        pageSize: 10,
        totalCount: 0,
        data: [],
      },
      timeout: undefined,
    };
  }

  componentDidUpdate() {}
  componentDidMount() {
    this.fetchCurrentVillageTypeData();
  }

  setCurrentVillageType = (villageType) => {
    console.log("village type change?", villageType);
    try {
      if (!villageType) {
        throw new Error("current village type is empty");
      }
      this.setState(
        {
          currentVillageTypeKey: villageType,
          tableLoadingFlag: true,
        },
        () => {
          // console.log(
          //   `current village type change! ->[${this.state.currentVillageTypeKey}]`
          // );
          this.fetchCurrentVillageTypeData();
        }
      );
    } catch (error) {
      console.error(error);
    }
  };

  randomFileType() {
    let ft = app_cofig.fileTypes;
    return ft[Math.floor(Math.random() * ft)].name;
  }
  whenSelectionChanged = () => {
    // console.log("change");
  };

  renderUpLoadFileComponent = (currentVillageTypeKey) => {
    const { currentUser } = this.state;
    return (
      <LoadFileComponment
        currentVillageTypeKey={currentVillageTypeKey}
        reloadFileList={this.reloadFileList}
        customerName={currentUser.name}
      />
    );
  };

  reloadFileList = () => {
    const { spinObj } = this.state;
    this.setState({
      tableLoadingFlag: true,
      spinObj: {
        isLoading: true,
        content: spinObj.content,
      },
    });
    this.fetchCurrentVillageTypeData();
  };

  renderContentSiderComponent = (currentVillageTypeKey) => {
    return (
      <ContentSiderComponent
        villageTypeList={currentVillageTypeKey}
        setCurrentVillageType={this.setCurrentVillageType}
      />
    );
  };

  onPageChange = (currentPageNum) => {
    const { tableRequestParams } = this.state;

    let newRequestParams = {
      fileName: "",
      villageType: tableRequestParams.villageType,
      pageNum: currentPageNum,
      pageSize: tableRequestParams.pageSize,
    };
    this.setState(
      {
        tableRequestParams: newRequestParams,
        tableLoadingFlag: true,
      },
      () => {
        this.fetchCurrentVillageTypeData();
      }
    );
  };
  fetchCurrentVillageTypeData = () => {
    const {
      responseTableData,
      tableRequestParams,
      currentVillageTypeKey,
      spinObj,
    } = this.state;

    // do get
    let cSearchData = [];
    axios
      .get(app_cofig.fileListRequestUrl, {
        method: "GET",
        params: {
          fileName: tableRequestParams.fileName,
          villageKey: currentVillageTypeKey,
          pageNum: tableRequestParams.pageNum,
          pageSize: tableRequestParams.pageSize,
        },
      })
      .then((res) => {
        // console.log("fetch file list", res);
        let resData = res.data.data;
        if (resData) {
          responseTableData.data = resData.list;
          responseTableData.data = responseTableData.data
            ? responseTableData.data.map((model) => {
                let fileStrings = model.fileName
                  ? model.fileName.split(".")
                  : ["未命名", ".txt"];
                let length = fileStrings.length;
                let fName = fileStrings[0];
                let fType = fileStrings[length - 1];
                let cFileName = `${fName}.${fType}`;
                cSearchData.push(cFileName);
                return {
                  dataID: model.uuid,
                  fileName: cFileName,
                  fileType: fType,
                  fileOriginalType: model.fileType,
                  fileSize: filesize(model.fileSize),
                  fileUpdater: model.createCustomer,
                  currentUpdatetime: model.createTime,
                  villageKey: model.villageType,
                  villageName: model.villageName,
                  fileLocationPath: model.filePath,
                };
              })
            : [];
          responseTableData.pageNum = resData.pageNum;
          responseTableData.pageSize = resData.pageSize;
          responseTableData.totalCount = resData.total;
        }
      })
      .catch((error) => {
        console.error("fetch file list failed", error);
      })
      .finally(() => {
        this.setState({
          autoModel: {
            isLoading: false,
            searchData: cSearchData,
          },
          spinObj: {
            isLoading: false,
            content: spinObj.content,
          },
          tableRequestParams: {
            fileName: "",
            villageType: tableRequestParams.villageType,
            pageNum: tableRequestParams.pageNum,
            pageSize: tableRequestParams.pageSize,
          },
          tableLoadingFlag: false,
        });
      });
  };

  currentFileAction = (actiontype, text, record, index) => {
    const { viewModel } = this.state;
    if (!actiontype) {
      throw new Error("action type is empty");
    }
    switch (actiontype) {
      case VIEW_FILE:
        this.buildFileView(VIEW_FILE, text, record, index);
        break;
      case DISABLE_FILE:
        this.disableCurrentFile(text, record, index);
        break;
      case DOWNLOAD_FILE:
        this.buildFileView(DOWNLOAD_FILE, text, record, index);
        break;
      default:
        break;
    }
  };

  disableCurrentFile = (text, record, index) => {
    const { spinObj } = this.state;
    // console.log("remove file", record);
    if (!record.villageKey) {
      throw new Error("when try to disable column, villageKey is empty!");
    }
    this.setState({
      tableLoadingFlag: true,
      spinObj: {
        isLoading: true,
        content: spinObj.content,
      },
    });
    axios
      .get(app_cofig.disableFilesRequestUrl, {
        method: "GET",
        params: {
          fileKey: record.dataID,
          villageType: record.villageKey,
        },
      })
      .then((data) => {
        let resData = data.data;
        if (resData.code === 1) {
          Toast.success(`删除文件\t${record.fileName}\t成功`);
        }
      })
      .catch((error) => {
        Toast.error(`删除文件\t${record.fileName}\t失败`);
        console.error("删除文件失败", error);
      })
      .finally(() => {
        this.setState({
          tableLoadingFlag: false,
          spinObj: {
            isLoading: false,
            content: spinObj.content,
          },
        });
        this.fetchCurrentVillageTypeData();
      });
  };
  buildFileView = (actiontype, text, record, index) => {
    const { spinObj } = this.state;
    let fileKey = record.dataID ? record.dataID : "";
    this.setState(
      {
        tableLoadingFlag: true,
        spinObj: {
          isLoading: true,
          content: spinObj.content,
        },
      },
      () => {
        if (actiontype === DOWNLOAD_FILE) {
          Toast.info(`正在下载文件\t${record.fileName}\t`);
          this.downloadAction(actiontype, fileKey, record);
        }
      }
    );
  };

  downloadAction = (actiontype, fileKey, record) => {
    const { spinObj } = this.state;
    axios({
      method: "POST",
      url:
        app_cofig.renderCurrentFileRequestUrl +
        `?fileKey=${fileKey}&villageType=${record.villageKey}`,
      responseType: "blob",
    })
      .then((res) => {
        const content = res.data;
        const downloadUrl = URL.createObjectURL(content);
        const link = document.createElement("a");
        link.download = `${record.villageName}-${record.fileName}.${record.fileType}`;
        link.href = downloadUrl;
        link.click();
        URL.revokeObjectURL(downloadUrl);
        Toast.success(`下载文件\t${record.fileName}\t成功`);
      })
      .catch((err) => {
        Toast.error(`下载文件\t${record.fileName}\t失败`);
        console.error(
          `when try to download file\t${record.fileName}\t failed`,
          err
        );
      })
      .finally(() => {
        this.setState({
          tableLoadingFlag: false,
          spinObj: {
            isLoading: false,
            content: spinObj.content,
          },
        });
      });
  };

  closeView = () => {
    const { viewModel } = this.state;
    this.setState({
      viewModel: {
        fileType: "",
        fileUrl: "",
        error: viewModel.error,
        documentFlag: false,
      },
    });
  };

  checkRenderFileType = (viewModel) => {
    return viewModel.fileType === "et" ? "xls" : viewModel.fileType;
  };

  autoFileNameDetect = (curInputStr) => {
    const { spinObj, tableLoadingFlag, autoModel, tableRequestParams } =
      this.state;

    this.setState({
      autoModel: {
        searchValue: curInputStr,
      },
      tableRequestParams: {
        fileName: curInputStr,
        villageType: tableRequestParams.villageType,
        pageNum: 1,
        pageSize: tableRequestParams.pageSize,
      },
    });
  };

  reloadingFileList = () => {
    this.setState(
      {
        spinObj: {
          isLoading: true,
        },
        tableLoadingFlag: true,
        autoModel: {
          isLoading: true,
        },
      },
      () => {
        this.fetchCurrentVillageTypeData();
      }
    );
  };

  autoCompleteRender = (item) => {
    let style1 = {
      width:'100%',
      padding:'5px 10px 5px 10px'
    }
    return (
      <div style={style1}>
        <div>{item}</div>
        <Divider />
      </div>
    );
  };

  render() {
    const {
      tableColumns,
      responseTableData,
      tableLoadingFlag,
      viewModel,
      rowSelection,
      autoModel,
      spinObj,
    } = this.state;
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

        <Modal
          title="文件浏览"
          fullScreen
          visible={viewModel.documentFlag}
          onOk={this.closeView}
          onCancel={this.closeView}
          className="custom-modal-body"
        >
          <div className="file-view-container">
            <FileViewer
              disable={false}
              fileType={this.checkRenderFileType(viewModel)}
              filePath={viewModel.fileUrl}
              errorComponent={<div>渲染失败</div>}
              onError={(e) => {
                console.error(e);
              }}
              unsupportedComponent={() => {
                return (
                  <Empty
                    image={<IllustrationConstruction />}
                    darkModeImage={<IllustrationConstructionDark />}
                    title={"不支持的预览类型"}
                    description="暂不支持本类型文档预览"
                  />
                );
              }}
            />
          </div>
        </Modal>

        {/* 内容区域 */}
        <Layout className="main-layout">
          {/*  ContentSiderComponent */}
          <Spin
            style={{ height: "100%" }}
            tip={spinObj.content}
            spinning={spinObj.isLoading}
          >
            {this.renderContentSiderComponent()}
          </Spin>
          <Content className="main-content">
            {this.renderUpLoadFileComponent(state.currentVillageTypeKey)}
            <div className="search-box">
              <AutoComplete
                style={{ width: "360px" }}
                showClear={true}
                disabled={autoModel.isLoading}
                loading={autoModel.isLoading}
                data={autoModel.searchData}
                value={autoModel.searchValue}
                onChange={this.autoFileNameDetect}
                prefix={<IconSearch onClick={this.reloadingFileList} />}
                placeholder="输入后点击左侧搜索... "
                size="default"
                position="bottomLeft"
                renderItem={this.autoCompleteRender}
              />
            </div>

            {/* 文件信息列表 */}
            {/* FileListComponent */}

            <Table
              rowKey="dataID"
              loading={tableLoadingFlag}
              bordered={true}
              columns={tableColumns}
              dataSource={responseTableData.data}
              rowSelection={rowSelection}
              scroll={{ y: "580px" }}
              pagination={{
                currentPage: responseTableData.pageNum,
                pageSize: responseTableData.pageSize,
                position: "top",
                total: responseTableData.totalCount,
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
      </Layout>
    );
  }
}
