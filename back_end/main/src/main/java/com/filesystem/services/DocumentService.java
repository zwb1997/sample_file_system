package com.filesystem.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

// File service
@Service("DocumentService")
public class DocumentService {

    private static final Logger LOG = LogManager.getLogger(DocumentService.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
