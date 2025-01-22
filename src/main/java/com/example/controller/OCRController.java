package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.service.OCRService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController()
@RequestMapping("")
@Slf4j
public class OCRController {

    @Autowired
    private OCRService ocrService;

    @RequestMapping(value = "/ocr",  method = {RequestMethod.POST, RequestMethod.GET})
    public Object imp(HttpServletRequest httpServletRequest, HttpServletResponse response,
                      @RequestBody(required = false)  Request request) throws TesseractException {
        log.info("request:{}", JSON.toJSONString(request));
        String text = ocrService.recognizeText(request.getUrl());
        log.info("text:{}", text);
        return text;
    }

    @Data
    public static class Request {
        private String url;
    }






}
