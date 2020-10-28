package com.cqut.learn;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cqut.learn.CustomView.Image3DView;
import com.cqut.learn.CustomView.MyScrollView;
import com.cqut.learn.CustomView.MyTextView;
import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.Cognate;
import com.cqut.learn.DataBase.Phrase;
import com.cqut.learn.DataBase.Sentence;
import com.cqut.learn.DataBase.Syno;
import com.cqut.learn.Util.BitmapToRound;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class MainLearnActivity extends BaseActivity implements View.OnClickListener {
    /*
     *@className:MainLearnActivity
     *@Description:展示单词学习界面
     *@author:lixin
     *@Date:2020/10/25 12:47
     */
    //trans
    private MyScrollView scrollView;
    private RelativeLayout title;//title根布局
    private TextView title_text_word;//标题栏上的单词
    private TextView content_text_word;//content里面的单词
    private TextView content_text_uk_phone;//英式英标
    private TextView content_text_us_phone;//美式英标
    //syno
    private MyTextView content_syno_text;//同义词翻译
    //cognate
    private MyTextView content_cognate_text;//同根词
    //phrase
    private MyTextView content_phrase_text;//短语
    //sentence
    private MyTextView content_sentence_text;//例句
    private Button bt_next;//下一个

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        Bitmap bitmap1= BitmapToRound.GetRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.con1));
        Bitmap bitmap2= BitmapToRound.GetRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.con2));
        Bitmap bitmap3= BitmapToRound.GetRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.con3));
        Bitmap bitmap4= BitmapToRound.GetRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.con4));

        Image3DView image3DView1=findViewById(R.id.activity_learn_content_3dswitch_1);
        image3DView1.setImageBitmap(bitmap1);
        Image3DView image3DView2=findViewById(R.id.activity_learn_content_3dswitch_2);
        Glide.with(this).load("https://5b0988e595225.cdn.sohucs.com/images/20190421/be80134bb4b04253806cb0690cdc90df.jpeg").into(image3DView2);
        Image3DView image3DView3=findViewById(R.id.activity_learn_content_3dswitch_3);
        image3DView3.setImageBitmap(bitmap3);

        Image3DView image3DView4=findViewById(R.id.activity_learn_content_3dswitch_4);
        image3DView4.setImageBitmap(bitmap4);

        Image3DView image3DView5=findViewById(R.id.activity_learn_content_3dswitch_5);
        image3DView5.setImageBitmap(bitmap3);



   List<CET4> cet4s= LitePal.limit(10).offset(200).find(CET4.class,true);
   for (CET4 cet4:cet4s){
       for (Cognate cognate:cet4.getCognates()){
           System.out.println(cognate.getPos()+":"+cognate.getP_Content()+":"+cognate.getP_Cn());
       }
   }

        //初始化
        initView();
       updateView(cet4s.get(2));
    }
    private void initView(){
        initTranslateView();
        initCognateView();
        initPhrase();
        initSynoView();
        initSentence();
    }
    private void updateView(CET4 cet4){
        updateTranslateView(cet4);
        updateCognate(cet4);
        updatePhrase(cet4);
        updateSynoView(cet4);
        updateSentence(cet4);
    }
    private void initTranslateView(){
        /*
        *@methodName:initView
        *@Description:初始化翻译
        *@author:lixin
        *@Date:2020/10/26 17:22
        *@Param:[]
        *@Return:void
        */
        title = findViewById(R.id.title);
        title_text_word =findViewById(R.id.activity_learn_title_text);
        scrollView=findViewById(R.id.activity_learn_content_scroll);
        scrollView.setOnScrollChangeListener(null);//设置为空即可
        content_text_word=findViewById(R.id.activity_learn_content_trans_text_word);
        content_text_uk_phone=findViewById(R.id.activity_learn_content_uk_phone);
        content_text_us_phone=findViewById(R.id.activity_learn_content_us_phone);
        //英式发音
        ImageView content_speaker_uk = findViewById(R.id.activity_learn_content_trans_speaker_uk);
        content_speaker_uk.setOnClickListener(this);//发音
        //美式发音
        ImageView content_speaker_us = findViewById(R.id.activity_learn_content_trans_speaker_us);
        content_speaker_us.setOnClickListener(this);//发音
    }
    @SuppressLint("SetTextI18n")
    private void updateTranslateView(CET4 cet4){
        /*
        *@methodName:upDateTranslateView
        *@Description:更新化同义词
        *@author:lixin
        *@Date:2020/10/26 17:22
        *@Param:[cet4]
        *@Return:void
        */
        scrollView.setTitle(title, title_text_word,cet4.getHeadWord(),window);//更新标题栏单词
        content_text_word.setText(cet4.getHeadWord());
        title_text_word.setText(cet4.getHeadWord());
        content_text_uk_phone.setText("["+cet4.getUk_phone()+"]");
        content_text_us_phone.setText("["+cet4.getUs_phone()+"]");
    }
    private void initSynoView(){
        /*
        *@methodName:initSynoView
        *@Description:初始化同义词
        *@author:lixin
        *@Date:2020/10/26 17:23
        *@Param:[]
        *@Return:void
        */
        content_syno_text=findViewById(R.id.activity_learn_content_syno_trans);
    }
    private void updateSynoView(CET4 cet4){
        /*
        *@methodName:upDateSynOView
        *@Description:更新同义词
        *@author:lixin
        *@Date:2020/10/26 17:24
        *@Param:[]
        *@Return:void
        */
        StringBuilder builder=new StringBuilder();
        for (Syno syno:cet4.getSynos()){
            builder.append(syno.getPos()+"."+syno.getP_Content()+" "+syno.getP_Cn()+"\n");
        }
         content_syno_text.setMyText(builder.toString());
    }
    private void initCognateView(){
        /*
        *@methodName:initCognateView
        *@Description:初始化同根词
        *@author:lixin
        *@Date:2020/10/26 17:32
        *@Param:[]
        *@Return:void
        */
        content_cognate_text=findViewById(R.id.activity_learn_content_cognate);
    }
    private void updateCognate(CET4 cet4){
        /*
        *@methodName:updateCognate
        *@Description:更新同根词
        *@author:lixin
        *@Date:2020/10/26 17:36
        *@Param:[cet4]
        *@Return:void
        */
        StringBuilder builder=new StringBuilder();
        for (Cognate cognate:cet4.getCognates()){
            builder.append(cognate.getPos()+"."+cognate.getP_Content()+" "+cognate.getP_Cn()+"\n");
        }
        content_cognate_text.setMyText(builder.toString());
    }
    private void initPhrase(){
        /*
        *@methodName:initPhrase
        *@Description:初始化短语
        *@author:lixin
        *@Date:2020/10/26 17:38
        *@Param:[]
        *@Return:void
        */
        content_phrase_text=findViewById(R.id.activity_learn_content_phrase_text);
    }
    private void updatePhrase(CET4 cet4){
        /*
        *@methodName:updatePhrase
        *@Description:更新短语
        *@author:lixin
        *@Date:2020/10/26 17:40
        *@Param:[]
        *@Return:void
        */
        StringBuilder builder=new StringBuilder();
        for (Phrase phrase:cet4.getPhrases()){
            builder.append(phrase.getP_Content()+" "+phrase.getP_Cn()+"\n");
        }
        content_phrase_text.setMyText(builder.toString());
    }
    private void initSentence(){
        /*
        *@methodName:initSentence
        *@Description:初始化例句
        *@author:lixin
        *@Date:2020/10/26 17:44
        *@Param:[]
        *@Return:void
        */
        content_sentence_text=findViewById(R.id.activity_learn_content_sentence_text);
    }
    private void updateSentence(CET4 cet4){
        /*
        *@methodName:updateSentence
        *@Description:更新例句
        *@author:lixin
        *@Date:2020/10/26 17:46
        *@Param:[cet4]
        *@Return:void
        */
        StringBuilder builder=new StringBuilder();
        for (Sentence sentence:cet4.getSentences()){
            builder.append(sentence.getP_Content()+" "+sentence.getP_Cn()+"\n");
        }
        content_sentence_text.setMyText(builder.toString());
    }
    @Override
    public void onClick(View v) {

    }

}
