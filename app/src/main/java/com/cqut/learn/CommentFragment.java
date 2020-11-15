package com.cqut.learn;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.CustomView.MyCommentAdapter;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Comment;
import com.cqut.learn.User.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentFragment extends Fragment implements View.OnClickListener  {
    /*
     *@className:CommentFragment
     *@Description:展示评论
     *@author:lixin
     *@Date:2020/10/28 23:17
     */
    private MyCommentAdapter adapter;//评论适配器
    private RecyclerView recyclerView;//评论列表
    private CET4 word;
    private List<Comment> comments;
    private ImageView title_back;//返回按钮

    public CommentFragment(CET4 word) {
        this.word=word;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_learn_comment,container,false);
         adapter=new MyCommentAdapter(getContext(),word.getComments());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView=view.findViewById(R.id.fragment_comment_recycler);
        title_back=view.findViewById(R.id.fragment_learn_comment_title_back);
        title_back.setOnClickListener(this);
       //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
       //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);

        return view;
    }
     @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_learn_comment_title_back:
             MainLearnActivity activity=(MainLearnActivity)getActivity();
             activity.removeFragment(this);

        }
    }
}
