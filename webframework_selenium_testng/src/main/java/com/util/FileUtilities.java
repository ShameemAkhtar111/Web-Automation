package com.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileDeleteStrategy;

public class FileUtilities {

    public static String createResultFolder(String resultFolder,String appName){
        SimpleDateFormat date = new SimpleDateFormat("MM_dd_yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh_mm_ss_aa");
        String strDate = date.format(new Date());
        String time = timeFormat.format(Calendar.getInstance().getTime());;

        String executionResFolder = resultFolder + "\\" +"ExecutionResults_" + strDate;
        File execResPath = new File(executionResFolder);
        if(!execResPath.exists()){
            if(!execResPath.mkdir()){
                throw new RuntimeException("Unable to create"+ executionResFolder + "Folder");
            }
        }
        String userExecResultFolder = executionResFolder + "\\" +appName+"_"+System.getProperty("user.name")+"_"+time;
        File userExecRes = new File(userExecResultFolder);
        if(!userExecRes.exists()){
            if(!userExecRes.mkdir()){
                throw new RuntimeException("Unable to create"+ userExecResultFolder + "Folder");
            }
        }
        return userExecResultFolder;
    }

    public static String createScreenShotFolder(String userExecResult){
        File screenShotFolder = new File(userExecResult + "\\" +"ScreenShots");
        if(!screenShotFolder.exists()){
            if(!screenShotFolder.mkdir()){
                throw new RuntimeException("Unable to create"+ screenShotFolder.toString() + "Folder");
            }
        }
        return screenShotFolder.toString();
    }
    
    public static void deleteAdditionalFolder(String userExcResPath) throws IOException{
        File fileToDelete = new File(userExcResPath+"\\ScreenShots\\");
        for(File files:fileToDelete.listFiles()){
            FileDeleteStrategy.FORCE.delete(files);
        }
        // fileToDelete.delete();
        FileDeleteStrategy.FORCE.delete(fileToDelete);

    }
}
