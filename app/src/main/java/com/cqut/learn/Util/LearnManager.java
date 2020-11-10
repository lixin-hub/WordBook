package com.cqut.learn.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.cqut.learn.LitePalDB.CET4;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LearnManager {
    public static String planChanged="planChanged";
    /*
     *@className:LearnManager
     *@Description:学习进度管理器，在baseActivity里面初始化，读取preference保存到静态变量，方便程序里面访问  key;
     *@author:lixin
     *@Date:2020/10/28 19:45
     */
    private Context context;
     public LearnManager(Context context){
      this.context=context;
      }
    public static String totalCounts="totalCounts";//词库的总数
    public static String currentWordId="currentWordId";//当前单词
    public static String currentGroupId="currentGroupId";//当前该学的单词组
    public static String totalLearnedCounts="totalLearnedCounts";//总共学习的单词数
    public static String countOfGroup="countOfGroup";//一组有多少个单词
    public static int book_gec=200;
    public static int book_ielts=300;
    public static int day_plan_10=10;
    public static int day_plan_20=20;
    public static int day_plan_30=30;

    public boolean setPlanChanged(boolean isChanged){
        /*
        *@methodName:setPlanChanged
        *@Description:设置计划是否改变
        *@author:lixin
        *@Date:2020/11/10 16:58
        *@Param:[isChanged]
        *@Return:boolean
        */
        return editPrefs(planChanged,isChanged);
    }

    public boolean isPlanChanged(){
        /*
        *@methodName:isPlanChanged
        *@Description:获取计划是否改变
        *@author:lixin
        *@Date:2020/11/10 17:00
        *@Param:[]
        *@Return:boolean
        */
        return getPrefs().getBoolean(planChanged,false);
    }
    public boolean setTotalLearnedCounts(int counts) {
        /*
        *@methodName:setTotalLearnedCounts
        *@Description:设置已经学过的单词数量
        *@author:lixin
        *@Date:2020/11/10 15:47
        *@Param:[counts]
        *@Return:boolean
        */
      return editPrefs(totalLearnedCounts,counts);
    }

    public  int getTotalLearnedCounts(){
        /*
        *@methodName:getTotalLearnedCounts
        *@Description:得到总共的已经学过的单词
        *@author:lixin
        *@Date:2020/11/10 15:43
        *@Param:[]
        *@Return:int
        */
         return getCountOfGroup()*getCurrentGroupId()+getCurrentWordId();
     }
     public boolean setCurrentWordId(int id){
        /*
        *@methodName:setCurrentWordId
        *@Description:设置当前进度下的单词id
        *@author:lixin
        *@Date:2020/11/10 15:45
        *@Param:[id]
        *@Return:boolean
        */
        return editPrefs(currentWordId,id);
     }
     public int getCurrentWordId(){
        /*
        *@methodName:getCurrentWordId
        *@Description:设置当前该学单词在单词组里面的id
        *@author:lixin
        *@Date:2020/11/10 15:48
        *@Param:[]
        *@Return:int
        */
        return getPrefs().getInt(currentWordId,-1);
     }

    public int getExtraDays() {
        /*
        *@methodName:getExtraDays
        *@Description:TOOD
        *@author:获取剩余的天数
        *@Date:2020/11/10 15:39
        *@Param:[]
        *@Return:int
        */
        return getTotalCounts()/getCountOfGroup();
    }

    public void setTotalCounts(int Counts){
        /*
        *@methodName:setTotalCounts
        *@Description:设置总共的单词数
        *@author:lixin
        *@Date:2020/11/10 15:22
        *@Param:[Counts]
        *@Return:void
        */
       if(!editPrefs(totalCounts,Counts)){
           Toast.makeText(context,"error"+this.getClass().toString(),Toast.LENGTH_LONG).show();
       }
    }
    public int getTotalCounts(){
        /*
        *@methodName:getToastCounts
        *@Description:获取总的单词数
        *@author:lixin
        *@Date:2020/11/10 15:37
        *@Param:[]
        *@Return:int
        */
        return getPrefs().getInt(totalCounts,0);
    }


    public int getCurrentGroupId(){
        /*
        *@methodName:getCurrentGroupId
        *@Description:得到当前单词组的id
        *@author:lixin
        *@Date:2020/11/10 15:22
        *@Param:[]
        *@Return:int
        */
       return getPrefs().getInt(currentGroupId,1);
    }
    public  int getCountOfGroup(){
        /*
        *@methodName:getCountOfGroup
        *@Description:得到计划中每一组的数量
        *@author:lixin
        *@Date:2020/11/10 15:23
        *@Param:[]
        *@Return:int
        */
        return getPrefs().getInt(countOfGroup,10);
    }


    public List<CET4> getCET4Group(){
        /*
        *@methodName:getCET4Group
        *@Description:得到当前进度下的单词组
        *@author:lixin
        *@Date:2020/11/10 15:23
        *@Param:[]
        *@Return:java.util.List<com.cqut.learn.LitePalDB.CET4>
        */
        return LitePal.limit(getCountOfGroup()).offset(getCurrentGroupId()*getCountOfGroup()).find(CET4.class);
    }

    public List<CET4> getCET4Group(int whichGroup){
        /*
        *@methodName:getCET4Group
        *@Description:得到指定单词组
        *@author:lixin
        *@Date:2020/11/10 15:24
        *@Param:[whichGroup]
        *@Return:java.util.List<com.cqut.learn.LitePalDB.CET4>
        */
        return LitePal.limit(getCountOfGroup()).offset(whichGroup*getCountOfGroup()).find(CET4.class);
    }

    public SharedPreferences getPrefs(){
        /**
        *@methodName:getPrefs
        *@Description:得到储存操作方法
        *@author:lixin
        *@Date:2020/11/10 15:24
        *@Param:[]
        *@Return:android.content.SharedPreferences
        */
        return context.getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
    }
    public SharedPreferences.Editor getEditor(){
        /*
        *@methodName:getEditor
        *@Description:得到储存编辑器
        *@author:lixin
        *@Date:2020/11/10 15:28
        *@Param:[]
        *@Return:android.content.SharedPreferences.Editor
        */
        return getPrefs().edit();
    }


    public boolean editPrefs(String key,int value){
        return getEditor().putInt(key,value).commit();
    }
    public boolean editPrefs(String key,String value){
        return getEditor().putString(key,value).commit();
    }
    public boolean editPrefs(String key,boolean value){
        return getEditor().putBoolean(key,value).commit();
    }

    public boolean setCurrentGroupId(int id) {
      return editPrefs(currentGroupId,id);
    }
}
