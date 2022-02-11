package com.framework;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.util.Misc;
import com.util.ReportUtils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestSetup {
    protected static ExtentReports report = new ExtentReports();
    protected static ExtentTest reportMessage;
    protected static int tcPassed = 0;
    protected static int tcFailed = 0;
    protected static int implicitlyWait = 10;
    protected WebDriver driver=null;
    BrowserManager browserManager;

    @BeforeSuite
    public void suiteSetup() throws IOException{
        report = ReportUtils.getReportObject(Misc.checkIfExecIsDebuggingMode());
    }

    @BeforeMethod
    public void initializeDriver(){
        browserManager = new BrowserManager();
        driver = browserManager.getDriver();
    }

    @AfterMethod
    public void quitDriver(ITestResult result){
        if(result.getStatus() == ITestResult.SUCCESS){
            tcPassed +=1;
            reportMessage.log(Status.PASS, "Test Case Passed");
        }else if(result.getStatus() == ITestResult.FAILURE){
            try{
                tcFailed+=1;
                reportMessage.fail("Test Case Failed is "+result.getName(),
                 MediaEntityBuilder.createScreenCaptureFromPath(ReportUtils.captureScreenShot(driver)).build());
                reportMessage.fail(result.getThrowable());    
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        report.flush();
        driver.quit();
    }

    @AfterSuite
    public void suiteTearDown() throws IOException{
        report.flush();
        ReportUtils.sendResultsViaEmail(report);
    }
    
    
}
