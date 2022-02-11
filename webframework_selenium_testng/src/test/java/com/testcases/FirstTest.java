package com.testcases;

import java.io.IOException;
import java.util.Map;

import com.framework.TestSetup;
import com.pages.LoginPage;
import com.util.Data_Supplier;

import org.testng.annotations.Test;

public class FirstTest extends TestSetup {
    LoginPage loginPage;

    @Test(dataProvider = "testdata",dataProviderClass = Data_Supplier.class)
    public void firstTestCase(Map dataMap) throws InterruptedException,IOException{
        reportMessage = report.createTest((String) dataMap.get("TestCaseName"));
        loginPage = new LoginPage(driver, reportMessage);
        loginPage.enterUserName("DemoName");
    }
}
