package com.cqut.learn.User;

import java.util.Date;

public class Comment
{
    /*
     *@className:Comment
     *@Description:评论对象，从网络数据读取
     *@author:lixin
     *@Date:2020/10/28 22:27
     */
    private String headWord;//该评论对应的单词
    private User user;//该条评论对应的用户
    private int likes;//点赞数量
    private String content;//评论内容
    private boolean isLike;//是否喜欢
    public boolean isLike() {
        return isLike;
    }
    public void setLike(boolean like) {
        isLike = like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private Date date;//评论时间；
}
