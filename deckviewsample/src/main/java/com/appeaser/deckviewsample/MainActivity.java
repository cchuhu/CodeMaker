package com.appeaser.deckviewsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements NetPostConnection.SuccessCallback,NetPostConnection.FailCallback{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;


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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        //set animation theme,you can set 4 colors at most
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light);
        //set your list
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this,"已经没有更新的啦",Toast.LENGTH_LONG).show();

                Intent i = new Intent();
                i.setClass(MainActivity.this, DeckViewSampleActivity.class);
                MainActivity.this.startActivity(i);
            }
        });

        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        //设置布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        //设置adapter
        MyAdapter myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);

        List<Infos> mLists = new ArrayList<>();
        Infos jb;
        for(int i=0;i<5;i++){
            jb = new Infos();
            jb.setPid(""+1111*i);
            mLists.add(jb);
        }
        myAdapter.addAll(mLists);


    }


    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(String result) throws JSONException {

    }
}
