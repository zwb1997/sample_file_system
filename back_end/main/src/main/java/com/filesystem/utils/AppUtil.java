package com.filesystem.utils;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppUtil {

    private static final Logger LOG = LogManager.getLogger(AppUtil.class);

    public static String generateUUIDWithoutHyphen(boolean flag) {

        String uuid = UUID.randomUUID().toString();
        if (flag) {
            uuid = uuid.replace("-", "");
        }

        return uuid;
    }
}
