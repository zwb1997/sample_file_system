import React from "react";
import "./ContentSiderComponent.css";

import axios from "axios";
import { Layout, Nav, Skeleton } from "@douyinfe/semi-ui";
import { IconHome } from "@douyinfe/semi-icons";
import app_cofig from "../../config/Config.js";

class ContentSiderComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      villageLoadingFlag: true,
      villageTypeList: props.villageTypeList ? props.villageTypeList : [],
    };
  }

  componentDidMount() {
    this.fetchFileTypes();
  }

  fetchFileTypes = () => {
    let requestUrl = app_cofig.baseRequestUrl + "dictionary/dics";
    let villageTypeList = [];
    axios
      .get(requestUrl, {
        method: "GET",
        params: {
          codeName: "village_category_",
        },
      })
      .then((d) => {
        let responseData = d.data;
        if (responseData && Array.isArray(responseData.data)) {
          villageTypeList = responseData.data.map((model) => {
            return {
              itemKey: model.codeName,
              text: model.codeValue,
              icon: <IconHome size="large" />,
            };
          });
        } else {
          throw new Error(
            `request ${requestUrl} error, responseData.data is not array`
          );
        }
      })
      .catch((e) => {
        console.error(`request ${requestUrl} error`, e);
      })
      .finally(() => {
        this.setState({
          villageLoadingFlag: false,
          villageTypeList: villageTypeList ? villageTypeList : [],
        });
      });
  };

  render() {
    const { Sider } = Layout;
    let state = this.state;
    return (
      <Sider style={{ backgroundColor: "var(--semi-color-bg-1)" }}>
        <Skeleton
          placeholder={
            <Skeleton.Paragraph
              rows={10}
              style={{ maxWidth: 220, marginBottom: 10 }}
            />
          }
          loading={state.villageLoadingFlag}
          active
        >
          <Nav
            style={{ maxWidth: 220, height: "100%" }}
            defaultSelectedKeys={
              state.villageTypeList.length
                ? [state.villageTypeList[0].itemKey]
                : []
            }
            items={state.villageTypeList}
            footer={{
              collapseButton: true,
            }}
          />
        </Skeleton>
      </Sider>
    );
  }
}

export default ContentSiderComponent;
