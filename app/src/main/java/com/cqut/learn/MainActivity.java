package com.cqut.learn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.cqut.learn.CustomView.MyTextView;
import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.MyDataBaseHelper;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.sugarkawhi.bottomnavigationbar.BottomNavigationBar;
import me.sugarkawhi.bottomnavigationbar.BottomNavigationEntity;

public class MainActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private EditText main_editText_search;//搜索框
    private Button main_bt_search;
    private BottomNavigationBar bottomNavigationBar;
    private Button main_bt_startLearn;
    private ScrollView scrollView;
    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        preferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
        editor = preferences.edit();
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
        main_editText_search.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_mid_bt_start_learn:
         Intent intent=new Intent(this,MainLearnActivity.class);
        startActivity(intent);
        break;
            case R.id.activity_main_search_word:
                break;

             }
    }

    /*
    TextWatcher动态监听editText的文本变化

     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      List<CET4> cet4s= MyDataBaseHelper.query("headWord=?",s.toString());
      for (CET4 cet4:cet4s){

        }
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}