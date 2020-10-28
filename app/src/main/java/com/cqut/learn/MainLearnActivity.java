package com.cqut.learn;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cqut.learn.CustomView.Image3DSwitchView;
import com.cqut.learn.CustomView.Image3DView;
import com.cqut.learn.CustomView.MyScrollView;
import com.cqut.learn.CustomView.MyTextView;
import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.Cognate;
import com.cqut.learn.DataBase.Phrase;
import com.cqut.learn.DataBase.Sentence;
import com.cqut.learn.DataBase.Syno;
import com.cqut.learn.DataBase.Translate;
import com.cqut.learn.Util.BitmapToRound;
import com.cqut.learn.Util.MyJsonParser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainLearnActivity extends BaseActivity implements View.OnClickListener  {
    /*
     *@className:MainLearnActivity
     *@Description:展示单词学习界面
     *@author:lixin
     *@Date:2020/10/25 12:47
     */
    //trans
    private MyScrollView scrollView;
    private Image3DSwitchView image3DSwitchView;//3d图片父布局
    private MyTimerTask task;//定时任务，切换图片
    private Timer timer;//定时器
    private HandleImage bitmapReadyListener;//监听图片是否准备好了
    private RelativeLayout title;//title根布局
    private TextView title_text_word;//标题栏上的单词
    private TextView content_text_word;//content里面的单词
    private TextView content_text_uk_phone;//英式英标
    private TextView content_text_us_phone;//美式英标
    private MyTextView content_trans_content;//翻译
    //syno
    private MyTextView content_syno_text;//同义词翻译
    //cognate
    private MyTextView content_cognate_text;//同根词
    //phrase
    private MyTextView content_phrase_text;//短语
    //sentence
    private MyTextView content_sentence_text;//例句
    private Button bt_next;//下一个
    //image
    Image3DView image3DView1;
    Image3DView image3DView2;
    Image3DView image3DView3;
    Image3DView image3DView4;
    Image3DView image3DView5;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

   List<CET4> cet4s= LitePal.limit(10).offset(20).find(CET4.class,true);
   //初始化
        timer=new Timer();//初始化定时器
        task=new MyTimerTask();
        timer.schedule(task,2000,2000);//2000ms循环播放
        initView();
       updateView(cet4s.get(2));
    }
    private void initView(){
        initTranslateView();
        initCognateView();
        initPhrase();
        initSynoView();
        initSentence();
        initImage();
    }
    private void updateView(CET4 cet4){
        updateTranslateView(cet4);
        updateCognate(cet4);
        updatePhrase(cet4);
        updateSynoView(cet4);
        updateSentence(cet4);
        updateImage(cet4);
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
        content_trans_content=findViewById(R.id.activity_learn_content_trans_content);
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
        StringBuilder builder=new StringBuilder();
        for (Translate translate:cet4.getTranslates()){
            builder.append(translate.getPos()).append(translate.getP_Cn()).append("\n").append(translate.getP_Content());}
        content_trans_content.setMyText(builder.toString());
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
    private void initImage(){
     bitmapReadyListener=new HandleImage();
     image3DSwitchView=findViewById(R.id.image_switch_view);
     image3DView1=findViewById(R.id.activity_learn_content_3dswitch_1);
     image3DView2=findViewById(R.id.activity_learn_content_3dswitch_2);
     image3DView3=findViewById(R.id.activity_learn_content_3dswitch_3);
     image3DView4=findViewById(R.id.activity_learn_content_3dswitch_4);
     image3DView5=findViewById(R.id.activity_learn_content_3dswitch_5);
    }
    private void updateImage(CET4 cet4){
        final List<Bitmap> bitmaps=new ArrayList<>();
        MyJsonParser.getImagePath(cet4.getTranslates().get(0).getP_Cn(), new MyJsonParser.GetImagePathListener() {
            @Override
            public void onSuccess(List<String> paths) {
                for (String path:paths){
                    Glide.with(MainLearnActivity.this).asBitmap().load(path).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bitmaps.add(BitmapToRound.GetRoundedCornerBitmap(resource));
                            if (bitmaps.size()>=5){
                                bitmapReadyListener.onReady(bitmaps);
                                return;
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailed(Exception e) {

            }});

    }
    @Override
    public void onClick(View v) {

    }
class HandleImage implements BitmapReadyListener{
        /*
         *@className:HandleImage
         *@Description:将图片显示上去
         *@author:lixin
         *@Date:2020/10/28 17:43
         */
    @Override
    public void onReady(List<Bitmap> bitmaps) {

        image3DView1.setImageBitmap(bitmaps.get(0));
        image3DView2.setImageBitmap(bitmaps.get(1));
        image3DView3.setImageBitmap(bitmaps.get(2));
        image3DView4.setImageBitmap(bitmaps.get(3));
        image3DView5.setImageBitmap(bitmaps.get(4));
    }
}
class MyTimerTask extends TimerTask{
/*
 *@className:MyTimerTask
 *@Description:定时切换图片
 *@author:lixin
 *@Date:2020/10/28 19:01
 */
    @Override
    public void run() {
        image3DSwitchView.scrollToNext();
    }
}
    public  interface BitmapReadyListener{
        /*
         *@className:BitmapReadyListener
         *@Description:监听图片是否准备好
         *@author:lixin
         *@Date:2020/10/28 17:26
         */
       void onReady(List<Bitmap> bitmaps);
     }
}
