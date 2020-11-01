package com.cqut.learn.LitePalDB;

import org.litepal.crud.LitePalSupport;

public class Cognate extends LitePalSupport {
    /*
     *@className:Cognate
     *@Description:同根词
     *@author:lixin
     *@Date:2020/10/26 10:46
     */
        private String pos;//词性
        private String p_Content;//单词内容
        private String p_Cn;//翻译
        private int theId;
        private CET4 cet4;
    public Cognate(String p_Cn, String p_Content, String pos,CET4 cet4) {
        this.cet4=cet4;
        this.p_Content = p_Content;
        this.p_Cn = p_Cn;
        this.pos = pos;
    }
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
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

    public int getTheId() {
        return theId;
    }

    public void setTheId(int theId) {
        this.theId = theId;
    }

    public CET4 getCet4() {
        return cet4;
    }

    public void setCet4(CET4 cet4) {
        this.cet4 = cet4;
    }

}
