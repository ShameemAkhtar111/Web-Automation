package com.pages;

import java.io.IOException;

import com.aventstack.extentreports.ExtentTest;
import com.framework.ElementFunctions;
import com.util.ReportUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends ElementFunctions {
    WebDriver driver;

    @FindBy(how = How.XPATH,using = "//input[@id='userName']")
    private WebElement userName;

    ExtentTest reportLogger;

    public LoginPage(WebDriver driver, ExtentTest reportObject){
        this.driver = driver;
        this.reportLogger = reportObject;
        PageFactory.initElements(driver, this);
    }
    
    public void enterUserName(String username) throws IOException{
        ReportUtils.compareStatusAndReport(setText(userName, username), true,
                                         "Entered username as: "+username,
                                         "Failed to enter username as: "+username,
                                          driver, reportLogger ,true);
    }
}
