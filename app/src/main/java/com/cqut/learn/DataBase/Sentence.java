package com.cqut.learn.DataBase;

import org.litepal.crud.LitePalSupport;

public class Sentence extends LitePalSupport {
    /*
     *@className:Sentence
     *@Description:例句
     *@author:lixin
     *@Date:2020/10/25 20:37
     */
    private String p_Content;//例句中文翻译
    private String p_Cn;//英文例句
    private CET4 cet4;//单词的引用
    private int theId;
    public int getTheId() {
        return theId;
    }

    public void setTheId(int theId) {
        this.theId = theId;
    }


    public String getP_Content() {
        return p_Content;
    }

    public void setP_Content(String p_Content) {
        this.p_Content = p_Content;
    }

    public String getP_Cn() {
        return p_Cn;
    }

    public void setP_Cn(String p_Cn) {
        this.p_Cn = p_Cn;
    }

    public CET4 getCet4() {
        return cet4;
    }

    public void setCet4(CET4 cet4) {
        this.cet4 = cet4;
    }

    public Sentence(String p_Content, String p_Cn, CET4 cet4) {
        this.p_Content = p_Content;
        this.p_Cn = p_Cn;
        this.cet4 = cet4;
    }
}
