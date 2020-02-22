package com.seven749.rainbowbihu.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.seven749.rainbowbihu.uitls.MyUtil;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        MyUtil.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtil.removeActivity(this);
    }
}
