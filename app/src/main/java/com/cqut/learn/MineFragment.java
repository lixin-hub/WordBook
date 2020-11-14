package com.cqut.learn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.CustomView.FavoriteListAdapter;
import com.cqut.learn.CustomView.SearchListAdapter;
import com.cqut.learn.Util.BitmapToRound;
import com.cqut.learn.Util.LearnManager;
import com.cqut.learn.Util.PictureUtil;

public class MineFragment extends Fragment implements View.OnClickListener {


    private CardView card_navigation_favourite;//收藏的单词
    private CardView card_navigation_plan_change;//改变计划
    private CardView card_navigation_change_theme;//改变主题
    private CardView card_navigation_account_problem;//帐号问题
    private CardView card_navigation_about;//关于
    private CardView card_navigation_logout;//推出登录

    private LearnManager manager;
    private TextView head_learned_count;//已经学习的单词数量
    private TextView head_id;//用户id
    private ImageView head_image;//用户头像

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_mine,container,false);
        manager=new LearnManager(this.getContext());
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
         head_id=view.findViewById(R.id.fragment_mine_head_id);
         head_learned_count=view.findViewById(R.id.fragment_main_head_has_learned_count);
         head_image=view.findViewById(R.id.fragment_mine_head_image);
         head_image.setImageBitmap(BitmapToRound.GetRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.plan_change),100));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        head_learned_count.setText(manager.getTotalLearnedCounts()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_mine_navigation_favorite:
                Intent in=new Intent(this.getActivity(),MineInfoActivity.class);
                in.putExtra(Constant.MINE_NAVIGATION_WHICH_ITEM,Constant.MINE_NAVIGATION_ITEM_FAVORITE);
                getContext().startActivity(in);
                break;
            case R.id.fragment_mine_navigation_change_plan:
                Toast.makeText(this.getContext(),"you clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_change_theme:
                Toast.makeText(this.getContext(),"you clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_account_problem:
                Toast.makeText(this.getContext(),"you clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_about:
                Toast.makeText(this.getContext(),"you clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.fragment_mine_navigation_force_logout:
                Toast.makeText(this.getContext(),"you clicked"+v.toString(),Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
