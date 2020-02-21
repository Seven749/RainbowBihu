package com.seven749.rainbowbihu.view;

import androidx.appcompat.app.ActionBar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seven749.rainbowbihu.uitls.MyUtil;
import com.seven749.rainbowbihu.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddQuestionActivity extends BaseActivity {

    private EditText title, content;
    private String titleText, contentText, lastUrl = "question.php";
    private Button buttonAddQuestion;
    private Map<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        title = (EditText)findViewById(R.id.add_title);
        content = (EditText)findViewById(R.id.add_content);
        buttonAddQuestion = (Button)findViewById(R.id.button_post_add);
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText = title.getText().toString();
                contentText = content.getText().toString();
                if (titleText.equals("") || contentText.equals("")) {
                    Toast.makeText(AddQuestionActivity.this, "输入框不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    hashMap = new HashMap<String, Object>() {{
                        put("title", titleText);
                        put("content", contentText);
                        put("token", MainActivity.token);
                    }};
                    MyUtil.sendOkHttpUtil(MainActivity.baseUrl + lastUrl, hashMap, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                final String status = jsonObject.getString("status");
                                Log.d("123", "onResponse: " + status);
                                final String info = jsonObject.getString("info");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (status.equals("200")) {
                                            Toast.makeText(AddQuestionActivity.this, "发布成功！",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(AddQuestionActivity.this, info + ",发布失败...",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
