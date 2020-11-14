package com.cqut.learn.Util;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer{
    /*
     *@className:MyTimer
     *@Description:封装定时器
     *@author:lixin
     *@Date:2020/11/12 19:48
     */
    public interface TimerListener{
        /*
         *@className:TimerListener
         *@Description:定时监听器
         *@author:lixin
         *@Date:2020/11/12 19:48
         */
         void run(int ms,int times);
    }
    private Timer timer;
    private TimerTask task;
    private long startTime,endTime,pastTime;
    private int times=0;
    public void schedule(final int msTime, final TimerListener listener){
        if (listener==null){ Log.e(this.toString(),"定时器监听器不能为空"); return;}
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                times++;
                listener.run(msTime,times);
            }
        };
        timer.schedule(task,msTime);
    }
   public void cancel(){timer.cancel();}
    public MyTimer schedule(final int msTime,int msDelay, final TimerListener listener){
        if (listener==null){ Log.e(this.toString(),"定时器监听器不能为空"); return null;}
        timer=new Timer();

        task=new TimerTask() {
            @Override
            public void run() {
                times++;
                listener.run(msTime,times);
            }
        };
        timer.schedule(task,msTime,msDelay);
        return this;
    }


    public long enableTimer(){
        startTime=-1;
        startTime=System.currentTimeMillis();
        return startTime;
    }
    public long getPastTime(){
        pastTime=-1;
        pastTime=System.currentTimeMillis()-startTime;
        return pastTime;
    }
    public long getEndTime(){
        return System.currentTimeMillis();
    }

}
