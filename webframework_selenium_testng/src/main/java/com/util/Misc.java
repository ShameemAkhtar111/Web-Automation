package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class Misc {
    public static String getCurrentDate(){
        SimpleDateFormat dt_Time = new SimpleDateFormat("MM/dd/yyyy");
        return dt_Time.format(new Date());
    }

    public static String getDateFormatted(String date,String format) throws java.text.ParseException{
        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
        SimpleDateFormat dtFormat = new SimpleDateFormat(format);
        return dtFormat.format(date1);
    }

    public static String getNewDate(int numOfDaysToAdd){
        SimpleDateFormat formateDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(formateDate.parse(getCurrentDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_MONTH,numOfDaysToAdd);

        String newDate = formateDate.format(cal.getTime());
        return newDate;
    }
    
    public static int getWebTableRowsCount(WebElement webTable){
        try {
            List<WebElement> totalRows = webTable.findElements(By.tagName("tr"));
            int totalRowCount = totalRows.size();
            return totalRowCount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getWebTableColHeadingCount(WebElement webTable){
        try {
            List<WebElement> totalThead = webTable.findElements(By.tagName("th"));
            int totalTheadCount = totalThead.size();
            return totalTheadCount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getWebTableColumnCount(WebElement webTable){
        try {
            List<WebElement> totalColumns = webTable.findElements(By.tagName("td"));
            int totalColumnCount = totalColumns.size();
            return totalColumnCount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int checkWebTableInputTags(WebElement webTable){
        try {
            int totInpTagCount = 0;
            List<WebElement> totalinputTags = webTable.findElements(By.tagName("input"));
            totInpTagCount = totalinputTags.size();
            return totInpTagCount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getColNumberOfHeading(List<WebElement> headingList,String reqHeadingName){
        try {
            int indexVal = -1;
            for(int i=0;i<headingList.size();i++){
                String headingText = headingList.get(i).getText();
                if(headingText.equalsIgnoreCase(reqHeadingName)){
                    indexVal = i;
                    break;
                }
            }
            return indexVal;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Boolean checkIfFileExists(String completeFilePathWithNameExt){
        Boolean status;
        try {
            File f = new File(completeFilePathWithNameExt);
            if(f.exists()){
                status = true;
            }else{
                status = false;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    public static String getFileCreationDate(String completeFilePathWithNameExt){
        String creationDate = null;
        try {
            File file = new File(completeFilePathWithNameExt);
            Path filePath = file.toPath();
            BasicFileAttributes view = Files.getFileAttributeView(filePath, BasicFileAttributeView.class).readAttributes();
            String sdate = view.creationTime().toString();
            String tempDate[] = sdate.split("T");
            creationDate = tempDate[0];
        } catch (Exception e) {
            e.printStackTrace();
            creationDate = "";
        }
        return creationDate;
    }

    public static String getColumnNamesFromCSV(String completeFilePathWithNameExt) throws FileNotFoundException{
        String columnNames = null;
        try {
            BufferedReader brReader = new BufferedReader(new FileReader(completeFilePathWithNameExt));
            String text;
            text = brReader.readLine();
            text = text.replace(",", "|");
            columnNames = text;
            brReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    public static String checkIfExecIsDebuggingMode(){
        String value = null;
        try {
            int debugVal = JOptionPane.showConfirmDialog(null, "Is this execution part of debugging?", "Debugg Prompt", JOptionPane.YES_NO_OPTION);
            switch(debugVal){
                case JOptionPane.YES_OPTION:
                    value = "Yes";
                    break;
                case JOptionPane.NO_OPTION:
                    value = "No";
                    break;
                default:
                    value = "Invalid";
                    break;
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return value;
    }

    public static String getFakeCreditCardNumber(String cardTypes){
        String creditCardNum = "";
        try {
            Faker fake = new Faker();
            if(cardTypes.equalsIgnoreCase("visa")){
                creditCardNum = fake.finance().creditCard(CreditCardType.VISA);
            }else if(cardTypes.equalsIgnoreCase("mastercard")){
                creditCardNum = fake.finance().creditCard(CreditCardType.MASTERCARD);
            }else if(cardTypes.equalsIgnoreCase("americanexpress")){
                creditCardNum = fake.finance().creditCard(CreditCardType.AMERICAN_EXPRESS);
            }else if(cardTypes.equalsIgnoreCase("laser")){
                creditCardNum = fake.finance().creditCard(CreditCardType.LASER);
            }else if(cardTypes.equalsIgnoreCase("random")){
                creditCardNum = fake.finance().creditCard();
            }else{
                creditCardNum = "";
            }

            if(creditCardNum.contains("-")){
                creditCardNum = creditCardNum.replace("-", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creditCardNum;
    }

    public static String getFakeName(){
        String name="";
        try {
            Faker fake = new Faker();
            String fname = fake.name().firstName();
            String lname = fake.name().lastName();
            name = fname+" "+lname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getFakeCrediCardExpiryDate(int startYear,int endYear){
        String expDate="";
        try {
            String month;
            String year;
            Faker fake = new Faker();
            int mnth = fake.number().numberBetween(01, 13);
            if((mnth/10)==0){
                month = "0"+String.valueOf(mnth);
            }else{
                month = String.valueOf(mnth);
            }
            int yr = fake.number().numberBetween(startYear, endYear);
            year = String.valueOf(yr);
            expDate = month+"/"+year;
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return expDate;
    }    
}
