import React from "react";
import "./MainComponent.css";
import {
  initializeFileTypeIcons,
  getFileTypeIconProps,
} from "@fluentui/react-file-type-icons";


import app_cofig from '../config/Config.js';


import axios from "axios";
import { Icon as Ficon } from "@fluentui/react";
import moment from "moment";
import {
  Layout,
  Nav,
  Button,
  Skeleton,
  Avatar,
  List,
  AutoComplete,
} from "@douyinfe/semi-ui";
import {
  IconSemiLogo,
  IconBytedanceLogo,
} from "@douyinfe/semi-icons";

import ContentSiderComponent from './content_sider/ContentSiderComponent.js';

initializeFileTypeIcons();

class MainComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      villageLoadingFlag: true,
      villageTypeList: [],
      currentUser: {
        name: "YD",
      },
      loadingFlag: false,
      headColumns: [],
      renderItems: [
        {
          fileName: "A.xlsx",
          fileType: "xlsx",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.txt",
          fileType: "txt",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.docx",
          fileType: "docx",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.doc",
          fileType: "doc",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.doc",
          fileType: "doc",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.doc",
          fileType: "doc",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
        {
          fileName: "A.xls",
          fileType: "xls",
          upLoadedBy: "YD",
          uploadTime: moment().format("YYYY-MM-DD HH:mm:ss"),
        },
      ],
    };
  }

  componentDidMount() {
    console.log("when this components mounted");
    this.sayHello();
  }
  sayHello() {
    console.log("hello");
  }

  randomFileType() {
    let ft = app_cofig.fileTypes;
    return ft[Math.floor(Math.random() * ft)].name;
  }
  whenSelectionChanged = () => {
    console.log("change");
  };

  render() {
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


          <ContentSiderComponent
            villageTypeList={state.villageTypeList}
          />

          <Content className="main-content">
            <div className="search-box">
              <AutoComplete placeholder="输入文件名" size="large" />
            </div>

            <Skeleton
              placeholder={<Skeleton.Paragraph rows={6} />}
              loading={state.loadingFlag}
              active
            >
              <div className="list-item-container">
                <List
                  className="item-list"
                  grid={{
                    gutter: 12,
                    span: 6,
                  }}
                  dataSource={state.renderItems}
                  renderItem={(item) => (
                    <List.Item className="list-item-single">
                      <div className="single_item">
                        <Ficon
                          {...getFileTypeIconProps({
                            extension: `${item.fileType}`,
                            size: 96,
                            imageFileType: "png",
                          })}
                        />
                        <div className="item-style">
                          <div>文件名</div>
                          <span>{item.fileName}</span>
                        </div>

                        <div className="item-style">
                          <div>文件类型</div>
                          <span>{item.fileType}</span>
                        </div>

                        <div className="item-style">
                          <div>上传用户</div>
                          <span>{item.upLoadedBy}</span>
                        </div>

                        <div className="item-style">
                          <div>上传时间</div>
                          <span>{item.uploadTime}</span>
                        </div>

                        <div className="item-style">
                          <div>Action:</div>
                          <div className="item-action-button-style">
                            <Button>检视</Button>
                            <Button>下载</Button>
                          </div>
                        </div>

                      </div>
                    </List.Item>
                  )}
                ></List>
              </div>
            </Skeleton>
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

export default MainComponent;
