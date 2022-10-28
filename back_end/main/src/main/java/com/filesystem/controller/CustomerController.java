package com.filesystem.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RequestMapping("/api/customer")
@Controller("CustomerController")
public class CustomerController {

    private static final Logger LOG = LogManager.getLogger(CustomerController.class);
    @Qualifier("JacksonMapper")
    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    public String customerTest() throws Exception {
        LOG.trace("go into a function!");

        ObjectNode node = mapper.createObjectNode().put("a", "1");

        String result = mapper.writeValueAsString(node);

        return result;
    }
}
