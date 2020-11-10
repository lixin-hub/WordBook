package com.cqut.learn.CustomView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.LitePalDB.Comment;
import com.cqut.learn.R;

import java.util.List;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {
  private List<Comment> comments;
  private Context context;
    public MyCommentAdapter(Context context, List<Comment> comments){
        this.comments=comments;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comment_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
              final Comment comment=comments.get(position);
             // holder.image_user.setImageBitmap(comment.getUser().getHeadImage());
              holder.text_time.setText(comment.getDate().toString());
              holder.image_likes.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (comment.isLike()){
                          holder.image_likes.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.activity_learn_title_before_likes));
                          comment.setLike(false);
                          comment.setLikes(comment.getLikes()-1);
                      }else{
                          holder.image_likes.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.activity_learn_title_after_likes));
                          comment.setLike(true);
                          comment.setLikes(comment.getLikes()+1);

                      }
                  }
              });
              holder.text_likes_counts.setText(comment.getLikes()+"");
              holder.text_content.setText(comment.getContent());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView text_content,text_time,text_age,text_likes_counts;
        public ImageView image_user,image_likes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_content=itemView.findViewById(R.id.fragment_comment_item_content);
            text_time=itemView.findViewById(R.id.fragment_comment_item_time);
            image_likes=itemView.findViewById(R.id.fragment_comment_item_likes);
            image_user=itemView.findViewById(R.id.fragment_comment_item_user_image);
            text_likes_counts=itemView.findViewById(R.id.fragment_comment_item_likes_counts);
        }
    }
}
