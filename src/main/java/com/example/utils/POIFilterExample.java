package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class POIFilterExample {
    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook(); // 创建新的Excel工作簿
        Sheet sheet = workbook.createSheet("FilterSheet"); // 创建一个新的工作表

        // 创建一些数据并添加到表格中
        Row row1 = sheet.createRow(0); // 创建表头（作为筛选的范围）
        Cell cell1 = row1.createCell(0);
        cell1.setCellValue("Column1");
        Cell cell2 = row1.createCell(1);
        cell2.setCellValue("Column2");
        Cell cell3 = row1.createCell(2);
        cell3.setCellValue("Column3");
        Cell cell4 = row1.createCell(3);
        cell4.setCellValue("Column4");

        // 数据1
        Row row2 = sheet.createRow(1);
        Cell cell21 = row2.createCell(0);
        cell21.setCellValue("val-a");
        Cell cell22 = row2.createCell(1);
        cell22.setCellValue("val-b");
        Cell cell23 = row2.createCell(2);
        cell23.setCellValue("val-c");
        Cell cell24 = row2.createCell(3);
        cell24.setCellValue("val-d");

        // 数据2
        Row row3 = sheet.createRow(2);
        Cell cell31 = row3.createCell(0);
        cell31.setCellValue("val-a2");
        Cell cell32 = row3.createCell(1);
        cell32.setCellValue("val-b2");
        Cell cell33 = row3.createCell(2);
        cell33.setCellValue("val-c2");
        Cell cell34 = row3.createCell(3);
        cell34.setCellValue("val-d2");

        // 数据3
        Row row4 = sheet.createRow(3);
        Cell cell41 = row4.createCell(0);
        cell41.setCellValue("val-a3");
        Cell cell42 = row4.createCell(1);
        cell42.setCellValue("val-b3");
        Cell cell43 = row4.createCell(2);
        cell43.setCellValue("val-c3");
        Cell cell44 = row4.createCell(3);
        cell44.setCellValue("val-d3");


        List<String> list = new ArrayList<>();
        list.add("qwer");
        list.add("asdf");
        list.add("zxcv");
        //list转array
        String[] array = new String[list.size()];
        list.toArray(array);
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)dvHelper.createExplicitListConstraint(array);
        //(开始行, 结束行, 开始列, 结束列);固定第3列为枚举
        CellRangeAddressList addressList = new CellRangeAddressList(1, 99, 2, 2);
        XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setSuppressDropDownArrow(true); //07默认true
        sheet.addValidationData(validation);

        // 设置筛选范围，这里是第一行（即表头）
        //CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 1); // 从第一行的第一列到第二列
        //sheet.setAutoFilter(cellRangeAddress); // 应用筛选

        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream("C:/Users/wayio/Desktop/poi_test.xlsx")) {
            workbook.write(outputStream);
        }

        workbook.close();
    }
}
