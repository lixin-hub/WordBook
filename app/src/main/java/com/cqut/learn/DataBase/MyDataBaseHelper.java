package com.cqut.learn.DataBase;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public  class MyDataBaseHelper{
    public static List<CET4> query(String conditions,String conditionsValue,int limit,int offset){//查询多少个单词limit，从第几I个开始查找offset
        List<CET4> cet4List= LitePal.limit(limit).offset(offset).where(conditions,conditionsValue).find(CET4.class);
        for (CET4 cet4:cet4List){
            List<Sentence> sentenceList= LitePal.where("theId=?",cet4.getWordId()+"").find(Sentence.class);
            cet4.setSentences(sentenceList);
//            for (Sentence sentence:sentenceList){
//                System.out.println(sentence.getP_Cn()+":"+sentence.getP_Content());
//            }
            List<Phrase> phraseList= LitePal.where("theId=?",cet4.getWordId()+"").find(Phrase.class);
            cet4.setPhrases(phraseList);
//            for (Phrase phrase:phraseList){
//                System.out.println(phrase.getP_Cn()+":"+phrase.getP_Content());
//            }
            List<Syno> synoList= LitePal.where("theId=?",cet4.getWordId()+"").find(Syno.class);
            cet4.setSynos(synoList);
//            for (Syno syno:synoList){
//                System.out.println(syno.getP_Cn()+":"+syno.getP_Content());
//            }
            List<Translate> translateList= LitePal.where("theId=?",cet4.getWordId()+"").find(Translate.class);
            cet4.setTranslates(translateList);
//            for (Translate translate:translateList){
//                System.out.println(translate.getP_Cn()+":"+translate.getP_Content());
//            }
            List<Cognate> cognates= LitePal.where("theId=?",cet4.getWordId()+"").find(Cognate.class);
            cet4.setCognates(cognates);
//            for (Cognate cognate:cognates){
//                System.out.println(cognate.getP_Cn()+":"+cognate.getP_Content());
//            }
        }
        return cet4List;
    }
    public static List<CET4> query(int limit,int offset){//查询多少个单词limit，从第几I个开始查找offset
        List<CET4> cet4List= LitePal.limit(limit).offset(offset).find(CET4.class);
        for (CET4 cet4:cet4List){
            List<Sentence> sentenceList= LitePal.where("theId=?",cet4.getWordId()+"").find(Sentence.class);
            cet4.setSentences(sentenceList);
//            for (Sentence sentence:sentenceList){
//                System.out.println(sentence.getP_Cn()+":"+sentence.getP_Content());
//            }
            List<Phrase> phraseList= LitePal.where("theId=?",cet4.getWordId()+"").find(Phrase.class);
            cet4.setPhrases(phraseList);
//            for (Phrase phrase:phraseList){
//                System.out.println(phrase.getP_Cn()+":"+phrase.getP_Content());
//            }
            List<Syno> synoList= LitePal.where("theId=?",cet4.getWordId()+"").find(Syno.class);
            cet4.setSynos(synoList);
//            for (Syno syno:synoList){
//                System.out.println(syno.getP_Cn()+":"+syno.getP_Content());
//            }
            List<Translate> translateList= LitePal.where("theId=?",cet4.getWordId()+"").find(Translate.class);
            cet4.setTranslates(translateList);
//            for (Translate translate:translateList){
//                System.out.println(translate.getP_Cn()+":"+translate.getP_Content());
//            }
            List<Cognate> cognates= LitePal.where("theId=?",cet4.getWordId()+"").find(Cognate.class);
            cet4.setCognates(cognates);
//            for (Cognate cognate:cognates){
//                System.out.println(cognate.getP_Cn()+":"+cognate.getP_Content());
//            }
        }
        return cet4List;
    }
    public static List<CET4> queryAll(){//查询全部
        List<CET4> cet4List= LitePal.findAll(CET4.class);
        for (CET4 cet4:cet4List){
            List<Sentence> sentenceList= LitePal.where("theId=?",cet4.getWordId()+"").find(Sentence.class);
            cet4.setSentences(sentenceList);
//            for (Sentence sentence:sentenceList){
//                System.out.println(sentence.getP_Cn()+":"+sentence.getP_Content());
//            }
            List<Phrase> phraseList= LitePal.where("theId=?",cet4.getWordId()+"").find(Phrase.class);
            cet4.setPhrases(phraseList);
//            for (Phrase phrase:phraseList){
//                System.out.println(phrase.getP_Cn()+":"+phrase.getP_Content());
//            }
            List<Syno> synoList= LitePal.where("theId=?",cet4.getWordId()+"").find(Syno.class);
            cet4.setSynos(synoList);
//            for (Syno syno:synoList){
//                System.out.println(syno.getP_Cn()+":"+syno.getP_Content());
//            }
            List<Translate> translateList= LitePal.where("theId=?",cet4.getWordId()+"").find(Translate.class);
            cet4.setTranslates(translateList);
//            for (Translate translate:translateList){
//                System.out.println(translate.getP_Cn()+":"+translate.getP_Content());
//            }
            List<Cognate> cognates= LitePal.where("theId=?",cet4.getWordId()+"").find(Cognate.class);
            cet4.setCognates(cognates);
//            for (Cognate cognate:cognates){
//                System.out.println(cognate.getP_Cn()+":"+cognate.getP_Content());
//            }
        }
        return cet4List;
    }
    public static List<CET4> query(String conditions,String conditionsValue){//通过条件查询
        List<CET4> cet4List= LitePal.where(conditions,conditionsValue).find(CET4.class);
        for (CET4 cet4:cet4List){
            List<Sentence> sentenceList= LitePal.where("theId=?",cet4.getWordId()+"").find(Sentence.class);
            cet4.setSentences(sentenceList);
//            for (Sentence sentence:sentenceList){
//                System.out.println(sentence.getP_Cn()+":"+sentence.getP_Content());
//            }
            List<Phrase> phraseList= LitePal.where("theId=?",cet4.getWordId()+"").find(Phrase.class);
            cet4.setPhrases(phraseList);
//            for (Phrase phrase:phraseList){
//                System.out.println(phrase.getP_Cn()+":"+phrase.getP_Content());
//            }
            List<Syno> synoList= LitePal.where("theId=?",cet4.getWordId()+"").find(Syno.class);
            cet4.setSynos(synoList);
//            for (Syno syno:synoList){
//                System.out.println(syno.getP_Cn()+":"+syno.getP_Content());
//            }
            List<Translate> translateList= LitePal.where("theId=?",cet4.getWordId()+"").find(Translate.class);
            cet4.setTranslates(translateList);
//            for (Translate translate:translateList){
//                System.out.println(translate.getP_Cn()+":"+translate.getP_Content());
//            }
            List<Cognate> cognates= LitePal.where("theId=?",cet4.getWordId()+"").find(Cognate.class);
            cet4.setCognates(cognates);
//            for (Cognate cognate:cognates){
//                System.out.println(cognate.getP_Cn()+":"+cognate.getP_Content());
//            }
        }
        return cet4List;
    }

}
