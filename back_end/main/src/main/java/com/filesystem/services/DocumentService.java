package com.filesystem.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import com.filesystem.execeptions.RequiredParameterMissingException;
import com.filesystem.mapper.DocumentMapper;
import com.filesystem.models.DocumentTable;
import com.filesystem.models.SavedFileModel;
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

    @Value("${app.file.servelt.temp.path}")
    private String webResourcesFileDirectory;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DocumentMapper documentMapper;

    private String createFileAndVillageName(String fileName, String villageType) {
        if (StringUtils.isBlank(fileName)) {
            throw new RequiredParameterMissingException("fileName");
        }
        if (StringUtils.isBlank(villageType)) {
            throw new RequiredParameterMissingException("villageType");
        }

        return new StringBuffer().append(fileName).append("-").append(villageType).toString();
    }

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
                Path curFilePath = Path.of(curRootPath, this.saveFilePath, villageType, cFileName);
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
    public List<DocumentTable> queryFilesAfterSaved(List<String> savedFileUUIDs, String villageType) {
        if (CollectionUtils.isEmpty(savedFileUUIDs)) {
            LOG.warn("when try to search file info by uuid, uuid list is empty; will not working.");
            return new ArrayList<DocumentTable>();
        }
        return documentMapper.searchFilesByUUids(savedFileUUIDs, villageType);
    }

    /**
     * working on
     * 
     * return a stream for download file
     * 
     * @param fileKey
     * @param villageType
     * @return
     * @throws Exception
     */
    public DocumentTable createTempFile(String fileKey, String villageType) throws Exception {

        DocumentTable documentTable = documentMapper.searchFilesByUUids(List.of(fileKey), villageType).stream()
                .findFirst()
                .get();

        return documentTable;
    }

    /**
     * check current file is cached
     * 
     * @param fileName
     * @param villageType
     * @return
     */
    private SavedFileModel checkFileIsCached(String fileName, String villageType) {
        LOG.info(ApplicationConsts.SAVED_FILE_MODEL);
        int pos = ApplicationConsts.SAVED_FILE_MODEL
                .indexOf(new SavedFileModel().setOriginalFileName(fileName).setFileVillageType(villageType));
        if (pos != -1) {
            LOG.info("current file -> [{}] village type -> [{}] already cached!", fileName, villageType);
            return ApplicationConsts.SAVED_FILE_MODEL.get(pos);
        }
        return new SavedFileModel();
    }

    /**
     * remove current cached file.
     * 
     * @param fileName
     * @param villageType
     * @return
     */
    // public String removeTempFile(String tempFileName, String villageType) {

    // String curFileKey = this.createFileAndVillageName(fileName, villageType);

    // int tempFileIndex = ApplicationConsts.SAVED_FILE_MODEL.indexOf(new
    // SavedFileModel().setSavedFileName(fileName))
    // .get(curFileKey);
    // String curTempFileName = curTempFile.getName();
    // if (curTempFile.delete()) {
    // LOG.info("delete temp file -> [{}] successfully", curTempFileName);
    // return curTempFileName;
    // }

    // return StringUtils.EMPTY;
    // }

    public List<DocumentTable> disableDocument(List<String> disableFileKeys, String villageType) {

        List<DocumentTable> results = new ArrayList<>();
        if (disableFileKeys.isEmpty()) {
            LOG.warn("when try to disable files, id is empty, will not work");

            return results;
        }

        List<String> removeFids = new ArrayList<>();
        // remove saved file
        disableFileKeys.forEach(key -> {
            DocumentTable dTable = documentMapper.searchFilesByUUids(List.of(key), villageType).stream().findFirst()
                    .get();
            File srcFile = new File(dTable.getFilePath());
            boolean fileExistsFlag = srcFile.exists();
            boolean fileRemoveFlag = srcFile.delete();
            if (BooleanUtils.isFalse(fileExistsFlag)) {
                LOG.warn("when try to remove file -> [{}], does not exists!", dTable);
            }

            if (BooleanUtils.isFalse(fileRemoveFlag)) {
                LOG.error("when try to remove file -> [{}], remove failed!", dTable);
            }
            LOG.info("remove file -> [{}] succussfully", dTable);

            dTable.setStateCode(ApplicationConsts.DISABLE);
            dTable.setDeleteTime(DateFormatUtils.format(new Date(), ApplicationConsts.DEFAULT_DATE_FORMAT_PATTERN));
            documentMapper.disableCurrentFile(dTable);
            removeFids.add(key);
        });

        // disable this column in db
        var d = documentMapper.queryFileByUUIDWithoutState(removeFids, villageType);
        return d;
    }

}
