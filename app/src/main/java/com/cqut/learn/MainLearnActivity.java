package com.cqut.learn;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cqut.learn.CustomView.Image3DSwitchView;
import com.cqut.learn.CustomView.Image3DView;
import com.cqut.learn.CustomView.MyScrollView;
import com.cqut.learn.CustomView.MyTextView;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Cognate;
import com.cqut.learn.LitePalDB.Phrase;
import com.cqut.learn.LitePalDB.Sentence;
import com.cqut.learn.LitePalDB.Syno;
import com.cqut.learn.LitePalDB.Translate;
import com.cqut.learn.Util.BitmapToRound;
import com.cqut.learn.Util.LearnManager;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyJsonParser;
import com.cqut.learn.Util.MyTimer;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainLearnActivity extends BaseActivity implements View.OnClickListener,MyTimer.TimerListener {
    /*
     *@className:MainLearnActivity
     *@Description:展示单词学习界面
     *@author:lixin
     *@Date:2020/10/25 12:47
     */
         private int pageFrom;//用于区分是该学的单词还是搜索或喜欢界面传入的单词
         private int displayWordId;//其他页面的wordid
        //trans
        private MyScrollView scrollView;
        private Image3DSwitchView image3DSwitchView;//3d图片父布局
        private HandleImage bitmapReadyListener;//监听图片是否准备好了
        private RelativeLayout title;//title根布局
        private TextView title_text_word;//标题栏上的单词
        private TextView content_text_word;//content里面的单词
        private TextView content_text_uk_phone;//英式英标
        private TextView content_text_us_phone;//美式英标
        private MyTextView content_trans_content;//翻译
        private ImageView content_speaker_uk;//英式发音
        private ImageView content_speaker_us;//美式发音
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
        private ImageView content_trans_like;
        private Image3DView image3DView1;
        private Image3DView image3DView2;
        private Image3DView image3DView3;
        private Image3DView image3DView4;
        private Image3DView image3DView5;
        //fragment
        private EditText edit_comment;
        private Button bt_comment;
        private CommentFragment commentFragment;
        private List<CET4> cet4s;//currentWordGroup
        private CET4 cet4;//当前显示的单词
        private LearnManager manager;
        //播放音频
        private MediaPlayer media;
        //定时器
        private MyTimer myTimer;//记录停留在当前页面的时间
        private TextView text_time;//显示时间
        private int t;//记录从开始按下下一个按钮的时间
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        //初始化
        initView();
        media=new MediaPlayer();
        manager=new LearnManager(this);
        myTimer=new MyTimer();
        displayWordId =getIntent().getIntExtra(Constant.WORD_ID,-1);
        pageFrom=getIntent().getIntExtra(Constant.WHICH_PAGE,0x33);
        if(pageFrom==Constant.FROM_OTHER&&displayWordId!=-1){
            myTimer.schedule(1000,1000,this);
            cet4s= LitePal.where("wordId=?", displayWordId +"").find(CET4.class);
            updateView(cet4s.get(0));
            if (bt_next.getVisibility()==View.VISIBLE)
            bt_next.setVisibility(View.GONE);//显示搜索界面的单词不能选择下一个
       }else {
            myTimer.enableTimer();
            myTimer.schedule(1000,1000,this);
            if (bt_next.getVisibility()==View.GONE)
                bt_next.setVisibility(View.VISIBLE);//显示学习界面的单词选择下一个
            int currentWordIndex=manager.getCurrentWordId();
            if (currentWordIndex!=-1){
            cet4s= manager.getCET4Group();
            updateView(cet4s.get(currentWordIndex));
            }
       }
        //轮播图
        {
            //定时器
            Timer timer = new Timer();//初始化定时器
            //定时任务，切换图片
            MyTimerTask task = new MyTimerTask();
            timer.schedule(task, 2000, 2000);//2000ms循环播放
        }
       //fragment
        bt_comment=findViewById(R.id.activity_learn_comment);
        edit_comment=findViewById(R.id.activity_learn_comment_editor);

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentWordIndex;
                if (pageFrom==Constant.FROM_MAIN){
                currentWordIndex=manager.getCurrentWordId();
                }else {currentWordIndex=0;}

                commentFragment=new CommentFragment(cet4s.get(currentWordIndex));
              addFragment(R.id.activity_main_fragment,commentFragment);
            }
        });

    }
    public void addFragment(@IdRes int convertView, @NonNull Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if (fragment.isAdded()){return;}
        transaction.add(convertView,fragment);
        transaction.commit();
    }
    public void removeFragment(@NonNull Fragment fragment){
            if (fragment!=null&&fragment.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }
    }
    private void initView(){
        bt_next=findViewById(R.id.activity_learn_next);//下一个单词
        bt_next.setOnClickListener(this);
        //标题栏返回按钮
        ImageView title_back = findViewById(R.id.activity_learn_title_back);
        text_time=findViewById(R.id.activity_learn_title_time_text);
        title_back.setOnClickListener(this);
        initTranslateView();
        initCognateView();
        initPhrase();
        initSynoView();
        initSentence();
        initImage();
    }
    private void updateView(CET4 cet4){
            cet4.getComments().get(0).setContent(cet4.getTranslates().get(0).getP_Content()+"\ngood good study day day up!");
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
        //收藏按钮
        content_trans_like = findViewById(R.id.activity_learn_content_trans_likes);
        content_trans_like.setOnClickListener(this);
        title_text_word =findViewById(R.id.activity_learn_title_text);
        scrollView=findViewById(R.id.activity_learn_content_scroll);
        scrollView.setOnScrollChangeListener(null);//设置为空即可
        content_text_word=findViewById(R.id.activity_learn_content_trans_text_word);
        content_text_uk_phone=findViewById(R.id.activity_learn_content_uk_phone);
        content_text_uk_phone.setOnClickListener(this);
        content_text_us_phone=findViewById(R.id.activity_learn_content_us_phone);
        content_text_us_phone.setOnClickListener(this);
        content_trans_content=findViewById(R.id.activity_learn_content_trans_content);
        //英式发音
        content_speaker_uk = findViewById(R.id.activity_learn_content_trans_speaker_uk);
        content_speaker_uk.setOnClickListener(this);//发音
        //美式发音
        content_speaker_us = findViewById(R.id.activity_learn_content_trans_speaker_us);
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
        content_text_uk_phone.setText("["+cet4.getUk_phone()+"]");
        content_text_us_phone.setText("["+cet4.getUs_phone()+"]");
        if (cet4.isLike()) {
            content_trans_like.setImageResource(R.drawable.activity_learn_title_after_likes);
        }else {
            content_trans_like.setImageResource(R.drawable.activity_learn_title_before_likes);
        }
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
            builder.append(syno.getPos()).append(".").append(syno.getP_Content()).append(" ").append(syno.getP_Cn()).append("\n");
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
            builder.append(cognate.getPos()).append(".").append(cognate.getP_Content()).append(" ").append(cognate.getP_Cn()).append("\n");
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
            builder.append(sentence.getP_Content()).append(" ").append(sentence.getP_Cn()).append("\n");
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
                            bitmaps.add(BitmapToRound.GetRoundedCornerBitmap(resource,14));
                            if (bitmaps.size()>=5){
                                bitmapReadyListener.onReady(bitmaps);
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
    switch ( v.getId()){
        case R.id.activity_learn_next://下一个单词按钮点击后
            t=0;
            removeFragment(commentFragment);
            scrollView.scrollTo(0,0);
              doWhenNextButtonClicked();
            cet4s= manager.getCET4Group();
            updateView(cet4s.get(manager.getCurrentWordId()));
           break;
        case R.id.activity_learn_title_back://返回按钮点击后
            finish();
            break;
        case R.id.activity_learn_content_trans_likes:
            if (pageFrom==Constant.FROM_MAIN){
                   if (cet4s.get(manager.getCurrentWordId()).isLike()){
                   cet4s= manager.getCET4Group();
                   cet4s.get(manager.getCurrentWordId()).setLike(false);
                   updateView(cet4s.get(manager.getCurrentWordId()));
          }else {
                   cet4s= manager.getCET4Group();
                   cet4s.get(manager.getCurrentWordId()).setLike(true);
                   updateView(cet4s.get(manager.getCurrentWordId()));
                }
                                            }
         else{
             if (cet4s.get(0).isLike()){
                 cet4s.get(0).setLike(false);
                 updateView(cet4s.get(0));  }
               else {
                   cet4s.get(0).setLike(true);
                 updateView(cet4s.get(0));}
             }
         break;
        case R.id.activity_learn_content_uk_phone:
        case R.id.activity_learn_content_trans_speaker_uk:
            String url="https://dict.youdao.com/dictvoice?audio="+cet4s.get(manager.getCurrentWordId()).getHeadWord()+"&type=1";
            voice(url);
            break;
        case R.id.activity_learn_content_us_phone:
        case R.id.activity_learn_content_trans_speaker_us:
            String url1="https://dict.youdao.com/dictvoice?audio="+cet4s.get(manager.getCurrentWordId()).getHeadWord()+"&type=2";
            voice(url1);
            break;
         }
    }

    @SuppressLint("SetTextI18n")
    private void doWhenNextButtonClicked() {
            /*
            *@methodName:doWhenNextButtonClicked
            *@Description:当点击下一个单词后执行此方法
            *@author:lixin
            *@Date:2020/11/12 19:02
            *@Param:[]
            *@Return:void
            */
        int oldOne=manager.getCurrentWordId();
        if(oldOne==9) {

            if (manager.isNewDay()) {
                manager.setTaskIsOver(false);
                bt_next.setClickable(true);
                int oldGroupId = manager.getCurrentGroupId();
                manager.setCurrentGroupId(oldGroupId + 1);
                cet4s.clear();
                cet4s.addAll(manager.getCET4Group());
                manager.setCurrentWordId(0);
                updateView(cet4s.get(manager.getCurrentWordId()));
            }else {
                bt_next.setClickable(false);
                bt_next.setText("over!");
                manager.setTaskIsOver(true);
                MyDialog.showDayTaskOverInfo(this,"开心","今日任务已完成，快去复习吧！");
            }
        }else{
            manager.setCurrentWordId(oldOne+1);
            updateView(cet4s.get(manager.getCurrentWordId()));
            commentFragment = null;
            commentFragment = new CommentFragment(cet4s.get(manager.getCurrentWordId()));
        }
    }

    @Override
    public void run(final int ms, final int times) {

            if (pageFrom==Constant.FROM_MAIN) {
                CET4 cet4 = cet4s.get(manager.getCurrentWordId());
                int oldTime = cet4.getTime();
                cet4.setTime(oldTime + 1);//当前单词在该页面停留的时间
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        //每一秒执行一次该方法
                        int s = ms * times / 1000;
                        CET4 cet4 = cet4s.get(manager.getCurrentWordId());
                        cet4.setTime(s);//把时间保存
                        if (s < 60) {
                            text_time.setText(s + "''");
                        } else {
                            int min = s / 60;
                            s = s % 60;
                            text_time.setText(min + "'" + s + "''");
                        }
                        t++;
                        if (t < 10) {
                            bt_next.setClickable(false);
                            bt_next.setTextColor(Color.GRAY);
                            bt_next.setText(getResources().getString(R.string.nextOne) + "(" + (10 - t) + ")");
                        } else {
                            cet4s.get(manager.getCurrentWordId()).setLearned(true);//10秒完成算学习
                            cet4s.get(manager.getCurrentWordId()).setScore(20 + cet4s.get(manager.getCurrentWordId()).getTime() * 1 / 5);
                            bt_next.setClickable(true);
                            bt_next.setText(R.string.nextOne);
                            bt_next.setTextColor(getColor(R.color.colorText));
                        }
                    }
                });
            }else {
                //来自其他页面的单词
                CET4 cet4=manager.getWordById(displayWordId);
                int oldTime = cet4.getTime();
                cet4.setTime(oldTime + 1);//当前单词在该页面停留的时间
                if (cet4.getTime()>=10){
                    cet4.setLearned(true);
                }
            }
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
    private void voice(String o)
    {
        try
        {
            if (o != null)
            {
                media.reset();
                media.setDataSource(o);
                media.prepare();
                media.start();
            }

        } catch (IOException e)
        { e.printStackTrace();}
        catch (IllegalStateException e)
        { e.printStackTrace();}
    }
}
