package com.cqut.learn.CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.DataBase.CET4;
import com.cqut.learn.DataBase.Translate;
import com.cqut.learn.R;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
  private List<CET4> cet4List;
    public SearchListAdapter( List<CET4> cet4s) {
        cet4List=cet4s;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search__dialog_search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         CET4 cet4=cet4List.get(position);
         holder.text_word.setText(cet4.getHeadWord());
         StringBuilder builder=new StringBuilder();
         for (Translate translate:cet4.getTranslates()){
             builder.append(translate.getPos()+ translate.getP_Cn()+"\n"+translate.getP_Content());}
         holder.text_trans.setText(builder.toString());
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
            text_trans=itemView.findViewById(R.id.activity_search_dialog_recycler_item_trans);
            text_word=itemView.findViewById(R.id.activity_search_dialog_recycler_item_word);
        }
    }
}
