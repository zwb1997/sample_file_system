package com.filesystem.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.filesystem.consts.enums.ServiceStatusEnum;
import com.filesystem.execeptions.RequiredParameterMissingException;
import com.filesystem.models.DictionaryTable;
import com.filesystem.models.ResponseModel;
import com.filesystem.services.DictionaryService;
import com.filesystem.utils.ResponseBuilder;

@Controller("DictionaryController")
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private static final Logger LOG = LogManager.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * search dictionary table by codeName
     * 
     * @param codeName
     * @return
     */
    @RequestMapping(value = "/dics", method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<List<DictionaryTable>> getDictionary(String codeName) {

        ResponseModel<List<DictionaryTable>> responseModel = null;
        try {
            responseModel = ResponseBuilder.build(dictionaryService.getDictionaries(codeName),
                    ServiceStatusEnum.SUCCESS, StringUtils.EMPTY);
        } catch (Exception e) {
            LOG.error("getDictionary error, message -> [{}]", e.getMessage());
            responseModel = ResponseBuilder.build(null, ServiceStatusEnum.FAILED, e.getMessage());
        }

        return responseModel;
    }
}
