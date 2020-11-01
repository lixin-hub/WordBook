package com.cqut.learn.CustomView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cqut.learn.BaseActivity;
import com.cqut.learn.Constant;
import com.cqut.learn.R;

import java.util.Set;

public class MyScrollView extends ScrollView implements  ScrollView.OnScrollChangeListener{
    /*
     *@className:MyScrollView
     *@Description:自定义scrollview实现简单的动画效果
     *@author:lixin
     *@Date:2020/10/25 17:33
     */

    private TextView titleText;
    private View title;
    private String text;
    private Window window;
    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyScrollView(Context context) {
        super(context);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(this);
    }
    public void setTitle(View title, TextView titleText,String text, Window window){
        /*
         *@className:MyScrollView
         *@Description:设置必要参数
         *@author:lixin
         *@Date:2020/10/25 14:03
         */
        this.title=title;
        this.titleText=titleText;
        this.window=window;
        this.text=text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.setEdgeEffectColor(Color.RED);
        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        titleText.setText("");
        float height = title.getLayoutParams().height;
        float y = title.getY() * getResources().getDisplayMetrics().density;
        final float offsetHeight = y + height;
        if (scrollY >= offsetHeight - titleText.getHeight() * getResources().getDisplayMetrics().density) {
            titleText.setX(scrollY/2);
            titleText.setText(text);
        }
        if (scrollY < offsetHeight) {
            titleText.setText("");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (Constant.is3DViewTouched){return false;}
        return super.onTouchEvent(ev);
    }
}
