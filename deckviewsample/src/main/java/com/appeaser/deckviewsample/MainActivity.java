package com.appeaser.deckviewsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements NetPostConnection.SuccessCallback,NetPostConnection.FailCallback{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private String username;
    private List<Infos> infosList = new ArrayList<>();
    private Infos info;


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
        username = getIntent().getStringExtra("USERNAME");
        username="adadw@qq.com";

        //初始化控件
        recyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        //设置布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        //设置adapter
        final MyAdapter myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        //set animation theme,you can set 4 colors at most
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light);
        //set your list
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                new NetPostConnection(Configs.URL_GET_LIST, new NetPostConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        if (result.equals("1")) {
                            Toast.makeText(MainActivity.this, "刷新失败", Toast.LENGTH_LONG).show();
                        } else {
                            JSONArray jsonArray = new JSONArray(result);
                            JSONObject jo;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jo = (JSONObject) jsonArray.get(i);
                                info = new Infos();
                                info.setPid(jo.getString("ppt_id"));
                                info.setPnum(jo.getString("page_num"));
                                infosList.add(info);
                            }
                            myAdapter.addAll(infosList);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new NetPostConnection.FailCallback() {
                    @Override
                    public void onFail() {

                    }
                }, "username", username);

//                Intent i = new Intent();
//                i.setClass(MainActivity.this, DeckViewSampleActivity.class);
//                MainActivity.this.startActivity(i);
            }
        });



        myAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "lalallalala:"+position, Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(String result) throws JSONException {

    }
}
