package com.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.framework.FileReaderManager;

import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

public class Data_Supplier {

    @DataProvider(name = "testdata")

    public Object[][] datasupplier(ITestNGMethod testContext) throws IOException{
        String testInputSheetPath = FileReaderManager.getInstance().getConfiReader().getInputSheetPath();
        // String sheetName = FileReaderManager.getInstance().getConfiReader().getSheetName();
        String sheetName = testContext.getMethodName();
        ExcelReader xl = new ExcelReader(testInputSheetPath);
        int flagYesCount = 0;
        int i=0;
        flagYesCount = xl.getCountofValuesInSheet(sheetName, "Flag", "YES");
        Object [][] obj = new Object[flagYesCount][1];
        for(int row = 2;row<=xl.getRowCount(sheetName);row++){
            Map<Object,Object> dataObj = new HashMap<Object,Object>();
            if(xl.getCellData(sheetName, "Flag", row).equalsIgnoreCase("YES")){
                for(int col = 1;col<=xl.getColumnCount(sheetName);col++){
                    dataObj.put(xl.getCellData(sheetName, col, 1), xl.getCellData(sheetName, col, row));
                }
                obj[i][0] = dataObj;
                i++;
            }
        }
        return Obj;
    }
    
}
