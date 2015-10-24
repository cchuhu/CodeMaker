package com.appeaser.deckviewsample;

import android.content.Context;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by chen on 15/10/24.
 */
public class MyWebView extends WebView{
    public MyWebView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }
}
