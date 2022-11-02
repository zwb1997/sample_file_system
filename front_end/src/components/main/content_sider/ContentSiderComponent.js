import React from "react";
import "./ContentSiderComponent.css";

import axios from "axios";
import { Layout, Nav, Skeleton, Divider, Pagination } from "@douyinfe/semi-ui";
import { IconHome } from "@douyinfe/semi-icons";
import app_cofig from "../../../config/Config.js";

class ContentSiderComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      villageLoadingFlag: true,
      currentVillage: "",
      pageInfos: {
        pageNum: 1,
        pageSize: 10,
      },
      responseData: {
        villageTypeList: [],
        pageNum: 0,
        totalCount: 0,
        pageSize: 0,
      },
      currentVillageTypeHandler: this.props.setCurrentVillageType,
    };
  }

  componentDidMount() {
    this.fetchVillageTypes();
  }

  fetchVillageTypes = () => {
    let requestUrl = app_cofig.baseRequestUrl + "dictionary/dics";
    const { responseData, pageInfos, currentVillageTypeHandler } = this.state;
    axios
      .get(requestUrl, {
        method: "GET",
        params: {
          codeName: "village_category_",
          pageNum: pageInfos.pageNum,
          pageSize: pageInfos.pageSize,
          totalCount: 0,
          totalPages: 0,
        },
      })
      .then((d) => {
        let resData = d.data.data;
        if (resData && Array.isArray(resData.list)) {
          responseData.villageTypeList = resData.list.map((model) => {
            return {
              itemKey: model.codeName,
              text: model.codeValue,
              icon: <IconHome size="large" />,
            };
          });
          responseData.pageNum = resData.pageNum;
          responseData.pageSize = resData.pageSize;
          responseData.totalCount = resData.total;
          this.setState({
            villageLoadingFlag: false,
            responseData: responseData,
            currentVillage:
              responseData.villageTypeList.length > 0
                ? responseData.villageTypeList[0].itemKey
                : "",
          });

          currentVillageTypeHandler(
            responseData.villageTypeList[0].itemKey,
          );
        } else {
          throw new Error(`request ${requestUrl} error`);
        }
      })
      .catch((e) => {
        console.error(`request ${requestUrl} error`, e);
      })
      .finally(() => {});
  };

  onPageChange = (shiftPageNum, shiftPageSize) => {
    // balabala....
    const { pageInfos } = this.state;
    pageInfos.pageNum = shiftPageNum;
    pageInfos.pageSize = shiftPageSize;

    this.setState(
      {
        pageInfos: pageInfos,
      },
      () => {
        this.fetchVillageTypes();
      }
    );
  };

  shiftCurrentVillageType = (option) => {
    const { currentVillageTypeHandler } = this.state;
    let villageType = option.itemKey;
    this.setState(
      {
        currentVillage: villageType,
      },
      () => {
        currentVillageTypeHandler(this.state.currentVillage);
      }
    );
  };

  render() {
    const { Sider } = Layout;
    let { villageLoadingFlag, currentVillage, responseData } = this.state;
    return (
      <Sider>
        <Skeleton
          placeholder={
            <Skeleton.Paragraph rows={10} className="content-sider-style" />
          }
          loading={villageLoadingFlag}
          active
        >
          <Nav
            style={{ maxWidth: 220, height: "100%" }}
            defaultSelectedKeys={[currentVillage]}
            footer={{
              collapseButton: true,
            }}
            onClick={this.shiftCurrentVillageType}
          >
            {responseData.villageTypeList.map((item, index) => {
              return (
                <div key={item.itemKey + "" + index}>
                  <Nav.Item
                    itemKey={item.itemKey}
                    text={item.text}
                    icon={item.icon}
                  />
                  <Divider
                    style={{
                      borderColor: "#2196f36e",
                    }}
                  />
                </div>
              );
            })}
            <Nav.Footer>
              <Pagination
                hoverShowPageSelect
                size="small"
                style={{
                  width: "100%",
                  flexBasis: "100%",
                  justifyContent: "center",
                }}
                pageSize={responseData.pageSize}
                total={responseData.totalCount}
                currentPage={responseData.pageNum}
                onChange={this.onPageChange}
              />
            </Nav.Footer>
          </Nav>
        </Skeleton>
      </Sider>
    );
  }
}

export default ContentSiderComponent;
