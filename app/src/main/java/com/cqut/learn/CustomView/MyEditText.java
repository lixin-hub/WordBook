package com.cqut.learn.CustomView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {
    MyOnClickListener listener;
    public MyEditText(@NonNull Context context) {
        super(context);
    }
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
 public void setMyOnClickListener(MyOnClickListener listener){
        this.listener=listener;
 }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        listener.onClick(this);
        return false;
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

    }
    public interface MyOnClickListener{
        void onClick(View v);
    }
}
