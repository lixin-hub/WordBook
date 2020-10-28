package com.cqut.learn;

import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqut.learn.CustomView.MyEditText;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyJsonParser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import me.sugarkawhi.bottomnavigationbar.BottomNavigationBar;
import me.sugarkawhi.bottomnavigationbar.BottomNavigationEntity;

public class MainActivity extends BaseActivity implements View.OnClickListener, MyJsonParser.WordParseListener, MyEditText.MyOnClickListener {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private MyEditText main_editText_search;//搜索框
    private Button main_bt_search;
    private BottomNavigationBar bottomNavigationBar;
    private Button main_bt_startLearn;
    private ScrollView scrollView;

    private TextView text_Title;//Dialog
    private Dialog dialog;
    private TextView text_progress;
    private ProgressBar bar;
    private TextView text_message;

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        preferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        editor = preferences.edit();
        if (LitePal.count("CET4")<3700){
            View view= LayoutInflater.from(this).inflate(R.layout.alert_dialog_view,null);
            text_message=view.findViewById(R.id.alert_dialog_view_message);
            bar=view.findViewById(R.id.alert_dialog_progress);
            text_Title=view.findViewById(R.id.alert_dialog_title);
            text_progress=view.findViewById(R.id.alert_dialog_text_progress);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(view);
            builder.setCancelable(false);
             dialog=builder.create();
            dialog.show();
            text_Title.setText("正在解析单词数据这个过程可能需要几分钟");
            MyJsonParser.setWordParseListener(this);
            MyJsonParser.start(this,"CET4luan_2.json",LitePal.count("CET4"));
        }
        List<BottomNavigationEntity> mEntities = new ArrayList<>();

        mEntities.add(new BottomNavigationEntity(
                "学习",
                R.drawable.activity_main_navigation_icon1,
                R.drawable.activity_main_navigation_icon1));
        mEntities.add(new BottomNavigationEntity(
                "训练",
                R.drawable.activity_main_navigation_icon1,
                R.drawable.activity_main_navigation_icon1));
        mEntities.add(new BottomNavigationEntity(
                "我的",
                R.drawable.activity_main_navigation_icon1,
                R.drawable.activity_main_navigation_icon1, 10));
        bottomNavigationBar.setEntities(mEntities);
        //点击item
        bottomNavigationBar.setBnbItemSelectListener(new BottomNavigationBar.IBnbItemSelectListener() {

            @Override
            public void onBnbItemSelect(int position) {
                Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
        //重复点击
        bottomNavigationBar.setBnbItemDoubleClickListener(new BottomNavigationBar.IBnbItemDoubleClickListener() {
            @Override
            public void onBnbItemDoubleClick(int position) {
            }
        });
    }

    private void initView(){
        main_bt_startLearn=findViewById(R.id.activity_main_mid_bt_start_learn);
        main_bt_startLearn.setOnClickListener(this);
        Toolbar toolbar=findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationBar=findViewById(R.id.bottomNavigationBar);
        main_editText_search=findViewById(R.id.activity_main_search_word);
        main_editText_search.setMyOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_mid_bt_start_learn:
         Intent intent=new Intent(this,MainLearnActivity.class);
        startActivity(intent);
        break;
            case R.id.activity_main_search_word:
                main_editText_search.setInputType(InputType.TYPE_NULL);
                Intent in=new Intent(this, SearchDialogActivity.class);
                startActivity(in);
                break;

             }
    }


    @Override
    public void onWordParsedOver(int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyDialog.showWordInfo(MainActivity.this,"完成","所有单词解析完成");
                dialog.dismiss();
            }
        });

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