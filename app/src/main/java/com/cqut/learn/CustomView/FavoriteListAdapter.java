package com.cqut.learn.CustomView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Cognate;
import com.cqut.learn.LitePalDB.Translate;
import com.cqut.learn.MainLearnActivity;
import com.cqut.learn.R;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {
  private List<CET4> cet4List;
    private Context context;
    public FavoriteListAdapter(Context context, List<CET4> cet4s) {
        cet4List=cet4s;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_mine_favorite_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
         final CET4 cet4=cet4List.get(position);
        holder.text_word.setText(cet4.getHeadWord());
         StringBuilder builder=new StringBuilder();
         if (cet4.getTranslates().size()>=1) {
             Translate translate = cet4.getTranslates().get(0);
             builder.append(translate.getPos()).append(" ").append(translate.getP_Cn()).append("  ...").append("\n").append(translate.getP_Content()).append("  ...").append("\n");
         }
         if (cet4.getCognates().size()>=1) {
             Cognate cognate = cet4.getCognates().get(0);
             builder.append("同根词:\n").append(cognate.getPos()).append(" ").append(cognate.getP_Cn()).append("  ...").append("\n").append(cognate.getP_Content()).append("  ...").append("\n");
         }
         holder.text_trans.setText(builder.toString());
         holder.linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context, MainLearnActivity.class);
                 intent.putExtra("theId",cet4.getWordId());
                 context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return cet4List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
            public LinearLayout linearLayout;
            public TextView text_word;
            public TextView text_trans;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout= (LinearLayout) itemView;
            text_trans=itemView.findViewById(R.id.dialog_mine_favorite_recycler_item_trans);
            text_word=itemView.findViewById(R.id.dialog_favorite_recycler_item_word);

        }
    }
}
