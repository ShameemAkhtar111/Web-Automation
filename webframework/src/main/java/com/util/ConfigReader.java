package com.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.enums.DriverType;
import com.enums.EnvironmentType;

public class ConfigReader {
    private Properties properties;
    private final String configFilePath = "\\src\\main\\resources\\config.properties";

    public ConfigReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + configFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("config.properties not found at "+configFilePath);
        }
    }

    public String getAppName(){
        String appName = properties.getProperty("appName");
        if(appName!=null) return appName;
        else throw new RuntimeException("App Name not specified in the config.properties file.");
    }

    public DriverType getBrowser(){
        String browserName = properties.getProperty("browser");
        if(browserName==null || browserName.equalsIgnoreCase("chrome")) return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
        else if(browserName.equalsIgnoreCase("iexplorer")) return DriverType.INTERNETEXPLORER;
        else throw new RuntimeException("Browser Name key value in the config.properties is not matched: "+browserName);
    }
    public EnvironmentType getEnvironment(){
        String environmentName = properties.getProperty("execEnvironment");
        if(environmentName==null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL;
        else if (environmentName.equalsIgnoreCase("remote")) return EnvironmentType.REMOTE;
        else throw new RuntimeException("Environment Type key value in the config.properties is not matched: "+environmentName);
    }
    public String getDriverPath(){
        String driverPath = properties.getProperty("driverPath");
        if(driverPath!=null) return driverPath;
        else return "";
    }
    public String getApplicationUrl(){
        String url = properties.getProperty("appUrl");
        if(url!=null) return url;
        else throw new RuntimeException("App Url not specified in the config.properties file");
    }
    public String getUserId(){
        String uId = properties.getProperty("userId");
        if(uId!=null) return uId;
        else throw new RuntimeException("User id not specified in the config.properties file");
    }
    public String getPassword(){
        String pass = properties.getProperty("password");
        if(pass!=null) return pass;
        else throw new RuntimeException("Password not specified in the config.properties file");
    }
    public String getInputSheetPath(){
        String inputSheetPath = properties.getProperty("inputSheetPath");
        if(inputSheetPath!=null) return inputSheetPath;
        else throw new RuntimeException("Input sheet path not specified in the config.properties file");
    }
    public String getSheetName(){
        String sheetName = properties.getProperty("sheetName");
        if(sheetName!=null) return sheetName;
        else throw new RuntimeException("Sheet name not specified in the config.properties file");
    }
    public String getTestEnvironment(){
        String testEnv = properties.getProperty("testEnvironment");
        if(testEnv!=null) return testEnv;
        else throw new RuntimeException("Test environment not specified in the config.properties file");
    }
    public String getRecipientEmailIds(){
        String recpEmail = properties.getProperty("recipientEmailIds");
        if(recpEmail!=null) return recpEmail;
        else throw new RuntimeException("Recipient email ids not specified in the config.properties file");
    }
    public int getImplicitWait(){
        String implicitWait = properties.getProperty("implicitWait");
        if(implicitWait!=null) return Integer.parseInt(implicitWait);
        else return 20;
    }
}
