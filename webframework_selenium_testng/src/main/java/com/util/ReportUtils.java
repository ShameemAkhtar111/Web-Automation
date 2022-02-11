package com.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.FileReaderManager;
import com.sun.jna.platform.win32.Secur32Util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.sun.jna.platform.win32.Secur32;

public class ReportUtils {
    public static ExtentReports extent;
    public static ExtentTest testReport;
    public static String baseResultDir = null;
    public static String userExecResultPath = null;
    public static String screenShotFolder = null;
    public static String htmlResultUrl = null;

    public static ExtentReports getReportObject(String debugYesNo) throws IOException{
        String userName = Secur32Util.getUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameDisplay);
        if(debugYesNo.equalsIgnoreCase("Yes")){
            baseResultDir = "\\abc\\debuggResults";
        }else if(debugYesNo.equalsIgnoreCase("No")){
            baseResultDir = "\\abc\\resPath";
        }else{
            throw new RuntimeException("Invalid value detected on debugg prompt dailogue box");
        }
        userExecResultPath = FileUtilities.createResultFolder(baseResultDir, FileReaderManager.getInstance().getConfiReader().getAppName());
        screenShotFolder = FileUtilities.createScreenShotFolder(userExecResultPath);
        htmlResultUrl = userExecResultPath + "\\" + "ExecutionResult.html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(htmlResultUrl);
        reporter.config().setReportName(FileReaderManager.getInstance().getConfiReader().getAppName()+"-Automation Execution Result");
        reporter.config().setDocumentTitle(FileReaderManager.getInstance().getConfiReader().getAppName()+"-Automation Execution Result");
        reporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(reporter);

        try {
            extent.setSystemInfo("Machine Name: ", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Architecture", System.getProperty("os.arch"));
        extent.setSystemInfo("Test data sheet path :", FileReaderManager.getInstance().getConfiReader().getInputSheetPath());
        return extent;
    }
    
    public static String getCurrentDateTime(){
        SimpleDateFormat dt_Time = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss_SSSS");
        return dt_Time.format(new Date());
    }

    public static String captureScreenShot(WebDriver driver){
        File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenShotPath = screenShotFolder;
        String screenshotPath = screenShotPath + "\\Pic_" + getCurrentDateTime() + ".png";

        try {
            FileUtils.copyFile(scr, new File(screenshotPath));
        } catch (IOException e) {
            //TODO: handle exception
            System.out.println("unable to take screenshot"+e.getMessage());
        }

        return screenshotPath;
    }

    public static void logMessage(ExtentTest testCase,String msg,String stepStatus,Boolean screenshotTrueOrFalse, 
        WebDriver driver) throws IOException{
        if(stepStatus.equalsIgnoreCase("pass")){
            if(screenshotTrueOrFalse == true){
                testCase.pass(msg, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                testCase.pass(msg);
            }
        }else if(stepStatus.equalsIgnoreCase("fail")){
            if(screenshotTrueOrFalse == true){
                testCase.fail(msg, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                testCase.fail(msg);
            }
        }else if(stepStatus.equalsIgnoreCase("info")){
            if(screenshotTrueOrFalse == true){
                testCase.info(msg, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                testCase.info(msg);
            }
        }else{
            throw new RuntimeException("Invalid step status");
        }
            

    }

    public static void compareStatusAndReport(boolean actualStatus,boolean expectedStatus,String strPassMessage,
    String strFailMessage,WebDriver driver,ExtentTest objExtentTest,boolean screenShotTrueOrFalse)
    throws IOException{
        if(actualStatus == expectedStatus){
            if(screenShotTrueOrFalse == true){
                objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                objExtentTest.pass(strPassMessage);
            }
        }else{
            if(screenShotTrueOrFalse == true){
                objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                objExtentTest.pass(strPassMessage);
            }
        }
        Assert.assertEquals(actualStatus,expectedStatus);
    }

    public static void compareTextAndReport(String actualText,String expectedText,String strPassMessage,
    String strFailMessage,WebDriver driver,ExtentTest objExtentTest,boolean screenShotTrueOrFalse)
    throws IOException{
        if(actualText.equals(expectedText)){
            if(screenShotTrueOrFalse == true){
                objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                objExtentTest.pass(strPassMessage);
            }
        }else{
            if(screenShotTrueOrFalse == true){
                objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
            }else{
                objExtentTest.pass(strPassMessage);
            }
        }
        Assert.assertEquals(actualText,expectedText);
    }

    public static void compareIntegerAndReport(int actualVal,int expectVal,String strPassMessage,
        String strFailMessage,WebDriver driver,ExtentTest objExtentTest,boolean screenShotTrueOrFalse)
        throws IOException{
            if(actualVal == expectVal){
                if(screenShotTrueOrFalse == true){
                    objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
                }else{
                    objExtentTest.pass(strPassMessage);
                }
            }else{
                if(screenShotTrueOrFalse == true){
                    objExtentTest.pass(strPassMessage, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
                }else{
                    objExtentTest.pass(strPassMessage);
                }
            }
            Assert.assertEquals(actualVal,expectVal);
        }

    public static void sendResultsViaEmail(ExtentReports extent) throws UnknownHostException{
        String fullName = Secur32Util.getUserNameEx(Secur32.EXTENDED_NAME_FORMAT.NameDisplay);

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", "mail.aflac.com");

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress("abc@gmail.com"));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(FileReaderManager.getInstance().getConfiReader().getRecipientEmailIds()));
            
            message.setSubject(FileReaderManager.getInstance().getConfiReader().getAppName() + " Test Execution Result by: "+
            fullName.replace("- Contractor", ""));

            String httpResultURL = htmlResultUrl.replace("\\\\dcaalm03\\results\\", "http://dcaalm03:81/");
            String updatedResultUrl = httpResultURL.replace("\\", "/");

            String stats = "<b style='font-family:Times New Roman;font-size:15px;'>Test Summary:</b>" + 
            "</br>Passed TCs    :"
            + extent.getStats().getParentCountPass() +"</br> Failed TCs    : "
            + extent.getStats().getParentCountFail() +"</br> Warning TCs    : "
            + extent.getStats().getParentCountWarning() + "</br> Skipped TCs    :"
            + extent.getStats().getParentCountSkip() +"</br><b>Total    :"
            + extent.getStats().getParentCount() +"</b></br>"+"<hr>";

            String env = "<b style='font-family:Times New Roman;font-size: 15px;'> Test Parameters:</b>"+
            "</br>Executed By   : "+fullName.replace("- Contractor", "") +
            "</br>E/V Id    : "+System.getProperty("user.name") +
            "</br>Machine   : "+InetAddress.getLocalHost().getHostName() +
            "</br>Operating System: "+System.getProperty("os.name") +
            "</br>OS Version    : "+System.getProperty("os.version") + "</br><hr>";

            String header = "</br><b style='color:red;font-family:Times New Roman;font-size:15px;'>"
                            +"Autogenerated Email. Please do not reply.</b></br></br><hr>";

            message.setContent(
                header +"</br></br>"+env+"</br></br>"+ stats + "</br></br>"+"<b style='font-family:Times New Roman;font-size:15px;'>"
                +"Execution Result URL:</b> </br></br> "+htmlResultUrl , "text/html");
            
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
