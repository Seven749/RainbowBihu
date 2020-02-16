package com.seven749.rainbowbihu.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.seven749.rainbowbihu.R;

public class YQTitle extends LinearLayout implements View.OnClickListener{
    public YQTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.yq_title, this);
//        ImageButton yqBack = (ImageButton)findViewById(R.id.yq_back);
//        ImageButton yqRefresh = (ImageButton)findViewById(R.id.yq_refresh);
//        yqBack.setOnClickListener(this);
//        yqRefresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.yq_back:
//                ((Activity)getContext()).finish();
//                break;
//            case R.id.yq_refresh:
//
//                break;
//            default:
//                break;
//        }
    }
}
