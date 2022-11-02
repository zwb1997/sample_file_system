package com.filesystem.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * customer service
 */
@Service("CustomerService")
@Component
public class CustomerService {

    private static final Logger LOG = LogManager.getLogger(CustomerService.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // login

}
