package com.seven749.rainbowbihu.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seven749.rainbowbihu.uitls.DoubleClickExitHelper;
import com.seven749.rainbowbihu.R;
import com.seven749.rainbowbihu.uitls.httphelper.CallBack;
import com.seven749.rainbowbihu.uitls.httphelper.NetUtil;
import com.seven749.rainbowbihu.uitls.httphelper.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private Button buttonPostLogin;
    private TextView toRegister;
    private EditText editUsername, editPassword;
    private String username,password;
    private final static String lastUrl = "login.php";
    private Map<String, Object> postDate = new HashMap<>();
    private DoubleClickExitHelper doubleClickExitHelper = new DoubleClickExitHelper(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonPostLogin = (Button)findViewById(R.id.post_login);
        toRegister = (TextView)findViewById(R.id.to_register);
        buttonPostLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        editUsername = (EditText)findViewById(R.id.username_l);
        editPassword = (EditText)findViewById(R.id.password_l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doubleClickExitHelper.onKeyDown(keyCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_login:
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                if ("".equals(username) || "".equals(password)) {
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (3 > username.length() || username.length() > 14) {
                    Toast.makeText(LoginActivity.this, "用户名3~14位",
                            Toast.LENGTH_SHORT).show();
                } else if (8 > password.length() || password.length() > 16) {
                    Toast.makeText(LoginActivity.this, "密码8~16位",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // post login
                    postDate.put("username", username);
                    postDate.put("password", password);
                    Request request = new Request.Builder().url(MainActivity.baseUrl+lastUrl).method("POST").hashMap(postDate).build();
                    NetUtil.getInstance().execute(request, new CallBack() {
                        @Override
                        public void onResponse(String response) {
                            parseJSON(response);
                        }

                        @Override
                        public void onFailed(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
                break;
            case R.id.to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void parseJSON(String jsonData) {
        try {
            final JSONObject jsonObject = new JSONObject(jsonData);
            final String status = jsonObject.getString("status");
            final String info = jsonObject.getString("info");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (status.equals("200")) {
                        try {
                            final String id = jsonObject.getJSONObject("data").getString("id");
                            final String username = jsonObject.getJSONObject("data").getString("username");
                            final String token = jsonObject.getJSONObject("data").getString("token");
                            Toast.makeText(LoginActivity.this, "登录成功！",
                                    Toast.LENGTH_SHORT).show();
                            MainActivity.username = username;
                            MainActivity.token = token;
                            MainActivity.isLogin = true;
//                            MineFragment.toLogin
                            setResult(1);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, info + "，登录失败...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
