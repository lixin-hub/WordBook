package com.cqut.learn.Util;

import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.Word;

import java.util.List;

public class LearnManager {
    /*
     *@className:LearnManager
     *@Description:学习进度管理器，在baseActivity里面初始化，读取preference保存到静态变量，方便程序里面访问
     *@author:lixin
     *@Date:2020/10/28 19:45
     */
    public static Word currentBook;//当前该学的单词
    public static List<Word> currentWordGroup;//当前该学的单词组
    public static int dayLearnedCounts;//今天已经学的单词
    public static int totalLearnedCounts;//总共学习的单词数
}
