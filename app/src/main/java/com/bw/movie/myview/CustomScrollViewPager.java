package com.bw.movie.myview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 *@describe(描述)：CustomScrollViewPager
 *@data（日期）: 2019/12/11
 *@time（时间）: 19:11
 *@author（作者）: 徐姗姗
 **/


public class CustomScrollViewPager extends ViewPager {
    //是否可以左右滑动？true 可以，像Android原生ViewPager一样。
    // false 禁止ViewPager左右滑动。
    private boolean scrollable = false;

    public CustomScrollViewPager( Context context) {
        super(context);
    }

    public CustomScrollViewPager( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollable;
    }
}
