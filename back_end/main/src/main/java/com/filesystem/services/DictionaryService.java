package com.filesystem.services;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.filesystem.mapper.DictionaryMapper;
import com.filesystem.models.DictionaryTable;

/**
 * dictionary service
 */
@Service("DictionaryService")
public class DictionaryService {

    private static final Logger LOG = LogManager.getLogger(CustomerService.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    public List<DictionaryTable> getDictionaries(String codeName) {

        LOG.info("1");
        if (StringUtils.isBlank(codeName)) {
            codeName = StringUtils.EMPTY;
        }
        List<DictionaryTable> resultList = dictionaryMapper.searchCodeByName(codeName);

        return resultList;
    }
}
