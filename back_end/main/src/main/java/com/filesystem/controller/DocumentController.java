package com.filesystem.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.filesystem.consts.enums.ServiceStatusEnum;
import com.filesystem.execeptions.RequiredParameterMissingException;
import com.filesystem.models.DocumentTable;
import com.filesystem.models.ResponseModel;
import com.filesystem.models.request_model.DocumentRequestModel;
import com.filesystem.services.DocumentService;
import com.filesystem.utils.ResponseBuilder;
import com.github.pagehelper.PageInfo;

@CrossOrigin
@Controller("DocumentController")
@RequestMapping("/api/document")
public class DocumentController {

    private static final Logger LOG = LogManager.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;
    // file upload

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public ResponseModel<List<DocumentTable>> uploadFile(
            @RequestParam("villageType") String villageType,
            @RequestParam("customerName") String customerName,
            @RequestParam("files") MultipartFile[] files) {

        if (StringUtils.isBlank(villageType)) {
            throw new RequiredParameterMissingException("controller -> uploadFile, villageType");
        }
        LOG.info("do save file, village type ->[{}]", villageType);

        List<String> savedFileUUIDs = documentService.saveFile(files, villageType, customerName);
        try {
            return ResponseBuilder.build(documentService.queryFilesAfterSaved(savedFileUUIDs, villageType),
                    ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);
        } catch (Exception e) {
            LOG.error("uploadFile failed! error -> [{}]", e.getMessage());
            return ResponseBuilder.build(new ArrayList<DocumentTable>(), ServiceStatusEnum.FAILED, e.getMessage());
        }
    }

    // file search
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<PageInfo<DocumentTable>> searchDocumentTable(
            DocumentRequestModel documentRequestModel) {

        try {
            return ResponseBuilder.build(documentService.getDocumentTables(documentRequestModel),
                    ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);
        } catch (Exception e) {
            LOG.error("uploadFile failed! error -> [{}]", e.getMessage());
            return ResponseBuilder.build(new PageInfo<>(List.of()),
                    ServiceStatusEnum.FAILED, e.getMessage());
        }
    }

    @RequestMapping(value = "/view", method = RequestMethod.POST)
    @ResponseBody
    public void viewCurrentFile(
            @RequestParam("fileKey") String fileKey,
            @RequestParam("villageType") String villageType,
            HttpServletRequest req,
            HttpServletResponse res)
            throws Exception {

        if (StringUtils.isBlank(fileKey)) {
            throw new RequiredParameterMissingException("controller -> /view; fileKey");
        }
        if (StringUtils.isBlank(villageType)) {
            throw new RequiredParameterMissingException("controller -> /view; villageType");
        }

        res.reset();

        DocumentTable document = documentService.createTempFile(fileKey, villageType);

        String fileName = document.getFileName();
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.setContentType("application/octet-stream");
        res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream outputStream = res.getOutputStream();

        FileInputStream fis = new FileInputStream(new File(document.getFilePath()));

        outputStream.write(fis.readAllBytes());

        outputStream.flush();
        fis.close();

        return;
        // build stream
        // add to response

        // return ResponseBuilder.build(new EmptyObject(),
        // ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);
    }

    @RequestMapping(value = "/disable", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public ResponseModel<List<DocumentTable>> diableFiles(
            @RequestParam(value = "fileKey", required = false) String fileKey,
            @RequestParam("villageType") String villageType) {

        List<String> disableFileKeys = new ArrayList<>();
        if (StringUtils.isNotBlank(fileKey)) {
            disableFileKeys.add(fileKey);
        }

        return ResponseBuilder.build(documentService.disableDocument(disableFileKeys, villageType),
                ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);

    }

}
