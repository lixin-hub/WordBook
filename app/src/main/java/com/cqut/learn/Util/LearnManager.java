package com.cqut.learn.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cqut.learn.LitePalDB.CET4;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LearnManager {
    /*
     *@className:LearnManager
     *@Description:学习进度管理器，在baseActivity里面初始化，读取preference保存到静态变量，方便程序里面访问  key;
     *@author:lixin
     *@Date:2020/10/28 19:45
     */



    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void setPreferences(SharedPreferences preferences) {
        LearnManager.preferences = preferences;
    }

    public static void setEditor(SharedPreferences.Editor editor) {
        LearnManager.editor = editor;
    }
    public static int totalCounts=3739;//词库的总数
    public static String currentWordId="currentWordId";//当前单词
    public static String currentGroupId="currentGroupId";//当前该学的单词组
    public static String totalLearnedCounts="totalLearnedCounts";//总共学习的单词数
    public static String countOfGroup="countOfGroup";//一组有多少个单词
    private static List<CET4> ce4Group;//该学的单词组
    public static List<CET4> getCe4Group() {
        return LitePal.limit(preferences.getInt(countOfGroup,10)
                             ).offset(preferences.getInt(currentGroupId,0)*preferences.getInt(currentGroupId,0)).find(CET4.class);
    }

    public static void setCe4Group(List<CET4> ce4Group) {
        LearnManager.ce4Group = ce4Group;
    }

     public static int getTotalLearnedCounts(){
        int count=preferences.getInt(countOfGroup,10)*preferences.getInt(currentGroupId,0)+preferences.getInt(currentWordId,0);
        editor.putInt(totalLearnedCounts,count);
        editor.commit();
        return count;
     }
    public static int getExtraDays() {
        return (totalCounts-getTotalLearnedCounts())/preferences.getInt(countOfGroup,10);
    }
}
