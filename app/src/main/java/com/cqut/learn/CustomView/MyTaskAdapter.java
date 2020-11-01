package com.cqut.learn.CustomView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Translate;
import com.cqut.learn.MainLearnActivity;
import com.cqut.learn.R;

import java.util.List;


    public class MyTaskAdapter extends RecyclerView.Adapter<com.cqut.learn.CustomView.MyTaskAdapter.ViewHolder> {
        private List<CET4> cet4List;
        private Context context;
        public MyTaskAdapter( Context context,List<CET4> cet4s) {
            cet4List=cet4s;
            this.context=context;
        }

        @NonNull
        @Override
        public com.cqut.learn.CustomView.MyTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mid_task_recycler_item,parent,false);
            return new com.cqut.learn.CustomView.MyTaskAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.cqut.learn.CustomView.MyTaskAdapter.ViewHolder holder, int position) {
            final CET4 cet4=cet4List.get(position);
            holder.text_word.setText(cet4.getHeadWord());
            holder.text_word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, MainLearnActivity.class);
                    intent.putExtra("theId",cet4.getWordId());
                    context.startActivity(intent);
                }
            });
            holder.text_trans.setText(cet4.getTranslates().get(0).getP_Cn().split("；")[0]);
        }

        @Override
        public int getItemCount() {
            return cet4List.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public TextView text_word;
            public TextView text_trans;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text_trans=itemView.findViewById(R.id.activity_main_mid_task_recycler_item_word_trans);
                text_word=itemView.findViewById(R.id.activity_main_mid_recycler_item_task_word);
            }
        }
    }
