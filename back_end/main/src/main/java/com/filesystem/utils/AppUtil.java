package com.filesystem.utils;

import java.util.UUID;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppUtil {

    private static final Logger LOG = LogManager.getLogger(AppUtil.class);

    private static StopWatch sw;

    public static String generateUUIDWithoutHyphen(boolean flag) {

        String uuid = UUID.randomUUID().toString();
        if (flag) {
            uuid = uuid.replace("-", "");
        }

        return uuid;
    }
}
