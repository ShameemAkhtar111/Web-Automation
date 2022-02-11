package com.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public String filepath;
    public FileInputStream file = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    public ExcelReader(String filepath){
        ZipSecureFile.setMinInflateRatio(0);
        this.filepath = filepath;
        try{
            file = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheetAt(0);
            file.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getRowCount(String sheetName){
        int index = workbook.getSheetIndex(sheetName);
        if(index == -1){
            return 0;
        }else{
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }
    }

    public String getDefaultSheetName(){
        String sheet = workbook.getSheetName(0);
        return sheet;
    }

    public String getCellData(String sheetName,String colName,int rowNum){
        try{
            if(rowNum<=0){
                return "";
            }
            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if(index == -1){
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for(int i=0;i<row.getLastCellNum();i++){
                if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())){
                    col_Num=i;
                    break;
                }
            }
            if(col_Num == -1){
                return "";
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum -1);
            if(row==null){
                return "";
            }
            cell = row.getCell(col_Num);

            if(cell == null){
                return "";
            }

            if(cell.getCellType().name().equals("STRING")){
                return cell.getStringCellValue();
            }else if((cell.getCellType().name().equals("NUMERIC"))){
                String cellText = String.valueOf(cell.getNumericCellValue());
                return cellText;
            }else if(cell.getCellType().BLANK !=null){
                return "";
            }else{
                return String.valueOf(cell.getBooleanCellValue());
            }
        }catch(Exception e){
            e.printStackTrace();
            return "row "+ rowNum + " or column "+ colName + " does not exist in xlsx";
        }
    }

    public Object getCellData(String sheetName,int colNum,int rowNum){
        try {
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            if(rowNum<=0){
                return null;
            }
            int index = workbook.getSheetIndex(sheetName);
            if(index==-1){
                return null;
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if(row == null){
                return null;
            }
            cell = row.getCell(colNum - 1);
            if(cell == null){
                return null;
            }

            if(cell.getCellType().name().equals("FORMULA")){

                switch(evaluator.evaluateFormulaCell(cell)){
                    case STRING :
                        return cell.getStringCellValue();
                    case NUMERIC :
                        return cell.getNumericCellValue();
                    case BOOLEAN : 
                        // return String.valueOf(cell.getBooleanCellValue());
                        return cell.getBooleanCellValue();
                    default :
                        return null;
                }
            }else{
                if(cell.getCellType().name().equals("STRING")){
                    return cell.getStringCellValue();
                }else if((cell.getCellType().name().equals("NUMERIC"))){
                    return cell.getNumericCellValue();
                }else if(cell.getCellType().BLANK!=null){
                    return "";
                }else{
                    return cell.getBooleanCellValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "row "+ rowNum + " or column "+ colNum + " does not exist in xlsx";
        }
    }

    public Boolean setCellData(String sheetName,String colName,int rowNum,String data){
        try {
            // file = new FileInputStream(filepath);
            // workbook = new XSSFWorkbook(file);
            if(rowNum<=0){
                return false;
            }
            ZipSecureFile.setMinInflateRatio(0);
            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if(index == -1){
                return false;
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);

            for(int i=0;i<row.getLastCellNum();i++){
                if(row.getCell(i).getStringCellValue().trim().equals(colName)){
                    colNum = i;
                    break;
                }
            }

            if(colNum == -1){
                return false;
            }

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if(row == null){
                row = sheet.createRow(rowNum-1);
            }

            cell = row.getCell(colNum);
            if(cell == null){
                cell = row.createCell(colNum);
            }

            cell.setCellValue(data);
            fileOut = new FileOutputStream(filepath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean setCellData(String sheetName, String[] colNames,Object[] dataValuesToWrite, int rowNum ){
        try {
            // file = new FileInputStream(filepath);
            // workbook = new XSSFWorkbook(file);
            if(rowNum<=0){
                return false;
            }
            ZipSecureFile.setMinInflateRatio(0);
            int index = workbook.getSheetIndex(sheetName);
            if(index == -1){
                return false;
            }
            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            ArrayList<Integer> colNums = new ArrayList<Integer>();
            for(int i=0;i<row.getLastCellNum();i++){
                for(String values:colNames){
                    if(row.getCell(i).getStringCellValue().trim().equals(values)){
                        colNums.add(i);
                    }
                }
            }
            if(colNums.size()==0){
                return false;
            }

            for(int columns : colNums){
                sheet.autoSizeColumn(columns);
            }
            row = sheet.getRow(rowNum - 1);
            if(row == null){
                row = sheet.createRow(rowNum-1);
            }

            Object[] arrColumnNums = colNums.toArray();

            for(int i=0;i<arrColumnNums.length;i++){
                cell = row.getCell((Integer)arrColumnNums[i]);
                if(cell == null){
                    cell = row.createCell((Integer)arrColumnNums[i]);
                }
                cell.setCellValue((String) dataValuesToWrite[i]);
            }

            fileOut = new FileOutputStream(filepath);
            workbook.write(fileOut);
            fileOut.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean isSheetExist(String sheetName){
        int index = workbook.getSheetIndex(sheetName);
        if(index == -1){
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            if(index == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    public int getColumnCount(String sheetName){
        if(!isSheetExist(sheetName)){
            return -1;
        }
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);
        
        if(row == null){
            return -1;
        }
        return row.getLastCellNum();
    }

    public int getCellRowNum(String sheetName,String colName,String cellValue){
        for(int i=2;i<=getRowCount(sheetName);i++){
            if(getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)){
                return i;
            }
        }
        return -1;
    }

    public int getCountofValuesInSheet(String sheetName,String colName,String cellValue){
        int count = 0;
        for(int i=2;i<=getRowCount(sheetName);i++){
            if(getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)){
               count++;
            }
        }
        return count;
    }

    public Map getDictionaryBasedOnTestCaseIdAndFlagValue(String sheetName,String testCIdVar,String testCaseIdValue,
                                                        String flagVar,String flagValue){
        HashMap<String,String> dictItems = new HashMap<String,String>();
        int index = workbook.getSheetIndex(sheetName);
        if(index == -1){
            throw new RuntimeException("Invalid SheetName");
        }
        int totalRows = getRowCount(sheetName);
        int i=1;
        for(int rcounter =2;rcounter<= totalRows;rcounter++){
            String testCId = getCellData(sheetName, testCIdVar, rcounter);
            String flVar = getCellData(sheetName, flagVar, rcounter);
            if(testCId.equalsIgnoreCase(testCaseIdValue) && flVar.equalsIgnoreCase(flagValue)){

                for(int col=1;col<=getColumnCount(sheetName);col++){
                    dictItems.put((String) getCellData(sheetName, col, 1) + "#" + String.valueOf(i),
                                (String) getCellData(sheetName,col,rcounter));
                }
                i++;
            }
        
        }
        return dictItems;
            
    }

    public int getRowCountOfDict(String sheetNameOfDict,Map mp){
        int colCount = getColumnCount(sheetNameOfDict);
        int dictRowCount = mp.size()/colCount;
        return dictRowCount;
    }

    public Map getDictionaryBasedOnTestCaseId(String sheetName,String testCIdVar,String testCaseIdValue){
        HashMap<String,String> dictItems = new HashMap<String,String>();
        int index =  workbook.getSheetIndex(sheetName);
        if(index == -1){
            throw new RuntimeException("Invalid SheetName");
        }
        int totalRows = getRowCount(sheetName);
        int i=1;
        for(int rcounter = 2;rcounter<= totalRows;rcounter++){
            String testCId = getCellData(sheetName, testCIdVar, rcounter);
            if(testCId.equalsIgnoreCase(testCaseIdValue)){
                for(int col=1;col<=getColumnCount(sheetName);col++){
                    dictItems.put((String) getCellData(sheetName,col,1) + "#" +String.valueOf(i),
                     (String)getCellData(sheetName, col, rcounter));
                }
                i++;
            }
        }
        return dictItems;
    }
}