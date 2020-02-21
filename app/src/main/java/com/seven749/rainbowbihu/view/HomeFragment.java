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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.seven749.rainbowbihu.uitls.MyUtil;
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
    private SwipeRefreshLayout swipeRefresh;
    public static QuestionAdapter adapter;
    private int getQ = 1, getC = 1;
//    private TextView nextPage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorBlue);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
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
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (MyUtil.isSlideToBottom(recyclerView)) {
                        addNewPage();
                    }
                }

                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
//            Log.d(TAG, "onViewCreated: setAdapter");
        } else {
            Toast.makeText(getContext(), "请先登录！", Toast.LENGTH_SHORT).show();
        }
//        nextPage = (TextView)view.findViewById(R.id.add_next);
//        nextPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addNewPage();
//            }
//        });
//        Log.d(TAG, "onCreateView: ");
        return view;
    }

    private void addNewPage() {
        /* 随机数
        final long l = System.currentTimeMillis();
        final int i = (int) (l % 11);
        */
        final int page;
        if (MainActivity.isCollection) {
            lastUrl = "getFavoriteList.php";
            page = getC;
            getC++;
        } else {
            lastUrl = "getQuestionList.php";
            page = getQ;
            getQ++;
        }
        postData = new HashMap<String, Object>() {{
            put("page",  page);
            put("count", 20);
            put("token", MainActivity.token);
        }};
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

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initQuestion(getActivity(), getContext());
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新成功！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
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