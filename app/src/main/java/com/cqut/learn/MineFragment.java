package com.cqut.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MineFragment extends Fragment implements View.OnClickListener {


    private CardView card_navigation_favourite;//收藏的单词
    private CardView card_navigation_plan_change;//改变计划
    private CardView card_navigation_change_theme;//改变主题
    private CardView card_navigation_account_problem;//帐号问题
    private CardView card_navigation_about;//关于
    private CardView card_navigation_logout;//推出登录


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_mine,container,false);
         card_navigation_about=view.findViewById(R.id.fragment_mine_navigation_about);
         card_navigation_about.setOnClickListener(this);
         card_navigation_account_problem=view.findViewById(R.id.fragment_mine_navigation_account_problem);
         card_navigation_account_problem.setOnClickListener(this);
         card_navigation_plan_change=view.findViewById(R.id.fragment_mine_navigation_change_plan);
         card_navigation_plan_change.setOnClickListener(this);
         card_navigation_favourite=view.findViewById(R.id.fragment_mine_navigation_favorite);
         card_navigation_favourite.setOnClickListener(this);
         card_navigation_logout=view.findViewById(R.id.fragment_mine_navigation_force_logout);
         card_navigation_logout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_mine_navigation_favorite:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_change_plan:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_change_theme:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_account_problem:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_about:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_force_logout:
                Toast.makeText(this.getContext(),"youc clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;


        }
    }
}
