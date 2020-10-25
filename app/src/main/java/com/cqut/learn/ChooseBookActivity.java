package com.cqut.learn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseBookActivity extends BaseActivity implements View.OnClickListener {
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
    private SharedPreferences.Editor editor;
    private boolean nextButtonEnableFlag_1,isNextButtonEnableFlag_2;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_book);
        SharedPreferences preferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        editor= preferences.edit();
        if (preferences.getBoolean("isFirst",true)){
            initView();
            editor.putBoolean("isFirst",false);
        }else {
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
                editor.putInt("bookName",Constant.book_cet4);
                nextButtonEnableFlag_1=true;
                break;
            case R.id.activity_choose_book_button_gec:
                bt_cet4.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_gec.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_ielts.setBackgroundColor(getColor(R.color.colorTextBackground));
                nextButtonEnableFlag_1=true;
                editor.putInt("bookName",Constant.book_gec);
                break;
            case R.id.activity_choose_book_button_ielts:
                bt_cet4.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_gec.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_ielts.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                nextButtonEnableFlag_1=true;
                editor.putInt("bookName",Constant.book_ielts);
                break;
            case R.id.activity_choose_book_button_plan_10:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackground));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((373+" 天")); }
                editor.putInt("dayPlan",Constant.day_plan_10);
                break;
            case R.id.activity_choose_book_button_plan_20:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackground));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((186)+" 天"); }
                editor.putInt("dayPlan",Constant.day_plan_20);
                break;
            case R.id.activity_choose_book_button_plan_30:
                bt_plan10.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan20.setBackgroundColor(getColor(R.color.colorTextBackground));
                bt_plan30.setBackgroundColor(getColor(R.color.colorTextBackgroundSelected));
                isNextButtonEnableFlag_2=true;
                if (nextButtonEnableFlag_1){bt_next.setEnabled(true);textView_days.setText((124+" 天")); }
                editor.putInt("dayPlan",Constant.day_plan_30);
                break;
            case R.id.activity_choose_book_button_next:
                editor.commit();
                Intent in=new Intent(this,MainActivity.class);
                startActivity(in);
                finish();
            default:break;
        }
    }
}
