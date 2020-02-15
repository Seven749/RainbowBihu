package com.seven749.rainbowbihu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private List<Answer> mAnswerList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View answerView;
        TextView aContent, aDate, aAuthor;
        ImageView images;

        public  ViewHolder(View view) {
            super(view);
            answerView = view;
            aContent = (TextView) view.findViewById(R.id.show_content_a);
            aDate = (TextView) view.findViewById(R.id.show_date_a);
            aAuthor = (TextView) view.findViewById(R.id.show_author_a);
        }

    }
    public AnswerAdapter(Context context, List<Answer> answerList) {
        this.context = context;
        mAnswerList = answerList;
    }

    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.answer_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Answer answer  = mAnswerList.get(position);
        holder.aContent.setText(answer.getContent());
        holder.aDate.setText(answer.getDate());
        holder.aAuthor.setText(answer.getAuthorName());

    }
}
