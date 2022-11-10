package com.filesystem.schedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.filesystem.consts.ApplicationConsts;
import com.filesystem.models.SavedFileModel;

// @Component
public class CachedFileScheduleService {

    private static final Logger LOG = LogManager.getLogger(CachedFileScheduleService.class);

    // @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    // @Scheduled(fixedDelay = 1000, timeUnit = TimeUnit.SECONDS)
    public void scanAndCleanTempFiles() {

        LOG.info("scan temp file...");

        if (CollectionUtils.isNotEmpty(ApplicationConsts.SAVED_FILE_MODEL)) {
            LOG.info("Cache file detected!");
            List<SavedFileModel> removeList = new ArrayList<>();

            for (SavedFileModel model : ApplicationConsts.SAVED_FILE_MODEL) {

                String oriFileName = model.getOriginalFileName();
                String cacheRandomFileName = model.getSavedFileName();
                LOG.info("==== Cache File Info ====");
                LOG.info("file original name [{}]", oriFileName);
                LOG.info("file original type [{}]", model.getOriginalFileType());
                LOG.info("cache file name [{}]", cacheRandomFileName);
                LOG.info("==== Cache File Clean ====");
                File cacheFile = model.getCacheFile();

                boolean fileExistFlag = cacheFile.exists();
                if (BooleanUtils.isFalse(fileExistFlag)) {
                    LOG.error("clean cache file -> [{}], temp file id -> [{}] failed, file does not exist", oriFileName,
                            cacheRandomFileName);
                }

                boolean fileDeleteFlag = cacheFile.delete();
                if (BooleanUtils.isFalse(fileDeleteFlag)) {
                    LOG.error("clean cache file -> [{}], temp file id -> [{}] failed, file delete failed", oriFileName,
                            cacheRandomFileName);
                }

                if (fileExistFlag && fileDeleteFlag) {
                    LOG.info("clean cache file -> [{}] success", cacheRandomFileName);
                }

                removeList.add(model);
                LOG.info("add file model -> [{}] to wait to be removed");
            }

            this.cleanTempList(removeList);
        } else {
            LOG.info("no cache files... will scan within 5 seconds...");
        }
    }

    private void cleanTempList(List<SavedFileModel> removeList) {

        if (CollectionUtils.isNotEmpty(removeList)) {
            removeList.forEach(model -> {
                ApplicationConsts.SAVED_FILE_MODEL.remove(model);
                LOG.info("clean file model -> [{}] success", model);
            });
        }
    }
}
