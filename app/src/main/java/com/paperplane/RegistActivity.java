package com.paperplane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

///import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextP,editTextCT,editTextSMS;//输入框：电话、密码、验证码
    private Button button,SMSBtn;//注册按钮、验证码按钮
    private ImageView returnImage;//返回按钮
    private TextView enterText;//登陆按钮
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_regist);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        editTextP=(EditText)findViewById(R.id.et_phone_num);
        editTextCT=(EditText)findViewById(R.id.et_password);
        editTextSMS=(EditText) findViewById(R.id.et_sms_code);
        button=(Button)findViewById(R.id.btn_now_register);
        enterText=(TextView)findViewById(R.id.tv_enter);
        returnImage=(ImageView) findViewById(R.id.iv_return);
        SMSBtn=(Button)findViewById(R.id.bn_sms_code);
        button.setOnClickListener(this);
        enterText.setOnClickListener(this);
        returnImage.setOnClickListener(this);
        SMSBtn.setOnClickListener(this);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_now_register:
                register();
                break;
            case R.id.tv_enter:
                returnEnter();
                break;
            case R.id.iv_return:
                returnEnter();
                break;
            case R.id.bn_sms_code:
                String phone=editTextP.getText().toString().trim();
                if(phone.length()!=11){
                    Toast.makeText(this, "电话号码不可用", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void register() {
        String username=editTextP.getText().toString().trim();//获取电话号
        String code=editTextSMS.getText().toString().trim();//获取验证码
        String password=editTextCT.getText().toString().trim();//获取设置密码
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this, "未输入手机号", Toast.LENGTH_SHORT).show();
            editTextP.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(code)){
            Toast.makeText(this, "未输入验证码", Toast.LENGTH_SHORT).show();
            editTextSMS.requestFocus();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "未设置密码", Toast.LENGTH_SHORT).show();
            editTextCT.requestFocus();
            return;
        }
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("正在注册。。。。" );
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                pd.dismiss();
                returnEnter();
            }
        }).start();//开启线程
    }

    /**
     * 跳转到登陆界面
     */
    private void returnEnter() {
        Intent intent=new Intent(this,EnterActivity.class);
        startActivity(intent);
    }
}
