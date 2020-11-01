package com.cqut.learn;

import android.app.Application;

import org.litepal.LitePal;

public class MyApplication extends Application {
    /*
     *@className:MyApplication
     *@Description:用于初始化数据库
     *@author:lixin
     *@Date:2020/10/25 17:31
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
