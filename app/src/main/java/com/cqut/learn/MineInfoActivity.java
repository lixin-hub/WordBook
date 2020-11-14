package com.cqut.learn;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.cqut.learn.CustomView.FavoriteListAdapter;
import com.cqut.learn.CustomView.MyRecyclerView;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.Util.LearnManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineInfoActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private LearnManager manager;
    private FrameLayout coverLayout;//用于背替换的布局
    private ImageView titleBack;//返回按钮
    private EditText searchBox;//搜索框
    private FavoriteListAdapter adapter;
    private List<CET4> displayList;//显示列表
    private Toolbar toolbar;
    private MyRecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mine_navigation);

        init();
        coverView();
    }

    private void init(){
        /*
        *@methodName:init
        *@Description:初始化必要对象
        *@author:lixin
        *@Date:2020/11/14 14:48
        *@Param:[]
        *@Return:void
        */
        toolbar=findViewById(R.id.dialog_mine_navigation_toolbar);
        setSupportActionBar(toolbar);
        manager=new LearnManager(this);
        coverLayout=findViewById(R.id.dialog_mine_navigation_item);
        titleBack=findViewById(R.id.dialog_mine_navigation_title_back);
        titleBack.setOnClickListener(this);
        searchBox=findViewById(R.id.dialog_mine_navigation_title_search_box);
        searchBox.addTextChangedListener(this);
    }
    private void coverView(){
/*
*@methodName:coverView
*@Description:根据intent的参数判断显示哪一个布局
*@author:lixin
*@Date:2020/11/14 14:47
*@Param:[]
*@Return:void
*/

        switch (getIntent().getIntExtra(Constant.MINE_NAVIGATION_WHICH_ITEM,0)){
            case Constant.MINE_NAVIGATION_ITEM_FAVORITE:
                displayList=new ArrayList<>();
                displayList.addAll(manager.getMyFavoriteList());
                Collections.reverse(displayList);//倒序
                coverLayout.addView(LayoutInflater.from(this).inflate(R.layout.dialog_mine_favorite,coverLayout,false));
               recyclerView=findViewById(R.id.dialog_mine_favorite_recycler);
                adapter= new FavoriteListAdapter(this,displayList);
                recyclerView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                        menu.setHeaderTitle("编辑我的收藏");
                        menu.setHeaderIcon(R.drawable.time_icon);
                        menu.add(0,1,1,"从列表移除");
                        menu.add(0,2,2,"全部移除");
                        menu.add(0,3,3,"按时间正序");
                        menu.add(0,4,4,"按时间倒序");
                        menu.add(0,5,5,"按单词长度正序");
                        menu.add(0,6,6,"按单词长度倒序");
                    }
                });

                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                //设置布局管理器
                recyclerView.setLayoutManager(layoutManager);
                //设置为垂直布局，这也是默认的
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(adapter);
                break;
                case Constant.MINE_NAVIGATION_ITEM_PLAN_CHANGE:
                    break;
                case Constant.MINE_NAVIGATION_ITEM_ABOUT:
                    break;
                case Constant.MINE_NAVIGATION_ITEM_ACCOUNT_PROBLEM:
                    break;
        }
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        MyRecyclerView.RecyclerViewContextInfo info= (MyRecyclerView.RecyclerViewContextInfo) item.getMenuInfo();
         int p=info.getPosition();
        switch (item.getItemId()){
            case 1:
                displayList.remove(p);
                CET4 cet4=displayList.get(p);
                cet4.setLike(false);
                adapter.notifyDataSetChanged();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_mine_navigation_title_back:
                finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<CET4> cet4s=manager.getMyFavoriteList();
        displayList.removeAll(displayList);
        for (CET4 cet4:cet4s){
            if (cet4.getHeadWord().contains(s)||cet4.getTranslates().get(0).getP_Cn().contains(s)){
            displayList.add(cet4);
            }
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


}
