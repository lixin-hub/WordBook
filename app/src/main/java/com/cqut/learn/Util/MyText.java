package com.cqut.learn.Util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.annotation.NonNull;

import com.cqut.learn.CustomView.MyTextView;
import com.cqut.learn.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyText {
    /*
     *@className:MyText
     *@Description:对文本操作进行封装
     *@author:lixin
     *@Date:2020/10/24 13:47
     */
public static void clickableText(final MyTextView textView, final TextItemClickListener listener){
    /*
    *@methodName:clickableText
    *@Description:实现文本局部可点击
    *@author:lixin
    *@Date:2020/10/24 13:48
    *@Param:[textView, listener]
    *@Return:void
    */
    final String text;
    text = textView.getText().toString();
    final SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
    Pattern pattern=Pattern.compile("[a-zA-Z]+");
    final Matcher matcher=pattern.matcher(text);
    while (matcher.find()) {
        final String group = matcher.group();
        if (group.length() > 3) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    listener.onTextItemClicked(widget, group);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    listener.updateDrawState(ds, group);
                }
            };

            spannableStringBuilder.setSpan(clickableSpan, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
    textView.setHighlightColor(textView.getContext().getColor(R.color.colorTextBackgroundSelected));
    textView.setText(spannableStringBuilder);
    textView.setMovementMethod(MyTextView.CustomLinkMovementMethod.getInstance());
}

public interface TextItemClickListener{
    /*
     *@className:TextItemClickListener
     *@Description:局部文本点击监听器
     *@author:lixin
     *@Date:2020/10/24 13:50
     */
    void onTextItemClicked(@NonNull View view, String matcher);
    void updateDrawState(@NonNull TextPaint ds, String matcher) ;
    }
}

