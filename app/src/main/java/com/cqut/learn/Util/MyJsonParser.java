package com.cqut.learn.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Cognate;
import com.cqut.learn.LitePalDB.Comment;
import com.cqut.learn.LitePalDB.Phrase;
import com.cqut.learn.LitePalDB.Sentence;
import com.cqut.learn.LitePalDB.Syno;
import com.cqut.learn.LitePalDB.Translate;
import com.cqut.learn.R;
import com.cqut.learn.User.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyJsonParser {
    /*
     *@className:MyJsonParser
     *@Description:提供一些静态的方法方便调用去获得一个被解析的数据
     *@author:lixin
     *@Date:2020/10/25 19:50
     */
    public static Context context;
    public interface WordParseListener {
        /*
         *@className:WordParseListener
         *@Description:解析数据回调，用于界面更新
         *@author:lixin
         *@Date:2020/10/27 20:59
         */
        public void onWordParsedOver(int count);

        public void onWordParsed(String word, int index);
    }

    private static WordParseListener listener;

    public static void setWordParseListener(WordParseListener mlistener) {
        listener = mlistener;
    }
 public static interface GetImagePathListener{
        public void onSuccess(List<String> paths);
        public void onFailed(Exception e);
 }
    public static void getImagePath(String keyWord, final GetImagePathListener listener) {
        /*
        *@methodName:getImagePath
        *@Description:解析从网络读取的图片地址
        *@author:lixin
        *@Date:2020/10/28 16:01
        *@Param:[keyWord, listener]
        *@Return:void
        */
        final String path="https://pic.sogou.com/pics/json.jsp?query="+keyWord+"表情包";
                NetWork.connectNet(path, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {e.printStackTrace();
                    listener.onFailed(e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            listener.onSuccess(parseImageJson(response.body().string()));
                        }catch (Exception e){
                            listener.onFailed(e);
                        }
                    }
                });
    }



    public static List<String> parseImageJson(String jsonStr){
        /*
        *@methodName:parseImageJson
        *@Description:解析图片地址
        *@author:lixin
        *@Date:2020/10/28 16:02
        *@Param:[jsonStr]
        *@Return:java.util.List<java.lang.String>
        */
        JSONObject object=new JSONObject(jsonStr);
        List<String> paths=new ArrayList<>();
        JSONArray items=object.getJSONArray("items");
        for (int i=0;i<items.length();i++){
            JSONObject item=items.getJSONObject(i);
            if (item.has("picUrl")){
            String path=item.getString("picUrl");
                paths.add(path);
                if (paths.size()>=5){break;}
            }
        }
        return paths;
    }
    static  Bitmap bitmap;
    public static void start(final Context context, final String fileName, final int index){
        /**
        *@methodName:startAndReturnCET4
        *@Description:创建一个线程来解析json数据
        *@author:lixin
        *@Date:2020/10/25 20:26
        *@Param:[context, fileName]
        */
        bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.activity_main_navigation_icon1);
        MyJsonParser.context=context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                readFileWithLine(context,fileName,index);
                listener.onWordParsedOver(LitePal.count(CET4.class));
            }
        }).start();

    }

    public static void readFileWithLine(@NonNull Context context,String fileName,int index){
        int lines = 0;
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(context.getAssets().open(fileName), StandardCharsets.UTF_8);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            while ((line=bufferedReader.readLine())!=null){
                lines++;
                if (lines>=index) {//从断点开始解析
                    int began, end;
                    began = line.indexOf("{");
                    end = line.length();
                    if (began != -1 && line.length() > 3) {
                        String data = line.substring(began, end);
                        JsonParser(data);
                    }
                }
            }

            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    public static void JsonParser(@NonNull String jsonStr){
        /*
        *@methodName:JsonParser
        *@Description:Json解析器，解析一行json数据
        *@author:lixin
        *@Date:2020/10/25 20:01
        *@Param:[jsonStr]
        *@Return:void
        */

            CET4 cet4=new CET4();

            JSONObject jsonObject = new JSONObject(jsonStr);
            int wordRank = jsonObject.getInt("wordRank");
            cet4.setWordRank(wordRank);
            cet4.setWordId(wordRank);
            String bookId=jsonObject.getString("bookId");
            cet4.setBookId(bookId);
            cet4.setWordRank(wordRank);
            //System.out.println(wordRank);
            String headWord = jsonObject.getString("headWord");
            cet4.setHeadWord(headWord);
            //System.out.println(headWord);
            JSONObject content1 = jsonObject.getJSONObject("content");
            JSONObject word = content1.getJSONObject("word");
            JSONObject content = word.getJSONObject("content");
            if (content.has("usphone")&&content.has("ukphone")){
            String usPhone=content.getString("usphone");
            String ukPhone=content.getString("ukphone");
            cet4.setUk_phone(ukPhone);
            cet4.setUs_phone(usPhone);
            }else {
                return;//数据不完整直接返回
            }
            cet4.setLike(false);
            cet4.setTime(0);
            cet4.setScore(0);
            cet4.setLearned(false);
            JSONArray trans = content.getJSONArray("trans");
            //翻译
            if (!trans.isEmpty() || trans.length() >= 1) {
                List<Translate> translates=new ArrayList<>();
                for (int j = 0; j < trans.length(); j++) {
                    JSONObject object = (JSONObject) trans.get(j);
                   String cn = object.getString("tranCn");
                   String pos=object.getString("pos");
                   if(object.has("tranOther")){
                   String tranOther=object.getString("tranOther");
                   Translate translate=new Translate(pos,cn,tranOther,cet4);
                   translate.setTheId(wordRank);
                   translates.add(translate);
                   }
                    //System.out.println(cn);
                     }
                cet4.setTranslates(translates);
                //例句
                if(content.has("sentence")) {
                    JSONObject sentence=content.getJSONObject("sentence");
                    JSONArray sentences = sentence.getJSONArray("sentences");
                    List<Sentence> sentences1=new ArrayList<>();
                    for (int i = 0; i < sentences.length(); i++) {
                        JSONObject sen = sentences.getJSONObject(i);
                        String sContent = sen.getString("sContent");
                        String sCn = sen.getString("sCn");
                        //System.out.println("例句"+(i+1)+":"+sContent);
                        //System.out.println("例句翻译："+sCn);
                        Sentence sentence1=new Sentence(sContent,sCn,cet4);
                        sentence1.setTheId(wordRank);
                        sentences1.add(sentence1);
                    }
                    cet4.setSentences(sentences1);
                }
                //同义词
                if(content.has("syno")) {
                    JSONObject syno = content.getJSONObject("syno");
                    JSONArray synos = syno.getJSONArray("synos");
                    List<Syno> synoList=new ArrayList<>();
                    for (int i = 0; i < synos.length(); i++) {
                        JSONObject smlier = synos.getJSONObject(i);
                        String tran = smlier.getString("tran");
                        String pos=smlier.getString("pos");
                        JSONArray hwds = smlier.getJSONArray("hwds");
                        for (int j = 0; j < hwds.length(); j++) {
                            JSONObject w = hwds.getJSONObject(j);
                            String mw = w.getString("w");
                            Syno syno1=new Syno(pos,tran,mw,cet4);
                            syno1.setTheId(wordRank);
                            synoList.add(syno1);
                            //System.out.println("同义词 " + (j + 1) + ":" + mw+"--"+tran);
                        }
                    }
                    cet4.setSynos(synoList);
                }
                //同根词
                if (content.has("relWord")) {
                    JSONObject relWord = content.getJSONObject("relWord");
                    JSONArray rels = relWord.getJSONArray("rels");
                    List<Cognate> cognateList=new ArrayList<>();
                    for (int i = 0; i < rels.length(); i++) {
                        JSONObject object = rels.getJSONObject(i);
                        String pos = object.getString("pos");
                        JSONArray words = object.getJSONArray("words");
                        for (int j = 0; j < words.length(); j++) {
                            JSONObject object1 = words.getJSONObject(j);
                            String cn = object1.getString("tran");
                            String en = object1.getString("hwd");
                            Cognate cognate=new Cognate(cn,en,pos,cet4);
                            cognate.setTheId(wordRank);
                            cognateList.add(cognate);
                        }
                        cet4.setCognates(cognateList);
                    }
                }
                //短语
                if (content.has("phrase")) {
                    JSONObject phrase = content.getJSONObject("phrase");
                    JSONArray phrases = phrase.getJSONArray("phrases");
                    List<Phrase> phraseArrayList = new ArrayList<>();
                    for (int i = 0; i < phrases.length(); i++) {
                        JSONObject object = phrases.getJSONObject(i);
                        String cn = object.getString("pCn");
                        String en = object.getString("pContent");
                        Phrase phrase1 = new Phrase(en, cn, cet4);
                        phrase1.setTheId(wordRank);
                        phraseArrayList.add(phrase1);
                    }
                    cet4.setPhrases(phraseArrayList);

                }
                //模拟评论
                List<Comment> comments=new ArrayList<>();

                    Comment comment=new Comment();
                    comment.setContent(" ");
                    comment.setDate(new Date(System.currentTimeMillis()));
                    comment.setLike(true);
                    comment.setLikes(1000);
                    comment.setTheId(cet4.getWordId());
                    comments.add(comment);

                cet4.setComments(comments);
            }
            cet4.save();
        if (listener != null) {
            listener.onWordParsed(headWord, wordRank);
        }
    }

}
