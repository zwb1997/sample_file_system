package com.filesystem.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.filesystem.consts.ApplicationConsts;
import com.filesystem.mapper.DocumentMapper;
import com.filesystem.models.DocumentTable;
import com.filesystem.models.request_model.DocumentRequestModel;
import com.filesystem.utils.AppUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

// File service
@Service("DocumentService")
@Component
public class DocumentService {

    private static final Logger LOG = LogManager.getLogger(DocumentService.class);

    @Value("${app.file.save.location}")
    private String saveFilePath;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DocumentMapper documentMapper;

    /**
     * try to save file in local dicretory and return these file's uuids.
     * 
     * @param files
     * @param villageType
     * @param customerName
     * @return
     */
    public List<String> saveFile(MultipartFile[] files, String villageType, String customerName) {

        if (ArrayUtils.isEmpty(files)) {
            LOG.warn("when try to save files, MultipartFile array is empty; will not start working");
            return new ArrayList<String>();
        }
        List<String> savedFileIds = new ArrayList<>(files.length);
        for (MultipartFile fileModel : files) {

            String cFileName = fileModel.getOriginalFilename();

            try {
                String curRootPath = Paths.get("").toAbsolutePath().toString();
                Path curFilePath = Path.of(curRootPath, this.saveFilePath, cFileName);
                File cFile = curFilePath.toFile();
                if (cFile.exists()) {
                    LOG.warn("current file -> [{}] already exists, will not writing to data directory", cFileName);
                    savedFileIds.add(
                            documentMapper.queryDocuments(villageType, cFileName).stream().findFirst().get().getUuid());
                    continue;
                }
                FileUtils.writeByteArrayToFile(cFile, fileModel.getInputStream().readAllBytes());
                //
                LOG.info("writing file -> [{}] to local directory success!", cFileName);
                savedFileIds.add(
                        saveFileToDB(fileModel, villageType, customerName, curFilePath.toFile().getCanonicalPath()));
            } catch (Exception e) {
                LOG.error("save file -> [{}] failed", cFileName, e);
            }
        }
        return savedFileIds;
    }

    /**
     * save successfully writing local file infos to db
     * 
     * @param fileModel
     * @param villageType
     * @param customerName
     * @param curFilePath
     * @return
     */
    private String saveFileToDB(
            MultipartFile fileModel,
            String villageType,
            String customerName,
            String curFilePath) {
        if (ObjectUtils.isEmpty(fileModel)) {
            LOG.warn("current file object is empty, will not save file infos to DB");
            return StringUtils.EMPTY;
        }
        String curUUID = AppUtil.generateUUIDWithoutHyphen(true);
        String curFileName = fileModel.getOriginalFilename();
        // check if same file name and type exist, if exist, do update
        DocumentTable documentTable = new DocumentTable()
                .setUuid(curUUID)
                .setCreateCustomer(customerName)
                .setCreateTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .setFileName(curFileName)
                .setFileType(fileModel.getContentType())
                .setFilePath(curFilePath)
                .setFileSize(fileModel.getSize())
                .setStateCode(ApplicationConsts.ENABLE)
                .setVillageType(villageType)
                .setUpdatedTime(StringUtils.EMPTY)
                .setDeleteTime(StringUtils.EMPTY);
        // List<DocumentTable> checkTables = documentMapper.queryDocuments(villageType,
        // curFileName);
        // if (CollectionUtils.isNotEmpty(checkTables)) {
        // LOG.warn(
        // "when try to save file -> [{}], uuid -> [{}], after checking, the file
        // already exists, will not insert.",
        // curFileName, curUUID);
        // return checkTables.stream().findFirst().get().getUuid();
        // }
        // do insert
        documentMapper
                .insertSavedFileInfo(
                        List.of(
                                documentTable));

        return curUUID;
    }

    /**
     * get pageInfo<DocumentTable>
     * 
     * @param documentRequestModel
     * @return
     */
    @Transactional
    public PageInfo<DocumentTable> getDocumentTables(DocumentRequestModel documentRequestModel) throws Exception {

        if (StringUtils.isBlank(documentRequestModel.getFileName())) {
            documentRequestModel.setFileName(StringUtils.EMPTY);
        }
        if (StringUtils.isBlank(documentRequestModel.getVillageKey())) {
            documentRequestModel.setVillageKey(StringUtils.EMPTY);
        }

        PageInfo<DocumentTable> results = PageHelper
                .startPage(documentRequestModel.getPageNum(), documentRequestModel.getPageSize())
                .doSelectPageInfo(() -> documentMapper.queryDocuments(documentRequestModel.getVillageKey(),
                        documentRequestModel.getFileName()));

        return results;
    }

    /**
     * after successfully saved some files into db, query files columns
     * 
     * @param savedFileUUIDs
     * @return
     */
    public List<DocumentTable> queryFilesAfterSaved(List<String> savedFileUUIDs) {
        if (CollectionUtils.isEmpty(savedFileUUIDs)) {
            LOG.warn("when try to search file info by uuid, uuid list is empty; will not working.");
            return new ArrayList<DocumentTable>();
        }
        return documentMapper.searchFilesByUUids(savedFileUUIDs);
    }

}
