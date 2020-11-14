package com.cqut.learn.CustomView;

import android.content.Context;
import android.util.AttributeSet;

import android.view.ContextMenu;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


    public class MyRecyclerView extends RecyclerView {

        private RecyclerViewContextInfo mContextInfo = new RecyclerViewContextInfo();

        public MyRecyclerView(@NonNull Context context) {
            super(context);
        }

        public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean showContextMenuForChild(View originalView, float x, float y) {
            getPositionByChild(originalView);
            return super.showContextMenuForChild(originalView, x, y);
        }

        /**
         * 记录当前RecyclerView中Item上下文菜单的Position
         * @param originalView originalView
         */
        private void getPositionByChild(View originalView){
            LayoutManager layoutManager =getLayoutManager();
            if(layoutManager!=null){
                int position=layoutManager.getPosition(originalView);
                mContextInfo.setPosition(position);
            }
        }

        @Override
        protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
            return mContextInfo;
        }

        public static class RecyclerViewContextInfo implements ContextMenu.ContextMenuInfo {
            private int mPosition = 0;
            public int getPosition(){
                return this.mPosition;
            }
            public void setPosition(int position){
                this.mPosition = position;
            }
        }

    }

