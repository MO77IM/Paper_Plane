package com.paperplane;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
public class EnterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editPersion,editCode; //用户名、密码的输入框
    private TextView textViewR; //注册按钮
    private Button btn; //登录按钮
    private String currentUserName,currrentPassword; //加载输入完成后的用户名和密码

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_enter);
        init();
    }
     private  void init() {
         btn = (Button) findViewById(R.id.bn_common_login);
         btn.setOnClickListener(this);
         editPersion =(EditText) findViewById(R.id.et_username);
         editCode =(EditText) findViewById(R.id.et_password);
         textViewR =(TextView) findViewById(R.id.tv_register);
         textViewR.setOnClickListener(this);
     }

    /**
     * 点击事件
     * @param
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bn_common_login:
                login();
                break;
            case  R.id.tv_register:
                /**
                 * 跳转注册
                 */
                Intent intent =new Intent(EnterActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    private void login() {
        currentUserName=editPersion.getText().toString().trim();  //获取手机号，去除空格
        currrentPassword=editCode.getText().toString().trim(); //获取密码


        if(TextUtils.isEmpty(currentUserName)){
            Toast.makeText(this, "账号不为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(currrentPassword)){
            Toast.makeText(this, "密码不为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog pd = new ProgressDialog(EnterActivity.this);//等待
        pd.setMessage("正在登陆...");
        pd.show();//显示等待条

        /**
         * 模拟后台
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                pd.dismiss();//等待条消失
                /**
                 * 跳转
                 */
                Intent intent =new Intent(EnterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();//销毁
            }
        }).start();//开启线程
    }
}
