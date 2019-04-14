package com.util.excel;

import com.module.hadoop.Sort;
import com.util.file.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author kelong
 * @date 4/22/15
 */
public class ReadExcel {
    public void read() {
        try {
            String fileToBeRead = "/home/kelong/Desktop/FindMyPhone_results_develop.xls";

            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            HSSFRow row = sheet.getRow(0);
            HSSFCell cell = null;
            int colNum = row.getPhysicalNumberOfCells();
            System.out.println("列数:"+colNum);
            for (int j = 1; j < colNum; j++) {
                String outPath = "/home/kelong/Desktop/fmp/i18n/";
                for (int i = 0; i <= rowNum; i++) {
                    row = sheet.getRow(i);
                    HSSFCell firstCell = row.getCell((short) 0);
                    String key = firstCell.getStringCellValue();
                    cell = row.getCell((short) j);
                    if(cell==null){
                        continue;
                    }
                    String value = cell.getStringCellValue();
                    if (i == 0) {
                        outPath += "message_" + value + ".properties";
                        continue;
                    }
                    String content = key + "=" + value;
                    System.out.println(key + "=" + value);
                    FileUtil.writeToFileByLock(outPath, content);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadExcel readExcel = new ReadExcel();
        readExcel.read();
    }
}
