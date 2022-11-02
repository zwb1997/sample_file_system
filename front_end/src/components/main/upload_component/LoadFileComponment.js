import React from "react";
import "./LoadFileComponment.css";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";

import { Layout, Upload } from "@douyinfe/semi-ui";
import { IconBolt } from "@douyinfe/semi-icons";
import app_cofig from "../../../config/Config.js";
import { DialogActions } from "@mui/material";
export default class LoadFileComponment extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      dialogStateFlag: false,
      dialogAlert: "点击上传",
      fileTitleContent: "请选择上传文件",
      currentVillageTypeKey: this.props.currentVillageTypeKey,
      currentCustomerName: "",
      acceptFileTypeSuffix:
        "application/" + [...app_cofig.fileTypes.map((v) => v.name)].join(",."),
    };
  }

  componentDidMount() {}

  handleClose = () => {
    console.log("dialog close!");
    this.setState({ dialogStateFlag: false });
  };

  beforeUpload = ({ file, fileList }) => {
    const { currentVillageTypeKey } = this.state;

    let villageKey = currentVillageTypeKey ? currentVillageTypeKey : this.props.currentVillageTypeKey;
    console.log("before upload file, village type is", villageKey);
    console.log(file, fileList);
    return false;
  };

  render() {
    const {
      dialogStateFlag,
      fileTitleContent,
      dialogAlert,
      currentVillageTypeKey,
      currentCustomerName,
      acceptFileTypeSuffix,
    } = this.state;
    let villageKey = currentVillageTypeKey ? currentVillageTypeKey : this.props.currentVillageTypeKey;
    const { Content } = Layout;
    return (
      <Content>
        <Button
          variant="outlined"
          onClick={() => {
            console.log(
              "when click dialog, current village type",
              villageKey
            );
            this.setState({ dialogStateFlag: true });
          }}
        >
          {dialogAlert}
        </Button>
        <Dialog
          disableEscapeKeyDown={true}
          fullWidth={true}
          open={dialogStateFlag}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">{fileTitleContent}</DialogTitle>
          <DialogContent>
            {/* 弹窗组件 */}

            <Upload
              listType="list"
              action={
                app_cofig.uploadRequestUrl +
                `?villageType=${villageKey}&customerName=${currentCustomerName}`
              }
              // beforeUpload={this.beforeFileLoadAction}
              fileName={app_cofig.fileConfig.postRequestParamName}
              beforeUpload={this.beforeUpload}
              dragIcon={<IconBolt />}
              draggable={true}
              accept={acceptFileTypeSuffix}
              dragMainText={
                <div>
                  <div>单文件最大支持20mb</div>
                  <div>点击上传文件或拖拽文件到这里</div>
                </div>
              }
              dragSubText={
                "仅支持" + app_cofig.fileTypes.map((v) => v.name).join(". \t")
              }
              style={{ marginTop: 10 }}
              limit={app_cofig.fileConfig.maxLimit}
              multiple={true}
              maxSize={1024 * 20}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleClose}>取消</Button>
          </DialogActions>
        </Dialog>
      </Content>
    );
  }
}
