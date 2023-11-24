package org.example.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TranService {

    private static TranService instance;

    public static TranService getInstance() {
        if (instance == null) {
            instance = new TranService();
        }
        return instance;
    }

    public void doProcess(String param) {
        log.info("doProcess:{}", param);
    }
}
