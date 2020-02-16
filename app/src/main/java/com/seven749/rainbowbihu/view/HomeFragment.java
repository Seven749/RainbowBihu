package com.seven749.rainbowbihu.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seven749.rainbowbihu.control.MainActivity;
import com.seven749.rainbowbihu.uitl.MyUtil;
import com.seven749.rainbowbihu.model.Question;
import com.seven749.rainbowbihu.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    private String lastUrl;
    private List<Question> questionList = new ArrayList<>();
    private List<Question> questionListC = new ArrayList<>();
    private Map<String, Object> postData;
    private RecyclerView recyclerView;
    public static QuestionAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        if (MainActivity.isLogin) {
            initQuestion(getActivity(), getContext());
            if (MainActivity.isCollection) {
                adapter = new QuestionAdapter(getContext(),questionListC);
            }else {
                adapter = new QuestionAdapter(getContext(), questionList);
            }
//            Log.d(TAG, "onViewCreated: adapter");
            recyclerView = (RecyclerView) view.findViewById(R.id.recommend_recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
//            Log.d(TAG, "onViewCreated: setAdapter");
        } else {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
//        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }

    public void initQuestion(final Activity activity, Context context) {
/* 随机数
        final long l = System.currentTimeMillis();
        final int i = (int) (l % 11);
 */
        postData = new HashMap<String, Object>() {{
            put("page",  0);
            put("count", 20);
            put("token", MainActivity.token);
        }};
        if (MainActivity.isCollection) {
            lastUrl = "getFavoriteList.php";
            questionListC.clear();
        } else {
            lastUrl = "getQuestionList.php";
            questionList.clear();
        }
        MyUtil.sendOkHttpUtil(MainActivity.baseUrl + lastUrl, postData, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                parseJSON(response.body().string());
            }
        });
    }

    private  void parseJSON(String jsonData) {
        try {
            final JSONObject jsonObject = new JSONObject(jsonData);
            final String status = jsonObject.getString("status");
            final String info = jsonObject.getString("info");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (status.equals("200")) {
                        try {
                            JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("data").get("questions").toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonQuestion = jsonArray.getJSONObject(i);
                                final String idQ = jsonQuestion.getString("id");
                                final String titleQ = jsonQuestion.getString("title");
                                final String contentQ = jsonQuestion.getString("content");
//                                Log.d(TAG, "run: " + contentQ);
                                final String imagesQ = jsonQuestion.getString("images");
                                final String dateQ = jsonQuestion.getString("date");
                                final String authorNameQ = jsonQuestion.getString("authorName");
                                final String avatarQ = jsonQuestion.getString("authorAvatar");
                                if (MainActivity.isCollection) {
                                    Question question = new Question(idQ, titleQ, contentQ, imagesQ, dateQ, authorNameQ, avatarQ);
                                    questionListC.add(question);
                                    adapter.notifyDataSetChanged();
                                }else {
                                    final boolean isF = jsonQuestion.getBoolean("is_favorite");
                                    Question question = new Question(idQ, titleQ, contentQ, imagesQ, dateQ, authorNameQ, avatarQ,isF);
                                    questionList.add(question);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            Toast.makeText(getContext(), "刷新成功！",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), info + "，刷新失败...",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}