package org.example.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.service.SystemLogFactory;

public class TantanResponseLog {
    private static final Logger logger = LogManager.getLogger(TantanResponseLog.class.getName());
    public TantanResponseLog(){
        SystemLogFactory.put("tantan_response", logger);
    }
}
