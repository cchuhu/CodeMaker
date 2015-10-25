package com.appeaser.deckviewsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity implements NetPostConnection.SuccessCallback,NetPostConnection.FailCallback{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private String username;
    private Infos info;
    private TextView cancel;


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
        cancel = (TextView) findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //填写注销事件
            }
        });

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
                Configs.listInfos.clear();
                myAdapter.Clear();

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
                                Configs.listInfos.add(info);
                            }
                            myAdapter.addAll(Configs.listInfos);
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

                Toast.makeText(MainActivity.this, "lalallalala:" + position, Toast.LENGTH_LONG).show();
                new NetPostConnection(Configs.URL_CHANGE_PID, new NetPostConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        System.out.println(" List Table");
                        Intent intent = new Intent(MainActivity.this,DeckViewSampleActivity.class);
                        intent.putExtra("pnum", Integer.parseInt(result));
                        startActivity(intent);
                    }
                }, new NetPostConnection.FailCallback() {
                    @Override
                    public void onFail() {

                    }
                }, "pid" , Configs.listInfos.get(position).getPid());
            }
        });

    }

    //断开连接
    private void breakConnection(){

    }


    @Override
    public void onFail() {

    }

    @Override
    public void onSuccess(String result) throws JSONException {

    }
}
