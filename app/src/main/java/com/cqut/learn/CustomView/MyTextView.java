package com.cqut.learn.CustomView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cqut.learn.LitePalDB.CET4;
import com.cqut.learn.R;
import com.cqut.learn.Util.MyDialog;
import com.cqut.learn.Util.MyText;

import org.litepal.LitePal;

import java.util.List;

public class MyTextView extends androidx.appcompat.widget.AppCompatTextView implements MyText.TextItemClickListener {
/*
 *@className:MyTextView
 *@Description:解决布局clcik与TextView本身滑动冲突，并且封装了划词功能
 *@author:lixin
 *@Date:2020/10/24 13:52
 */
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setClickable(true);
        MyText.clickableText(this,this);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean linkHit;//内部富文本是否被点击
    private static long lastClickTime;

    private static final long CLICK_DELAY = 500L;

    public void setOnTextChanged(){
        MyText.clickableText(this,this);
    }
    @Override
    public boolean performClick() {   //最后响应3
        if (linkHit) {
            return true;    //这样textView的OnClick事件不会响应
        }
        return super.performClick();  //调用监听的onClick方法
    }



        @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {   //textView的OnClick事件响应，首先响应1
        linkHit = false;
        return super.onTouchEvent(event);
    }

    public void setMyText(String string){
        this.setText(string);
        setOnTextChanged();
    }
    @Override
    public void onTextItemClicked(@NonNull View view, String matcher) {
       List<CET4> cet4s= LitePal.where("headWord= ?",matcher).find(CET4.class);
       if (cet4s.size()>0){
           CET4 cet4=cet4s.get(0);
           StringBuilder builder=new StringBuilder();
           builder.append(cet4.getTranslates().get(0).getPos()).append(" ").append(cet4.getTranslates().get(0).getP_Cn()).append("\n").append(cet4.getTranslates().get(0).getP_Content());
        MyDialog.showWordInfo(view.getContext(),cet4.getHeadWord(),builder.toString());
    }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds, String matcher) {
         ds.setColor(this.getContext().getColor(R.color.colorAccent));
         ds.setUnderlineText(false);
    }

    public static class CustomLinkMovementMethod extends LinkMovementMethod { //次之2

        static CustomLinkMovementMethod sInstance;
        //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
        float x1 = 0;
        float x2 = 0;
        float y1 = 0;
        float y2 = 0;

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {

            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP ||
                    action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);  //第几行
                int off = layout.getOffsetForHorizontal(line, x); //水平偏移，第几个字

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
                //解决拦截滑动时触发Textview link 点击事件
                if (action == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    String TAG = "MyTextView";
                    if (y1 - y2 > 50) {
                        link = new ClickableSpan[0];
                    } else if (y2 - y1 > 50) {
                        link = new ClickableSpan[0];
                    }
                } else {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                //不是滑动、link.length != 0，拦截处理 Textview link 点击事件 ，证明点击了
                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                        link[0].onClick(widget);
                        return true;
                    }

                } else {
                    Selection.removeSelection(buffer);
                }

            }
           return Touch.onTouchEvent(widget, buffer, event);

        }
        public static CustomLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new CustomLinkMovementMethod();
            }
            return sInstance;
        }
    }
}