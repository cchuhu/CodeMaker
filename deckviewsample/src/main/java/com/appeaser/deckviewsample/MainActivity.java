package com.appeaser.deckviewsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    //PPT按钮
    public ImageButton btn_ppt;

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
                i.setClass(MainActivity.this, DeckViewSampleActivity.class);
                MainActivity.this.startActivity(i);
            }
        });


    }


}
