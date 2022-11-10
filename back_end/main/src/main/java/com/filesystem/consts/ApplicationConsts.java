package com.filesystem.consts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.filesystem.models.SavedFileModel;

public class ApplicationConsts {

    private ApplicationConsts() {
    }

    public static final String MYBATIS_MAPPER_XML_LOCATION_PATH = "classpath:mappers/*Mapper.xml";

    public static final int ENABLE = 1;

    public static final int DISABLE = 0;

    public static final List<SavedFileModel> SAVED_FILE_MODEL = Collections
            .synchronizedList(new ArrayList<SavedFileModel>(20));

    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

}
