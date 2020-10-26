package com.cqut.learn.DataBase;

import org.litepal.crud.LitePalSupport;

public class Syno extends LitePalSupport {
    /*
     *@className:Syno
     *@Description:同义词
     *@author:lixin
     *@Date:2020/10/25 20:39
     */
    private String pos;//词性
    private String p_Cn;//汉语意思
    private String p_Content;//单词
    private CET4 cet4;//单词引用
    private int theId;
    public int getTheId() {
        return theId;
    }

    public void setTheId(int theId) {
        this.theId = theId;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getP_Cn() {
        return p_Cn;
    }

    public void setP_Cn(String p_Cn) {
        this.p_Cn = p_Cn;
    }

    public String getP_Content() {
        return p_Content;
    }

    public void setP_Content(String p_Content) {
        this.p_Content = p_Content;
    }

    public CET4 getCet4() {
        return cet4;
    }

    public void setCet4(CET4 cet4) {
        this.cet4 = cet4;
    }

    public Syno(String pos, String p_Cn, String p_Content, CET4 cet4) {
        this.pos = pos;
        this.p_Cn = p_Cn;
        this.p_Content = p_Content;
        this.cet4 = cet4;
    }
}
