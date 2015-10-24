package com.appeaser.deckview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by antdlx on 2015/10/24.
 */
public class ANTWebView extends WebView {
    public ANTWebView(Context context) {
        super(context);
    }

    public ANTWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ANTWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ANTWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
