package com.cqut.learn.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetWork {

 /*
  *@className:NetWork
  *@Description:网络相关
  *@author:lixin
  *@Date:2020/10/26 11:36
  */



 public static void connectNet(final String path, final Callback callback){
     new Thread(new Runnable() {
         @Override
         public void run() {
             OkHttpClient client=new OkHttpClient();
             Request request=new Request.Builder().url(path).build();
             client.newCall(request).enqueue(callback);
         }
     }).start();

 }

    public static boolean isNetworkAvailable(Context context) {
        /*
        *@methodName:isNetworkAvailable
        *@Description:判断网络是否可用
        *@author:lixin
        *@Date:2020/10/26 11:37
        *@Param:[context]
        *@Return:boolean true表示可以
        */
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
