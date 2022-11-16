import React from "react";
import "./LoadFileComponment.css";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";

import { Layout, Upload, Toast, Notification } from "@douyinfe/semi-ui";

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
      reloadFileListCallBack: this.props.reloadFileList,
      currentCustomerName: this.props.customerName,
      acceptFileTypeSuffix:
        "." + [...app_cofig.fileTypes.map((v) => v.name)].join(", ."),
    };
  }

  componentDidMount() {}

  handleClose = () => {
    this.setState({ dialogStateFlag: false });
  };

  beforeUpload = ({ file, fileList }) => {
    const { currentVillageTypeKey } = this.state;

    let villageKey = currentVillageTypeKey
      ? currentVillageTypeKey
      : this.props.currentVillageTypeKey;
    console.log("before upload file, village type is", villageKey);
    // console.log(file, fileList);
    return true;
  };
  onSuccess = (res, file, fileList) => {
    const { reloadFileListCallBack } = this.state;
    if (res.code === 1) {
      Toast.success(`文件\t${file.name}\t上传成功`);
    } else {
      Toast.error(`文件\t${file.name}\t上传失败`);
    }

    this.setState({ dialogStateFlag: false }, () => {
      //reload file table
      reloadFileListCallBack();
    });
  };

  checkInvalidFile = (file) => {
    if (Array.isArray(file)) {
      file.forEach((v) =>
        Toast.create({
          type: "error",
          content: `上传的文件\t[${v.name}]\t没有有效的后缀(文件格式不规范)!`,
          duration: 8,
        })
      );
    }
    this.setState({
      dialogStateFlag: false,
    });
  };

  checkExceed = (fileList) => {
    // console.log("over", fileList);
    fileList.splice(3).forEach((v) => {
      Notification.error({
        title: "超出单次限制",
        content: <div>文件{v.name}上传失败</div>,
      });
    });
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
    let villageKey = currentVillageTypeKey
      ? currentVillageTypeKey
      : this.props.currentVillageTypeKey;
    const { Content } = Layout;

    return (
      <Content>
        <Button
          variant="outlined"
          onClick={() => {
            console.log("when click dialog, current village type", villageKey);
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
              onSuccess={this.onSuccess}
              onAcceptInvalid={this.checkInvalidFile}
              fileName={app_cofig.fileConfig.postRequestParamName}
              beforeUpload={this.beforeUpload}
              dragIcon={<IconBolt />}
              draggable={true}
              accept={acceptFileTypeSuffix}
              dragMainText={
                <div>
                  <div>
                    单文件最大支持<span className="notice-style">20mb</span>
                  </div>
                  <div>
                    一次最多上传<span className="notice-style">{app_cofig.fileConfig.maxLimit}个</span>文件
                  </div>
                  <div>点击上传文件或拖拽文件到这里</div>
                </div>
              }
              dragSubText={
                <div>
                  {"仅支持"}
                  <span className="notice-style">
                    {app_cofig.fileTypes.map((v) => v.name).join(". \t")}
                  </span>
                </div>
              }
              style={{ marginTop: 10 }}
              limit={app_cofig.fileConfig.maxLimit}
              onExceed={this.checkExceed}
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