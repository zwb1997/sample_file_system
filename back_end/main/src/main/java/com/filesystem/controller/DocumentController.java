package com.filesystem.controller;

import java.util.ArrayList;
import java.util.List;

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

        LOG.info("do save file, village type ->[{}]", villageType);

        List<String> savedFileUUIDs = documentService.saveFile(files, villageType, customerName);
        try {
            return ResponseBuilder.build(documentService.queryFilesAfterSaved(savedFileUUIDs),
                    ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);
        } catch (Exception e) {
            LOG.error("uploadFile failed! error -> [{}]", e.getMessage());
            return ResponseBuilder.build(new ArrayList<DocumentTable>(), ServiceStatusEnum.FAILED, e.getMessage());
        }
    }

    // file download

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

}
