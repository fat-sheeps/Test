package org.example.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.SystemLogFactory;

public class MomoRequestLog {

    private static final Logger logger = LogManager.getLogger(MomoRequestLog.class.getName());
    public MomoRequestLog(){

        SystemLogFactory.put("momo_request", logger);
    }
}
