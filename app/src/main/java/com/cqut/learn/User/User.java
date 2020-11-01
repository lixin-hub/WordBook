package com.cqut.learn.User;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;

import com.cqut.learn.Util.PictureUtil;

import java.util.Date;

public class User {
    public User(String userName, Date time, Bitmap headImage) {
        this.userName = userName;
        this.time = time;
        this.headImage = PictureUtil.convertIconToString(headImage);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLearningBook() {
        return learningBook;
    }

    public void setLearningBook(String learningBook) {
        this.learningBook = learningBook;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMal() {
        return mal;
    }

    public void setMal(String mal) {
        this.mal = mal;
    }

    public Bitmap getHeadImage() {
        return PictureUtil.convertStringToIcon(this.headImage);
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
     *@className:User
     *@Description:用户bean
     *@author:lixin
     *@Date:2020/10/28 22:14
     */
    private String userName;//用户名
    private String learningBook;//正在学习的单词书
    private Date time;//注册的时间
    private String phoneNumber;
    private String mal;//邮箱
    private String headImage;//头像，以字符串的形式储存
    private int id;//唯一标识
}
