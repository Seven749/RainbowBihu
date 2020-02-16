package com.seven749.rainbowbihu.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.seven749.rainbowbihu.R;
import com.seven749.rainbowbihu.control.RealTimeYQActivity;

import java.util.Calendar;

public class TitleLayout extends LinearLayout {

    private String[] days = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};
    private  Button buttonRealTimeYQ;

    public TitleLayout(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_title, this);
        Calendar cal = Calendar.getInstance();
        TextView date = (TextView) findViewById(R.id.date);
        TextView day = (TextView) findViewById(R.id.day);
        date.setText(""+cal.get(Calendar.DATE));
        day.setText(days[cal.get(Calendar.DAY_OF_WEEK) - 1]);
        buttonRealTimeYQ = (Button)findViewById(R.id.button_yq);
        buttonRealTimeYQ.setBackgroundResource(R.drawable.button_new);
        buttonRealTimeYQ.setBackgroundResource(R.color.colorGeryWhite);
        buttonRealTimeYQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RealTimeYQActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
