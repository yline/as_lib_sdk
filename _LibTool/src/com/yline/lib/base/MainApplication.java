package com.yline.lib.base;

import java.io.File;

import com.yline.application.AppConfig;
import com.yline.application.BaseApplication;

import android.os.Message;

/**
 * simple introduction
 *
 * @author YLine 2016-5-1 -> 下午3:29:09
 * @version 
 */
public class MainApplication extends BaseApplication
{
    
    @Override
    public void onCreate()
    {
        super.onCreate();
    }
    
    @Override
    protected void handlerDefault(Message msg)
    {
        
    }
    
    @Override
    protected AppConfig initConfig()
    {
        AppConfig appConfig = new AppConfig();
        appConfig.setFileLogPath("libTool" + File.separator);
        appConfig.setLogLocation(true);
        return appConfig;
    }
}
