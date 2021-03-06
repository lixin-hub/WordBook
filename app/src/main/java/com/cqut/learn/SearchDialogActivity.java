package com.cqut.learn;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cqut.learn.CustomView.SearchListAdapter;
import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.LitePalDB.Cognate;
import com.cqut.learn.LitePalDB.Translate;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class SearchDialogActivity extends BaseActivity implements TextWatcher {
    private EditText main_editText_search;//搜索框
    private SearchListAdapter adapter;
    private RecyclerView recyclerView;
    private Button bt_cancel;//取消搜索
    private CheckBox checkBox;//是否启用模糊搜索
    private TextView resultSize;//搜索结果条数
    private List<CET4> cet4List=new ArrayList<>();
    private List<CET4> cet4s=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dialog);
        main_editText_search=findViewById(R.id.activity_main_search_word);
        bt_cancel=findViewById(R.id.activity_search_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_editText_search.addTextChangedListener(this);
        main_editText_search.requestFocus();
        recyclerView=findViewById(R.id.activity_search_recycler);
        checkBox=findViewById(R.id.activity_search_check_box);
        resultSize=findViewById(R.id.activity_search_size);
        adapter=new SearchListAdapter(this,cet4List);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
    }
    /*
    TextWatcher动态监听editText的文本变化

     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void onTextChanged(final CharSequence s, int start, int before, int count) {
        resultSize.setText("正在搜索...");
        if (!checkBox.isChecked()){
           List<CET4> cet4s= LitePal.where("headWord=?",s.toString()).find(CET4.class);
            cet4List.clear() ;
            cet4List.addAll(cet4s);
            resultSize.setText(cet4List.size()+" 条结果");
            return;
        }
            new Thread(new Runnable() {
                @Override
                public void run() {
                if (s.length()>0&&s.toString().matches("^[a-zA-Z]*")) {
                    //如果是英文
                    if (cet4List.size()>0){
                        cet4s.clear();
                        cet4s.addAll(cet4List);
                        cet4List.clear();
                        for (CET4 cet4 : cet4s) {
                            if (cet4.getHeadWord().contains(s))
                                cet4List.add(cet4);
                            if (cet4List.size()==0){
                                List<Cognate> cognates = cet4.getCognates();
                                for (Cognate cognate : cognates) {
                                    if (cognate.getP_Content().contains(s)) {
                                        cet4List.add(cet4);
                                    }
                                }
                               }
                            }
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               resultSize.setText(cet4List.size()+" 条结果");
                               adapter.notifyDataSetChanged();
                           }
                       });
                    }else {
                        cet4s = LitePal.order("headWordLength asc").where("headWordLength >= ?", "2").find(CET4.class);
                        for (CET4 cet4 : cet4s) {
                                if (cet4.getHeadWord().contains(s))
                                    cet4List.add(cet4);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                resultSize.setText(cet4List.size()+" 条结果");
                            }
                        });
                    }


                }else if(s.length()>0&&!s.toString().matches("^[a-zA-Z]*")) {
                    //如果是中文
                    cet4List.clear();
                    List<Translate> translates = LitePal.findAll(Translate.class);
                    for (Translate translate : translates) {
                        if (translate.getP_Cn().contains(s)) {
                            cet4List.add(LitePal.where("wordId=?",translate.getTheId()+"").find(CET4.class).get(0));
                        }
                    }
                    if (cet4List.size()==0) {
                        List<Cognate> cognates=LitePal.findAll(Cognate.class);
                        for (Cognate cognate : cognates) {
                            if (cognate.getP_Cn().contains(s)) {
                                cet4List.add(LitePal.where("wordId=?", cognate.getTheId() + "").find(CET4.class).get(0));
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            resultSize.setText(cet4List.size()+" 条结果");
                        }
                    });
                }
                }
            }).start();
            }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
