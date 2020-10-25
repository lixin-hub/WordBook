package com.cqut.learn;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cqut.learn.CustomView.MyScrollView;

import org.w3c.dom.Text;

public class MainLearnActivity extends BaseActivity {
    /*
     *@className:MainLearnActivity
     *@Description:展示单词学习界面
     *@author:lixin
     *@Date:2020/10/25 12:47
     */
    private MyScrollView scrollView;
    private RelativeLayout title;
    private TextView titleText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        initView();
    }
    private void initView(){
        title = findViewById(R.id.title);
        titleText=findViewById(R.id.activity_learn_title_text);
        scrollView=findViewById(R.id.activity_learn_content_scroll);
        scrollView.setOnScrollChangeListener(null);//设置为空即可
        scrollView.setTitle(title,titleText,"Congratulations",window);
    }

}
