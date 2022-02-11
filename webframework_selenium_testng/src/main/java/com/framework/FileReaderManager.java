package com.framework;


import com.util.ConfigReader;

public class FileReaderManager {
    public static FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigReader configFileReader;

    private FileReaderManager(){

    }

    public static FileReaderManager getInstance(){
        return fileReaderManager;
    }
    public ConfigReader getConfiReader(){
        return (configFileReader == null) ? configFileReader = new ConfigReader(): configFileReader;
    }
    
}
