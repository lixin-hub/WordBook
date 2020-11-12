package com.cqut.learn;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqut.learn.CustomView.MyEditText;
import com.cqut.learn.CustomView.MyTaskAdapter;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.MongoDB.MongoDBUtil;
import com.cqut.learn.Util.LearnManager;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyJsonParser;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import me.sugarkawhi.bottomnavigationbar.BottomNavigationBar;
import me.sugarkawhi.bottomnavigationbar.BottomNavigationEntity;

public class MainActivity extends BaseActivity implements View.OnClickListener, MyJsonParser.WordParseListener, MyEditText.MyOnClickListener,BottomNavigationBar.IBnbItemSelectListener{
    private MyEditText main_editText_search;//搜索框
    private Button main_bt_search;
    private BottomNavigationBar bottomNavigationBar;
    private Button main_bt_startLearn;
    private ScrollView scrollView;
    private MineFragment mineFragment;
    private LinearLayout layout_visibility;//main布局的可见性，替换其他视图时为不可见
    private FrameLayout layout_take_place;//为fragment占位的布局

    private TextView text_Title;//Dialog
    private Dialog dialog;
    private TextView text_progress;
    private ProgressBar bar;
    //plan bar
    private ProgressBar plan_bar;
    //plan rate
    private TextView plan_rate;
    //extra days
    private TextView plan_extra_days;
    private TextView text_message;
    //planChanged
    TextView plan_change;//计划有变
    //dayGroup
    private List<CET4> cet4s=new ArrayList<>();
    //学习管理器
    LearnManager manager;
    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        manager=new LearnManager(this);
       //manager.setCurrentWordId(0);
        manager.setCurrentGroupId(0);
        //今入时判断词库是否加载完毕
        if (LitePal.count("CET4") < 3700) {
            View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog_view, null);
            text_message = view.findViewById(R.id.alert_dialog_view_message);
            bar = view.findViewById(R.id.alert_dialog_progress);
            text_Title = view.findViewById(R.id.alert_dialog_title);
            text_progress = view.findViewById(R.id.alert_dialog_text_progress);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);
            builder.setCancelable(false);
            dialog = builder.create();
            dialog.show();
            text_Title.setText("正在解析单词数据这个过程可能需要几分钟");
            MyJsonParser.setWordParseListener(this);
            MyJsonParser.start(this, "CET4_test.json", LitePal.count("CET4"));
        }
        //navigationBar
        {
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
            bottomNavigationBar.setBnbItemSelectListener(this);

        }
        //task的recycler
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        cet4s.addAll(manager.getCET4Group());
        MyTaskAdapter taskAdapter=new MyTaskAdapter(this,cet4s);//添加group到任务列表
        //recycler
        RecyclerView task_recycler = findViewById(R.id.activity_main_mid_recycler);
//设置布局管理器
        task_recycler.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        task_recycler.setAdapter(taskAdapter);
    }
    private void initView(){
        mineFragment=new MineFragment();
        layout_take_place=findViewById(R.id.activity_main_fragment);
        layout_visibility=findViewById(R.id.activity_main_visibility);
        plan_bar=findViewById(R.id.activity_main_head_plan_progressbar);
        plan_rate=findViewById(R.id.activity_main_head_plan_rate);
        plan_extra_days=findViewById(R.id.activity_main_head_plan_extra_days);
        main_bt_startLearn=findViewById(R.id.activity_main_mid_bt_start_learn);
        main_bt_startLearn.setOnClickListener(this);
        Toolbar toolbar=findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        bottomNavigationBar=findViewById(R.id.bottomNavigationBar);
        main_editText_search=findViewById(R.id.activity_main_search_word);
        main_editText_search.setMyOnClickListener(this);
        plan_change=findViewById(R.id.activity_main_head_plan_change_plan);
        plan_change.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        plan_bar.setMax(manager.getTotalCounts());
        plan_bar.setProgress(manager.getTotalLearnedCounts());
        plan_rate.setText(manager.getTotalLearnedCounts()+"/"+manager.getTotalCounts());
        plan_extra_days.setText("还有"+manager.getExtraDays()+"天就完成了");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_mid_bt_start_learn://开始学习按钮
         Intent intent=new Intent(this,MainLearnActivity.class);
         startActivity(intent);
               break;
            case R.id.activity_main_search_word://搜索框
                main_editText_search.setInputType(InputType.TYPE_NULL);
                Intent in=new Intent(this, SearchDialogActivity.class);
                startActivity(in);
                break;
            case R.id.activity_main_head_plan_change_plan://计划改变，跳转到选择界面
                manager.editPrefs(LearnManager.planChanged,true);
                Intent choose=new Intent(this, ChooseBookActivity.class);
            startActivity(choose);
            finish();
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
            @SuppressLint("SetTextI18n")
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


    private void addFragment(@IdRes int convertView, @NonNull Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(convertView,fragment);
        transaction.commit();
    }
    private void removeFragment(@NonNull Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    @Override
    public void onBnbItemSelect(int position) {

                switch (position) {
                    case 0://学习
                        switch (Constant.previous_view){//上一个界面
                            case 1:
                                break;
                            case 2:
                                removeFragment(mineFragment);
                            if (layout_visibility.getVisibility()==View.GONE){layout_visibility.setVisibility(View.VISIBLE);}
                            if (layout_take_place.getVisibility()==View.VISIBLE){layout_take_place.setVisibility(View.GONE);}
                        }
                        Constant.previous_view=position;
                        break;
                    case 1:
                        Constant.previous_view=position;
                        break;
                    case 2:
                        if (layout_visibility.getVisibility()==View.VISIBLE){layout_visibility.setVisibility(View.GONE);}
                        if (layout_take_place.getVisibility()==View.GONE){layout_take_place.setVisibility(View.VISIBLE);}
                         addFragment(R.id.activity_main_fragment,mineFragment);
                        Constant.previous_view=position;
                        break;
                }
    }
}