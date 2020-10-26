package com.cqut.learn.DataBase;

import org.litepal.crud.LitePalSupport;

public class Phrase extends LitePalSupport {
    /*
     *@className:Phrase
     *@Description:短语
     *@author:lixin
     *@Date:2020/10/25 20:38
     */
    private String p_Content;//英文短语
    private String p_Cn;//汉语意
    private CET4 CET4;
    private int theId;
    public int getTheId() {
        return theId;
    }

    public void setTheId(int theId) {
        this.theId = theId;
    }


    public Phrase(String p_Content, String p_Cn,CET4 CET4) {
        this.p_Content = p_Content;
        this.p_Cn = p_Cn;
        this.CET4 = CET4;
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


    public com.cqut.learn.DataBase.CET4 getCET4() {
        return CET4;
    }

    public void setCET4(com.cqut.learn.DataBase.CET4 CET4) {
        this.CET4 = CET4;
    }




}
