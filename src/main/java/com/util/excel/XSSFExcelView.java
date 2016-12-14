package com.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 一个sheet 可支持一百万条 导出
 */
public class XSSFExcelView extends AbstractView {

    /** The content type for an Excel response */
    private static final String CONTENT_TYPE = "application/vnd.ms-excel";

    /** The extension to look for existing templates */
    private static final String EXTENSION = ".xlsx";


    private String url;

    /** Excel文件名  **/
    private String filename;

    /** excel sheet 名称  **/
    private String sheetName;

    /** 表头名称  **/
    private List<String> heads;

    /** 数据  **/
    private	List<List<Object>> datas;

    /** 最后一行统计数据  **/
    private List<String> foot;

    /**
     * Default Constructor.
     * Sets the content type of the view to "application/vnd.ms-excel".
     */
    public XSSFExcelView() {
        setContentType(CONTENT_TYPE);
    }

    public XSSFExcelView(String filename, String sheetName, List<String> heads, List<List<Object>> datas) {
        setContentType(CONTENT_TYPE);
        this.filename = filename;
        this.sheetName = sheetName;
        this.heads = heads;
        this.datas = datas;
    }

    public XSSFExcelView(String filename, String sheetName, List<String> heads, List<List<Object>> datas, List<String> foot) {
        setContentType(CONTENT_TYPE);
        this.filename = filename;
        this.sheetName = sheetName;
        this.heads = heads;
        this.datas = datas;
        this.foot = foot;
    }

    /**
     * Set the URL of the Excel workbook source, without localization part nor extension.
     */
    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    /**
     * Renders the Excel view, given the specified model.
     */
    @Override
    protected final void renderMergedOutputModel(
        Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        SXSSFWorkbook workbook;
        if (this.url != null) {
            workbook = getTemplateSource(this.url, request);
        }
        else {
            workbook = new SXSSFWorkbook();
            logger.debug("Created Excel Workbook from scratch");
        }

        buildExcelDocument(model, workbook, request, response);

        // Set the content type.
        //		response.setContentType(getContentType());
        response.setHeader("Content-disposition", "attachment;filename=" + filename + EXTENSION);
        // Should we set the content length here?
        // response.setContentLength(workbook.getBytes().length);

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        // dispose of temporary files backing this workbook on disk
        workbook.dispose();
    }

    /**
     * Creates the workbook from an existing XLS document.
     * @param url the URL of the Excel template without localization part nor extension
     * @param request current HTTP request
     * @return the SXSSFWorkbook
     * @throws Exception in case of failure
     */
    protected SXSSFWorkbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
        LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
        Locale userLocale = RequestContextUtils.getLocale(request);
        Resource inputFile = helper.findLocalizedResource(url, EXTENSION, userLocale);

        // Create the Excel document from the source.
        if (logger.isDebugEnabled()) {
            logger.debug("Loading Excel workbook from " + inputFile);
        }
        return new SXSSFWorkbook(new XSSFWorkbook(inputFile.getInputStream()));
    }

    /**
     * Subclasses must implement this method to create an Excel SXSSFWorkbook document,
     * given the model.
     * @param model the model Map
     * @param request in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     */
    protected void buildExcelDocument(
        Map<String, Object> model, SXSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response)
        throws Exception{


        Sheet sheet = wb.createSheet(sheetName);
        for(int i = 0;i < heads.size();i++){
            sheet.setColumnWidth(i, 20 * 256);
        }

        Row  row = sheet.createRow((int) 0);
        row.setHeight((short) (20 * 20));

        // 设置值表头 设置表头居中
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER); 	// 创建一个居中格式
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);

        Cell cell = null;
        //设置第一行表头
        if(heads != null){
            for(int i = 0;i < heads.size();i++){
                cell = row.createCell(i);
                cell.setCellValue(heads.get(i));
                cell.setCellStyle(style);
            }
        }
        Font font = (Font) wb.createFont();
        //		font.setFontHeightInPoints((short) 24); // 字体高度
        font.setFontName("宋体"); // 字体
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);// 宽度
        style.setFont(font);

        CellStyle styleMiddle = wb.createCellStyle();
        styleMiddle.setAlignment(CellStyle.ALIGN_CENTER);
        styleMiddle.setBorderRight(CellStyle.BORDER_THIN);  //设置右边框
        styleMiddle.setBorderLeft(CellStyle.BORDER_THIN);
        styleMiddle.setBorderTop(CellStyle.BORDER_THIN);
        styleMiddle.setBorderBottom(CellStyle.BORDER_THIN);
        //设置 内容
        for(int i = 0;i < datas.size();i++){
            row = sheet.createRow(i+1);
            List<Object> lines = datas.get(i);
            for(int j = 0;j < lines.size();j++){
                cell = row.createCell(j);
                cell.setCellStyle(styleMiddle);
                Object data = lines.get(j);
                if(data != null) cell.setCellValue(data.toString());
            }
        }

        //设置 最后一行统计信息
        row = sheet.createRow(datas.size() + 2);
        row.setHeight((short) (20 * 20));
        if(foot != null){
            for(int i = 0;i < foot.size();i++){
                cell = row.createCell(i);
                cell.setCellValue(foot.get(i));
                cell.setCellStyle(style);
            }
        }

    }


    /**
     * Convenient method to obtain the cell in the given sheet, row and column.
     * <p>Creates the row and the cell if they still doesn't already exist.
     * Thus, the column can be passed as an int, the method making the needed downcasts.
     * @param sheet a sheet object. The first sheet is usually obtained by workbook.getSheetAt(0)
     * @param row thr row number
     * @param col the column number
     * @return the HSSFCell
     */
    protected Cell getCell(Sheet sheet, int row, int col) {
        Row sheetRow = sheet.getRow(row);
        if (sheetRow == null) {
            sheetRow = sheet.createRow(row);
        }
        Cell cell = sheetRow.getCell(col);
        if (cell == null) {
            cell = sheetRow.createCell(col);
        }
        return cell;
    }

    /**
     * Convenient method to set a String as text content in a cell.
     * @param cell the cell in which the text must be put
     * @param text the text to put in the cell
     */
    protected void setText(Cell cell, String text) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(text);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getHeads() {
        return heads;
    }

    public void setHeads(List<String> heads) {
        this.heads = heads;
    }

    public List<List<Object>> getDatas() {
        return datas;
    }

    public void setDatas(List<List<Object>> datas) {
        this.datas = datas;
    }

    public List<String> getFoot() {
        return foot;
    }

    public void setFoot(List<String> foot) {
        this.foot = foot;
    }

}
