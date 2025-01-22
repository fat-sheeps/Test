package com.example.domain.ip;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Risk {

    /**
     * @var Reader
     */
    private Reader reader;

    public Risk(String name) throws IOException,InvalidDatabaseException {
        this.reader = new Reader(name);
    }

    public Risk(InputStream in) throws IOException, InvalidDatabaseException {
        this.reader = new Reader(in);
    }

    public boolean reload(String name) {
        try {
            Reader r = new Reader(name);
            this.reader = r;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Map<String, String> findMap(String addr, String language) throws IPFormatException, InvalidDatabaseException {
        String[] data = this.reader.find(addr, language);
        if (data == null) {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();

        String[] fields = this.reader.getSupportFields();

        for (int i = 0, l = data.length; i < l; i++) {
            m.put(fields[i], data[i]);
        }

        return m;
    }

    public RiskInfo findInfo(String addr) throws IPFormatException, InvalidDatabaseException {

        Map<String, String> data = this.findMap(addr, "CN");
        if (data == null) {
            return null;
        }

        return new RiskInfo(data);
    }

    public String fields() {
        return Arrays.toString(this.reader.getSupportFields());
    }
}