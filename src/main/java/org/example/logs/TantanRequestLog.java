package org.example.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.SystemLogFactory;


public class TantanRequestLog {

    private static final Logger logger = LogManager.getLogger(TantanRequestLog.class.getName());

    public TantanRequestLog(){
        SystemLogFactory.put("tantan_request", logger);
    }
}
