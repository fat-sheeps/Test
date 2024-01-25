package org.example.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.SystemLogFactory;

public class MomoResponseLog {
    private static final Logger logger = LogManager.getLogger(MomoResponseLog.class.getName());

    public MomoResponseLog(){
        SystemLogFactory.put("momo_response", logger);
    }
}
