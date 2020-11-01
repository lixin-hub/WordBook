package com.cqut.learn;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.CustomView.MyCommentAdapter;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.User.Comment;
import com.cqut.learn.User.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentFragment extends Fragment {
    /*
     *@className:CommentFragment
     *@Description:展示评论
     *@author:lixin
     *@Date:2020/10/28 23:17
     */
    private MyCommentAdapter adapter;
    private RecyclerView recyclerView;
    CET4 word;
    List<Comment> comments;

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
         comments=new ArrayList<>();
         adapter=new MyCommentAdapter(getContext(),comments);
        init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView=view.findViewById(R.id.fragment_comment_recycler);
       //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
       //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        return view;
    }
          private  void init(){
         for (int i=0;i<10;i++){
                Comment comment = new Comment();
                comment.setLikes(i);
                comment.setContent("这是一条评论" + i);
                comment.setDate(new Date(System.currentTimeMillis()));
                comment.setUser(new User("用户" + i, new Date(System.currentTimeMillis() - 100000000), BitmapFactory.decodeResource(getContext().getResources(), R.drawable.cet4)));
            if (i%2==0) {
                comment.setLike(false);
            }else {
                comment.setLike(true);
            }
            comments.add(comment);
            adapter.notifyDataSetChanged();
        }

}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
