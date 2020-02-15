package com.seven749.rainbowbihu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtil.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtil.removeActivity(this);
    }
}
