package com.seven749.rainbowbihu.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seven749.rainbowbihu.model.Answer;
import com.seven749.rainbowbihu.uitls.MyUtil;
import com.seven749.rainbowbihu.R;
import com.seven749.rainbowbihu.uitls.httphelper.CallBack;
import com.seven749.rainbowbihu.uitls.httphelper.NetUtil;
import com.seven749.rainbowbihu.uitls.httphelper.Request;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuestionOpenedActivity extends BaseActivity {
    private TextView textTitle,textContent, textDate, textAuthor;
    private Button buttonAddCollection, buttonSendAnswer;
    private EditText editContentA;
    private String lastUrl, contentA;
    private Map<String, Object> hashMapFavorite, hashMapAnswer, hashMapSend;
    private List<Answer> answerList = new ArrayList<>();
    private AnswerAdapter adapter;
    private boolean isF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_opened);
        Intent intent = getIntent();
        final String qid = intent.getStringExtra("qid");
        String title = intent.getStringExtra("title");
        final String content = intent.getStringExtra("content");
        String images = intent.getStringExtra("images");
        String author = intent.getStringExtra("author");
        String date = intent.getStringExtra("date");
        isF = intent.getBooleanExtra("isF", false);

        textTitle = (TextView) findViewById(R.id.show_title_q);
        textContent = (TextView) findViewById(R.id.show_content_q);
        textDate = (TextView) findViewById(R.id.show_date_q);
        textAuthor = (TextView) findViewById(R.id.show_author_q);
        textTitle.setText(title);
        textContent.setText(content);
        textDate.setText(date);
        textAuthor.setText(author);
        buttonAddCollection = (Button) findViewById(R.id.add_collection);
        buttonSendAnswer = (Button) findViewById(R.id.button_send_answer);
        editContentA = (EditText) findViewById(R.id.edit_answer);
        if (MainActivity.isCollection || isF) {
            buttonAddCollection.setBackgroundResource(R.color.colorGery);
            buttonAddCollection.setText("已收藏");
            buttonAddCollection.setTextColor(buttonAddCollection.getResources().getColor(R.color.colorBlack));
        }
        initAnswer(qid);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.answer_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AnswerAdapter(QuestionOpenedActivity.this, answerList);
        recyclerView.setAdapter(adapter);
        buttonSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentA = editContentA.getText().toString();
                if (!contentA.equals("")) {
                    lastUrl = "answer.php";
                    hashMapSend = new HashMap<String, Object>() {{
                        put("qid", qid);
                        put("content", contentA);
                        put("images", "");
                        put("token", MainActivity.token);
                    }};
                    Request request = new Request.Builder()
                            .url(MainActivity.baseUrl+lastUrl)
                            .method("POST")
                            .hashMap(hashMapSend)
                            .build();
                    NetUtil.getInstance().execute(request, new CallBack() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final String status =  jsonObject.getString("status");
                                final String info = jsonObject.getString("info");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (status.equals("200")) {
                                            editContentA.setText(null);
                                            initAnswer(qid);
                                            Toast.makeText(QuestionOpenedActivity.this, "回答成功",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(QuestionOpenedActivity.this, info + "回答失败...",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailed(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        buttonAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashMapFavorite = new HashMap<String, Object>() {{
                    put("qid", qid);
                    put("token", MainActivity.token);
                }};
                if (MainActivity.isCollection || isF) {
                    sendCancel();
                } else {
                    sendAdd();
                }
                if (MainActivity.isCollection) {
                    finish();
                }
            }
        });

    }

    private void initAnswer(final String qid) {
        lastUrl = "getAnswerList.php";
        hashMapAnswer = new HashMap<String, Object>() {{
            put("page", 0);
            put("count", 20);
            put("qid",qid);
            put("token", MainActivity.token);
        }};
        answerList.clear();
        Request request = new Request.Builder()
                .url(MainActivity.baseUrl+lastUrl)
                .method("POST")
                .hashMap(hashMapAnswer)
                .build();
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                try {
                    final JSONObject jsonObject = new JSONObject(response);
                    final String status = jsonObject.getString("status");
                    final String info = jsonObject.getString("info");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status.equals("200")) {
                                try {
                                    JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("data").get("answers").toString());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonAnswer = jsonArray.getJSONObject(i);
                                        final String contentA = jsonAnswer.getString("content");
                                        final String imagesA = jsonAnswer.getString("images");
                                        final String dateA = jsonAnswer.getString("date");
                                        final String authorNameA = jsonAnswer.getString("authorName");
                                        final String avatarA = jsonAnswer.getString("authorAvatar");
                                        Answer answer = new Answer(contentA, dateA, authorNameA, avatarA);
                                        answerList.add(answer);
                                    }
//                                    Toast.makeText(QuestionOpenedActivity.this, "刷新成功！",
//                                            Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(QuestionOpenedActivity.this, info + "，获取回答失败...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendAdd() {
        lastUrl = "favorite.php";
        Request request = new Request.Builder()
                .url(MainActivity.baseUrl+lastUrl)
                .method("POST")
                .hashMap(hashMapFavorite)
                .build();
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    final String status = object.getString("status");
                    if (status.equals("200")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isF = !isF;
                                initButton();
                                Toast.makeText(QuestionOpenedActivity.this, "已收藏",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void sendCancel() {
        lastUrl = "cancelFavorite.php";
        Request request = new Request.Builder()
                .url(MainActivity.baseUrl+lastUrl)
                .method("POST")
                .hashMap(hashMapFavorite)
                .build();
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    final String status = object.getString("status");
                    if (status.equals("200")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isF = !isF;
                                initButton();
                                Toast.makeText(QuestionOpenedActivity.this, "已取消",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void initButton(){
        if (isF) {
            buttonAddCollection.setBackgroundResource(R.color.colorGery);
            buttonAddCollection.setText("已收藏");
            buttonAddCollection.setTextColor(buttonAddCollection.getResources().getColor(R.color.colorBlack));
        } else {
            buttonAddCollection.setBackgroundResource(R.color.colorBlue);
            buttonAddCollection.setText("添加收藏");
            buttonAddCollection.setTextColor(buttonAddCollection.getResources().getColor(R.color.colorGeryWhite));
        }
    }
}
