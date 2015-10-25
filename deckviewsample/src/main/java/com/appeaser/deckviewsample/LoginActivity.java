package com.appeaser.deckviewsample;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;


public class LoginActivity extends Activity {

    private Button btn_login,btn_safe_code;
    private TextView tv_safe_code;
    private EditText et_username;
    private View MyDialogView;
    private Dialog mydialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_safe_code = (Button) findViewById(R.id.btn_safe_code);
        tv_safe_code = (TextView) findViewById(R.id.tv_safe_code);
        et_username = (EditText) findViewById(R.id.et_username);
        MyDialogView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.my_dialog,null);
        mydialog = new Dialog(LoginActivity.this,R.style.MyDialog);
        mydialog.setContentView(MyDialogView);

        Window dialogWindowx = mydialog.getWindow();
        WindowManager.LayoutParams managerx = dialogWindowx.getAttributes();
        int WIDTH = getResources().getDisplayMetrics().widthPixels;
        int HEIGHT = getResources().getDisplayMetrics().heightPixels;
        managerx.width = (WIDTH / 7) * 4;
        managerx.height = HEIGHT / 4;
        dialogWindowx.setAttributes(managerx);

        Configs.SAFE_CODE = generateSafeCode();


        btn_safe_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tv_safe_code.setText(generateSafeCode());
                Configs.SAFE_CODE = generateSafeCode();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!tv_safe_code.getText().toString().isEmpty()&&(!et_username.getText().toString().isEmpty()))){
                    mydialog.show();
                    new NetPostConnection(Configs.URL_SAFE_CODE, new NetPostConnection.SuccessCallback() {
                        @Override
                        public void onSuccess(String result) throws JSONException {
                            if(result.equals("0")){
                                mydialog.dismiss();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.putExtra("USERNAME",et_username.getText().toString());
                                startActivity(i);
                                LoginActivity.this.finish();
                            }else {
                                mydialog.dismiss();
                                Toast.makeText(LoginActivity.this, "登陆错误", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new NetPostConnection.FailCallback() {
                        @Override
                        public void onFail() {
                        }
                    },"match_code",Configs.SAFE_CODE);
                }else {
                    Toast.makeText(LoginActivity.this, "登陆错误", Toast.LENGTH_LONG).show();
                }
                }
        });

    }

    private String generateSafeCode(){
        long time = System.currentTimeMillis();

        int size = Long.toHexString(time).length();

        String SafeCode =Long.toHexString(time).substring(size-7,size-1);

        return SafeCode;
    }



}
