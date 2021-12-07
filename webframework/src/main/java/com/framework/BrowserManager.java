package com.framework;

import java.util.concurrent.TimeUnit;

import com.enums.DriverType;
import com.enums.EnvironmentType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserManager {
    private WebDriver driver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
    private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";

    public BrowserManager(){
        driverType = FileReaderManager.getInstance().getConfiReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfiReader().getEnvironment();
    }

    public WebDriver getDriver(){
        if(driver==null) driver = createDriver();
        return driver;
    }

    public WebDriver createDriver(){
        switch(environmentType){
            case LOCAL : driver = createLocalDriver();
                break;
            case REMOTE : driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    public WebDriver createLocalDriver(){
        String webDriverPath = FileReaderManager.getInstance().getConfiReader().getDriverPath();
        switch(driverType){
            case FIREFOX :
                if(webDriverPath.isBlank()||webDriverPath.isEmpty()||webDriverPath == null){
                    WebDriverManager.firefoxdriver().setup();
                }else{
                    System.setProperty(FIREFOX_DRIVER_PROPERTY, webDriverPath);
                }
                driver = new FirefoxDriver();
                break;
            case CHROME : 
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito");
                options.addArguments("--ignore-ssl-error=yes");
                options.addArguments("--ignore-cretificate-errors");
                options.addArguments("--allow-insecure-localhost");
                if(webDriverPath.isBlank()||webDriverPath.isEmpty()||webDriverPath == null){
                    WebDriverManager.chromedriver().setup();
                }else{
                    System.setProperty(CHROME_DRIVER_PROPERTY, webDriverPath);
                }
                driver = new ChromeDriver(options);
                break;
            case INTERNETEXPLORER :
                if(webDriverPath.isBlank()||webDriverPath.isEmpty()||webDriverPath == null){
                    WebDriverManager.iedriver().setup();
                }else{
                    System.setProperty(IE_DRIVER_PROPERTY, webDriverPath);
                }
                driver = new InternetExplorerDriver();
                break;
            
            default : throw new RuntimeException("Please specify correct browserType i.e chrome,firefox or iexplorer");
        }

        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        return driver;
    }
    
    public WebDriver createRemoteDriver(){
        throw new RuntimeException("Remote web driver not implemented right now");
    }

    public void quitDriver(){
        driver.quit();
    }
}
