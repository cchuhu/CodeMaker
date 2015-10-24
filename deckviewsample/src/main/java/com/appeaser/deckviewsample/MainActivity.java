package com.appeaser.deckviewsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.json.JSONException;

public class MainActivity extends Activity implements NetPostConnection.SuccessCallback,NetPostConnection.FailCallback{
    //PPT按钮
    public ImageButton btn_ppt;
    private NetPostConnection conn;
    private String safecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    /**
     * 初始化界面元素
     */
    private void init() {
        btn_ppt = (ImageButton) findViewById(R.id.btn_ppt);
        btn_ppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                safecode = generateSafeCode();
                i.setClass(MainActivity.this, DeckViewSampleActivity.class);
                MainActivity.this.startActivity(i);
            }
        });


    }

    private String generateSafeCode(){
        long time = System.currentTimeMillis();

        String SafeCode =Long.toHexString(time).substring(0,6);

        return SafeCode;
    }


    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(String result) throws JSONException {

    }
}
