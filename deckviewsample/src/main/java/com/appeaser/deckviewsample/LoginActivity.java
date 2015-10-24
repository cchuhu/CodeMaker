package com.appeaser.deckviewsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class LoginActivity extends Activity {

    private Button btn_login,btn_safe_code;
    private TextView tv_safe_code;
    private EditText et_username;
    private boolean generated=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_safe_code = (Button) findViewById(R.id.btn_safe_code);
        tv_safe_code = (TextView) findViewById(R.id.tv_safe_code);
        et_username = (EditText) findViewById(R.id.et_username);

        Configs.SAFE_CODE = generateSafeCode();


        btn_safe_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NetPostConnection(Configs.URL_SAFE_CODE, new NetPostConnection.SuccessCallback() {
                    @Override
                    public void onSuccess(String result) throws JSONException {
                        if (result.equals("0")){
                            tv_safe_code.setText(Configs.SAFE_CODE);
                            generated=true;
                        }else {
                            tv_safe_code.setText("生成失败，请重新生成");
                        }
                    }
                }, new NetPostConnection.FailCallback() {
                    @Override
                    public void onFail() {
                        tv_safe_code.setText("生成失败，请重新生成");
                    }
                },"safe_code",Configs.SAFE_CODE);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generated&&(!et_username.getText().toString().isEmpty())){
                    new NetPostConnection(Configs.URL_LOGIN, new NetPostConnection.SuccessCallback() {
                        @Override
                        public void onSuccess(String result) throws JSONException {


                            //TODO waiting for the PHP results

                            //成功则跳转到主界面

                        }
                    }, new NetPostConnection.FailCallback() {
                        @Override
                        public void onFail() {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                        }
                    },"username",et_username.getText().toString());
                }else {
                    Toast.makeText(LoginActivity.this,"请先输入用户名或在网页端填写安全码",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private String generateSafeCode(){
        long time = System.currentTimeMillis();

        String SafeCode =Long.toHexString(time).substring(0,6);

        return SafeCode;
    }

}
