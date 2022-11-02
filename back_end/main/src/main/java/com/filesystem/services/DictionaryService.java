package com.filesystem.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.filesystem.mapper.DictionaryMapper;
import com.filesystem.models.DictionaryTable;
import com.filesystem.models.request_model.DictionaryRequestModel;
import com.filesystem.utils.AppUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * dictionary service
 */
@Service("DictionaryService")
@Component
public class DictionaryService {

    private static final Logger LOG = LogManager.getLogger(DictionaryService.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /**
     * 
     * @param codeName
     * @return
     */
    @Transactional
    public PageInfo<DictionaryTable> getDictionaries(DictionaryRequestModel requestModel) {

        if (StringUtils.isBlank(requestModel.getCodeName())) {

            requestModel.setCodeName(StringUtils.EMPTY);
        }

        PageInfo<DictionaryTable> result = PageHelper.startPage(requestModel.getPageNum(), requestModel.getPageSize())
                .doSelectPageInfo(() -> dictionaryMapper.searchCodeByName(requestModel.getCodeName()));
        return result;
    }

    /**
     * 
     * @param dictionaries
     * @return
     */
    public List<DictionaryTable> disableCodes(List<DictionaryTable> dictionaries) {

        if (CollectionUtils.isEmpty(dictionaries)) {
            LOG.warn("when try to disable dictionary, list is empty, dictionaries - > [{}]", dictionaries);
            return new ArrayList<DictionaryTable>(0);
        }

        List<String> ids = new ArrayList<>(dictionaries.size());
        dictionaries.forEach((v) -> {
            dictionaryMapper.disableDictionary(v);
            ids.add(v.getUuid());
        });

        // after delete, show the full data result state
        List<DictionaryTable> afterUpdateModels = dictionaryMapper.queryDicByIds(ids);
        return afterUpdateModels;
    }

    public List<DictionaryTable> insertNewDics(List<DictionaryTable> dictionaries) {

        List<String> newIds = new ArrayList<>(dictionaries.size());
        if (CollectionUtils.isEmpty(dictionaries)) {
            LOG.warn("when try to insert dictionary, list is empty, dictionaries - > [{}]", dictionaries);
            return new ArrayList<DictionaryTable>();
        }

        dictionaries.forEach(model -> {
            model.setUuid(
                    StringUtils.isNotBlank(model.getUuid()) ? model.getUuid()
                            : AppUtil.generateUUIDWithoutHyphen(true));
            newIds.add(model.getUuid());
        });

        dictionaryMapper.insertNewDics(dictionaries);
        // after insert, show the full data result state
        List<DictionaryTable> afterUpdateModels = dictionaryMapper.queryDicByIds(newIds);
        return afterUpdateModels;
    }
}
