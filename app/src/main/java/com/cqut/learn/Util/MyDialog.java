package com.cqut.learn.Util;

import android.app.AlertDialog;
import android.content.Context;
import com.cqut.learn.R;

public class MyDialog {
    /*
     *@className:MyDialog
     *@Description:快捷显示自定义的dialog'
     *@author:lixin
     *@Date:2020/10/24 13:47
     */
    public static void showWordInfo(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.search);
        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失
        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事
        dialog.show();                            //显示对话框
    }
    public static void showDayTaskOverInfo(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(R.drawable.happy);
        builder.setCancelable(true);            //点击对话框以外的区域是否让对话框消失
        AlertDialog dialog = builder.create();      //创建AlertDialog对象
        //对话框显示的监听事
        dialog.show();                            //显示对话框
    }
}
