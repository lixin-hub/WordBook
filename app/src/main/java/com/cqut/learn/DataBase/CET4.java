package com.cqut.learn.DataBase;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class CET4 extends LitePalSupport {


    /*
     *@className:CET4
     *@Description:英语四级单词数据库对象
     *@author:lixin
     *@Date:2020/10/25 17:40
     */
   private int wordId;//单词id
    private int wordRank;//单词序列
   private int likes;//点赞的次数
   private int score;//单词的分数
   private int degree;//单词的等级（难度级别）
   private String bookId;//所属单词书
   private String headWord;//单词拼写；
   private String us_phone;//美式音标
   private String uk_phone;//英式英标
   private List<Phrase> phrases=new ArrayList<>();//短语
   private List<Syno> synos=new ArrayList<>();//同近单词
   private List<Translate> translates=new ArrayList<>();//翻译列表
   private List<Sentence> sentences=new ArrayList<>();//例句列表

    public List<Cognate> getCognates() {
        return cognates;
    }

    public void setCognates(List<Cognate> cognates) {
        this.cognates = cognates;
    }

    private List<Cognate> cognates=new ArrayList<>();//同根词列表


    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getWordRank() {
        return wordRank;
    }

    public void setWordRank(int wordRank) {
        this.wordRank = wordRank;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getHeadWord() {
        return headWord;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public String getUs_phone() {
        return us_phone;
    }

    public void setUs_phone(String us_phone) {
        this.us_phone = us_phone;
    }

    public String getUk_phone() {
        return uk_phone;
    }

    public void setUk_phone(String uk_phone) {
        this.uk_phone = uk_phone;
    }
    public List<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public List<Syno> getSynos() {
        return synos;
    }

    public void setSynos(List<Syno> synos) {
        this.synos = synos;
    }

    public List<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(List<Translate> translates) {
        this.translates = translates;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

}
