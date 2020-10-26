package com.cqut.learn;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cqut.learn.CustomView.MyScrollView;
import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.Cognate;
import com.cqut.learn.DataBase.MyDataBaseHelper;
import com.cqut.learn.DataBase.Sentence;
import com.cqut.learn.DataBase.Syno;
import com.cqut.learn.Util.MyJsonParser;

import org.litepal.LitePal;

import java.util.List;

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
   //  MyJsonParser.start(this,"CET4luan_2.json");//把单词数据保存到数据库

   List<CET4> cet4s= MyDataBaseHelper.query("headWord=?","refuse");
   for (CET4 cet4:cet4s){
       for (Cognate cognate:cet4.getCognates()){
           System.out.println(cognate.getPos()+":"+cognate.getP_Content()+":"+cognate.getP_Cn());
       }
   }
    }

    private void initView(){
        title = findViewById(R.id.title);
        titleText=findViewById(R.id.activity_learn_title_text);
        scrollView=findViewById(R.id.activity_learn_content_scroll);
        scrollView.setOnScrollChangeListener(null);//设置为空即可
        scrollView.setTitle(title,titleText,"Congratulations",window);
    }

}
