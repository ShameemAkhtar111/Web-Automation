package com.framework;

import com.aventstack.extentreports.ExtentTest;
import com.pages.LoginPage;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private LoginPage loginPage;

    public PageObjectManager(WebDriver driver){
        this.driver = driver;
    }

    public LoginPage getLoginPage(ExtentTest reportLogger){
        return (loginPage == null) ? loginPage = new LoginPage(driver,reportLogger) : loginPage;
    }
    
}
