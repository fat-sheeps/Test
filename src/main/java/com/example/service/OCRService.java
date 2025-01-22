package com.example.service;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class OCRService {
    private final ITesseract tesseract;

    public OCRService() {
        this.tesseract = new Tesseract();
        //训练集存放的目录
        this.tesseract.setDatapath("C:\\Users\\wayio\\Desktop\\tessdata");
        this.tesseract.setLanguage("chi_sim");
        //如果想要实现简体和繁体的识别，只需要将语言类型拼接起来
        //tesseract.setLanguage("chi_sim+chi_tra");
    }

    public String recognizeText(BufferedImage image) throws TesseractException {

        return tesseract.doOCR(image);
    }
    public String recognizeText(String imageUrl) throws TesseractException {
        BufferedImage image = convertURLtoBufferedImage(imageUrl);
        if (image == null) {
            return null;
        }
        return recognizeText(image);
    }

    public static BufferedImage convertURLtoBufferedImage(String imageUrl) {
        try {
            // 创建一个URL对象
            URL url = new URL(imageUrl);
            // 使用ImageIO读取URL中的图片
            return ImageIO.read(url);
        } catch (IOException e) {
            log.error("imageUrl 解析error :{}", e.getMessage(), e);
            return null;
        }
    }
}
