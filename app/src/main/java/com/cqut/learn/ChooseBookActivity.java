package com.cqut.learn;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cqut.learn.Util.DataBaseTransToLocal;
import com.cqut.learn.Util.LearnManager;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyJsonParser;

import org.litepal.LitePal;

import java.util.Timer;
import java.util.TimerTask;

public class ChooseBookActivity extends BaseActivity implements View.OnClickListener,MyJsonParser.WordParseListener{
    /*
     *@className:ChooseBookActivity
     *@Description:选择单词书
     *@author:lixin
     *@Date:2020/10/23 22:39
     */
    private TextView textView_days;
    private Button  bt_cet4;
    private Button bt_gec;
    private Button bt_ielts;
    private Button bt_next;
    private Button bt_plan10;
    private Button bt_plan20;
    private Button bt_plan30;
    private TextView text_Title;
    private TextView text_progress;
    private ProgressBar bar;
    private TextView text_message;
    private LearnManager manager;
    private boolean nextButtonEnableFlag_1,isNextButtonEnableFlag_2;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_book);
        manager=new LearnManager(this);
        if (manager.getPrefs().getBoolean("isFirst",true)||manager.isPlanChanged()){
            new DataBaseTransToLocal().openDatabase(this);
            manager.saveLastTime();
            manager.setPlanChanged(false);//计划改变复位
            initView();
            manager.editPrefs("isFirst",false);
            manager.setTotalCounts(3645);
            manager.editPrefs(LearnManager.currentGroupId,0);
            manager.editPrefs(LearnManager.currentWordId,0);
        }else{
            Intent in=new Intent(this,MainActivity.class);
            startActivity(in);
            finish();
        }

    }
    private void initView(){
        /*
         *@className:ChooseBookActivity
         *@Description:初始化布局
         *@author:lixin
         *@Date:2020/10/25 12:49
         */
        bt_cet4=findViewById(R.id.activity_choose_book_button_cet4);
        bt_gec=findViewById(R.id.activity_choose_book_button_gec);
        bt_ielts=findViewById(R.id.activity_choose_book_button_ielts);
        bt_next=findViewById(R.id.activity_choose_book_button_next);
        textView_days=findViewById(R.id.activity_choose_text_days);
        bt_plan10=findViewById(R.id.activity_choose_book_button_plan_10);
        bt_plan30=findViewById(R.id.activity_choose_book_button_plan_30);
        bt_plan20=findViewById(R.id.activity_choose_book_button_plan_20);
        bt_next.setOnClickListener(this);
        bt_cet4.setOnClickListener(this);
        bt_gec.setOnClickListener(this);
        bt_ielts.setOnClickListener(this);
        bt_plan10.setOnClickListener(this);
        bt_plan30.setOnClickListener(this);
        bt_plan20.setOnClickListener(this);
        bt_next.setEnabled(false);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_choose_book_button_cet4:
                bt_cet4.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_gec.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_ielts.setBackgroundColor(getColor(R.color.colorTextBackground));
                manager.editPrefs("bookName",Constant.book_cet4);
                nextButtonEnableFlag_1=true;
                break;
            case R.id.activity_choose_book_button_gec:
                bt_cet4.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_gec.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_ielts.setBackgroundColor(getColor(R.color.colorTextBackground));
                nextButtonEnableFlag_1=true;
                manager.editPrefs("bookName",LearnManager.book_gec);
                break;
            case R.id.activity_choose_book_button_ielts:
                bt_cet4.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_gec.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_ielts.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                nextButtonEnableFlag_1=true;
                manager.editPrefs("bookName",LearnManager.book_ielts);
                break;
            case R.id.activity_choose_book_button_plan_10:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackground));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((373+" 天")); }
                manager.editPrefs(LearnManager.countOfGroup,LearnManager.day_plan_10);
                break;
            case R.id.activity_choose_book_button_plan_20:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackground));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((186)+" 天"); }
                manager.editPrefs("dayPlan",LearnManager.day_plan_20);
                break;
            case R.id.activity_choose_book_button_plan_30:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((124+" 天")); }
                manager.editPrefs("dayPlan",LearnManager.day_plan_30);
                break;
            case R.id.activity_choose_book_button_next:
//                View view= LayoutInflater.from(this).inflate(R.layout.alert_dialog_view,null);
//                text_message=view.findViewById(R.id.alert_dialog_view_message);
//                bar=view.findViewById(R.id.alert_dialog_progress);
//                text_Title=view.findViewById(R.id.alert_dialog_title);
//                text_progress=view.findViewById(R.id.alert_dialog_text_progress);
//                AlertDialog.Builder builder=new AlertDialog.Builder(this);
//                builder.setView(view);
//                builder.setCancelable(false);
//                Dialog dialog=builder.create();
//                dialog.show();
//                text_Title.setText("正在解析单词数据这个过程可能需要几分钟");
//                MyJsonParser.setWordParseListener(this);
//                MyJsonParser.start(this,"CET4luan_2.json",LitePal.count("CET4"));
                Intent in=new Intent(this,MainActivity.class);
                startActivity(in);
                finish();
                  break;
            default:break;
        }
    }

    @Override
    public void onWordParsedOver(final int count) {
        Intent in=new Intent(this,MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onWordParsed(final String word, final int index) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (text_message!=null) {
                    text_message.setText(word);
                    bar.setProgress(index);
                    text_progress.setText(index+"/"+bar.getMax());
                }
            }
        });

    }


}
