package com.seven749.rainbowbihu.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seven749.rainbowbihu.R;
import com.seven749.rainbowbihu.model.Question;
import com.seven749.rainbowbihu.uitls.MyUtil;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private List<Question> mQuestionList;
    private Context context;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View questionView;
        TextView qTitle, qContent, qDate, qAuthor;
        ImageView qImages, qAvatar;

        public  ViewHolder(View view) {
            super(view);
            questionView = view;
            qTitle = (TextView) view.findViewById(R.id.text_title_q);
            qContent = (TextView) view.findViewById(R.id.text_content_q);
            qDate = (TextView) view.findViewById(R.id.text_date_q);
            qAuthor = (TextView) view.findViewById(R.id.author_q);
            qAvatar = (ImageView) view.findViewById(R.id.avatar_q);
        }
    }
    public QuestionAdapter(Context context, List<Question> questionList) {
        this.context = context;
        mQuestionList = questionList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Question question = mQuestionList.get(position);
        holder.qTitle.setText(question.getTitle());
        holder.qContent.setText(question.getContent());
        holder.qDate.setText(question.getDate());
        holder.qAuthor.setText(question.getAuthorName());
        holder.qAvatar.setImageBitmap(MyUtil.getURLImage(question.getAvatar()));
        holder.questionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qid = question.getQid();
                String title = question.getTitle();
                String content = question.getContent();
                String images = question.getImage();
                String author = question.getAuthorName();
                String date = question.getDate();
                boolean isF = question.getIsF();
                Intent intent = new Intent(context, QuestionOpenedActivity.class);
                intent.putExtra("qid", qid);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("images", images);
                intent.putExtra("author", author);
                intent.putExtra("date", date);
                intent.putExtra("isF", isF);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
}
