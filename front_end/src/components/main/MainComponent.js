import React from "react";
import "./MainComponent.css";
import {
  initializeFileTypeIcons,
  getFileTypeIconProps,
  FileIconType,
} from "@fluentui/react-file-type-icons";
import { Icon as Ficon } from "@fluentui/react";
import moment from "moment";
import {
  Layout,
  Nav,
  Button,
  Skeleton,
  Avatar,
  List,
  Descriptions,
  Rating,
  ButtonGroup,
  Divider,
  AutoComplete,
} from "@douyinfe/semi-ui";
import {
  IconSemiLogo,
  IconBytedanceLogo,
  IconHome,
  IconSearch,
} from "@douyinfe/semi-icons";

const FileTypes = [
  { name: "xlsx" },
  { name: "xls" },
  { name: "csv" },
  { name: "doc" },
  { name: "docx" },
  { name: "pdf" },
];

initializeFileTypeIcons();

class MainComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      itemFileLists: [
        {
          itemKey: "西水泉村",
          text: "西水泉村",
          icon: <IconHome size="large" />,
        },
        {
          itemKey: "xx村1",
          text: "xx村1",
          icon: <IconHome size="large" />,
        },
        {
          itemKey: "xx村2",
          text: "xx村2",
          icon: <IconHome size="large" />,
        },
        {
          itemKey: "xx村3",
          text: "xx村3",
          icon: <IconHome size="large" />,
        },
      ],
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
    return FileTypes[Math.floor(Math.random() * FileTypes.length)].name;
  }
  whenSelectionChanged = () => {
    console.log("change");
  };

  render() {
    let state = this.state;
    const { Header, Footer, Sider, Content } = Layout;

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

              <div>something else</div>

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
          <Sider style={{ backgroundColor: "var(--semi-color-bg-1)" }}>
            <Nav
              style={{ maxWidth: 220, height: "100%" }}
              defaultSelectedKeys={[]}
              items={state.itemFileLists}
              footer={{
                collapseButton: true,
              }}
            />
          </Sider>
          <Content className="main-content">
            <div className="search-box">
              <AutoComplete
              placeholder="输入文件名"
              size="large"
               />
            </div>
            {state.loadingFlag ? (
              <Skeleton
                placeholder={<Skeleton.Paragraph rows={6} />}
                loading={state.loadingFlag}
                active
              />
            ) : (
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
                        <div className="item-style">文件名 {item.fileName}</div>
                        <div className="item-style">
                          文件类型 {item.fileType}
                        </div>
                        <div className="item-style">{item.upLoadedBy}上传</div>
                        <div className="item-style">
                          上传时间 {item.uploadTime}
                        </div>
                        <div className="item-action-style">
                          <span>Action:</span>
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
            )}
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
            <span>Copyright © 2019. Power by semi. Design by vince</span>
          </span>
        </Footer>
      </Layout>
    );
  }
}

export default MainComponent;
