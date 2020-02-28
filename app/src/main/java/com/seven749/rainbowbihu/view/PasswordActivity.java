package com.seven749.rainbowbihu.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seven749.rainbowbihu.R;
import com.seven749.rainbowbihu.uitls.httphelper.CallBack;
import com.seven749.rainbowbihu.uitls.httphelper.NetUtil;
import com.seven749.rainbowbihu.uitls.httphelper.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordActivity extends BaseActivity {

    private EditText editOldPassword, editNewPassword, editNewPasswordAgain;
    private Button changeOk;
    private final static String lastUrl = "changePassword.php";
    private String oldPassword, newPassword, newPasswordA;
    private Map<String, Object> postData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editOldPassword = (EditText)findViewById(R.id.password_old);
        editNewPassword = (EditText)findViewById(R.id.password_new);
        editNewPasswordAgain = (EditText)findViewById(R.id.password_new_again);
        changeOk = (Button)findViewById(R.id.change_password);
        changeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = editOldPassword.getText().toString();
                newPassword = editNewPassword.getText().toString();
                newPasswordA = editNewPasswordAgain.getText().toString();
                if ("".equals(oldPassword) || "".equals(newPassword) || "".equals(newPasswordA)) {
                    Toast.makeText(PasswordActivity.this, "输入框不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (!MainActivity.password.equals(oldPassword)){
                    Toast.makeText(PasswordActivity.this, "请输入正确的旧密码",
                            Toast.LENGTH_SHORT).show();
                } else if (8 > newPassword.length() || newPassword.length() > 16) {
                    Toast.makeText(PasswordActivity.this, "密码8~16位",
                            Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(newPasswordA)) {
                    Toast.makeText(PasswordActivity.this, "两次输入的新密码不一致",
                            Toast.LENGTH_SHORT).show();
                } else if (newPassword.equals(newPasswordA)) {
                    Toast.makeText(PasswordActivity.this, "新密码不能和旧密码一样",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // post change password
                    sendRequest();
                }
            }
        });
    }
    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    postData.put("password", newPassword);
                    postData.put("token", MainActivity.token);
                    Request request = new Request.Builder()
                            .url(MainActivity.baseUrl+lastUrl)
                            .method("POST")
                            .hashMap(postData)
                            .build();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                            final String token = jsonObject.getJSONObject("data").getString("token");
                            Toast.makeText(PasswordActivity.this, "修改成功！",
                                    Toast.LENGTH_SHORT).show();
                            MainActivity.token = token;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(PasswordActivity.this, info + "，修改失败...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
