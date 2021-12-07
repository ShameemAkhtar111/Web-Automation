package com.framework;

import java.awt.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.impl.regex.REUtil;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverInfo;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementFunctions {

    public boolean setText(WebElement element,String textToWrite){
        try{
            element.sendKeys(textToWrite);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void clear(WebElement element){
        try {
            element.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEnabled(WebElement element){
        return element.isEnabled();
    }

    public boolean click(WebElement element){
        try{
            element.click();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSelected(WebElement element){
        return element.isSelected();
    }

    public boolean isDisplayed(WebElement element){
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }catch(TimeoutException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isDisplayedInTime(WebElement element,WebDriver driver,int waitTime){
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        } catch(TimeoutException e){
            e.printStackTrace();
            return false;
        }
    }

    // public boolean areElementsVisible(List<WebElement> ele,WebDriver driver,int waitTime){
    //     driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
    //     try {
    //         if(ele.size()!=0){
    //             return true;
    //         }else{
    //             return false;
    //         }

    //     } catch (Exception e) {
    //         //TODO: handle exception
    //         e.printStackTrace();
    //         return false;
    //     }finally{
    //         driver.manage().timeouts().implicitlyWait(TestSetup.implicitWait, TimeUnit.SECONDS);
    //     }
    // }

    public boolean isElementVisibleAndClickable(WebElement we,WebDriver driver,int waitTime){
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        try{
            we.isDisplayed();
            wait.until(ExpectedConditions.elementToBeClickable(we));
            return true;
        }catch(org.openqa.selenium.NoSuchElementException e){
            e.printStackTrace();
            return false;
        }catch(TimeoutException e){
            e.printStackTrace();
            return false;
        }
    }

    public int isElementDisabled(WebElement we){
        try{
            if(we.isEnabled()){
                return -1;
            }else{
                return 1;
            }
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public int areElementsDisabled(java.util.List<WebElement> webList){
        int check =0;
        try{
            if(webList.size()!=0){
                for(WebElement webRdBtn : webList){
                    if(webRdBtn.getAttribute("disabled").equalsIgnoreCase("false")){
                        check++;
                    }
                }
            }else{
                return 0;
            }
            return check;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isAlertPresentInTime(WebDriver driver,int waitTime){
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        boolean presentFlag = false;
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            presentFlag = true;
        }catch(NoAlertPresentException e){
            e.printStackTrace();
        }catch(TimeoutException e){
            e.printStackTrace();
        }
        return presentFlag;
    }

    public boolean clickUsingActions(WebDriver driver,WebElement element){
        Actions action = new Actions(driver);
        try {
            action.click(element).build().perform();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setTextUsingActions(WebDriver driver,WebElement element,String data){
        Actions action = new Actions(driver);
        try{
            action.sendKeys(element,data).build().perform();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
