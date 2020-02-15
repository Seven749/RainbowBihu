package com.seven749.rainbowbihu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private  Button buttonCollection, buttonRecommend;
    private ImageButton buttonMine, buttonHome, buttonAdd;
    private TextView textHome, textMine;
    private LinearLayout titleLayout;
    private DoubleClickExitHelper doubleClickExitHelper = new DoubleClickExitHelper(MainActivity.this);
    public static String password;
    public static final String baseUrl = "http://bihu.jay86.com/";
    public static String token, username;
    public static boolean isLogin = false, isCollection = false;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    replaceFragmentHome(new HomeFragment());
                }
                break;
            default:
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }

        replaceFragmentHome(new HomeFragment());
        titleLayout = (LinearLayout)findViewById(R.id.title);
        textHome = (TextView) findViewById(R.id.text_home);
        textMine = (TextView) findViewById(R.id.text_mine);
        buttonMine = (ImageButton) findViewById(R.id.button_mine);
        buttonHome = (ImageButton) findViewById(R.id.button_home);
        buttonAdd = (ImageButton) findViewById(R.id.button_add_question);
        buttonMine.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonCollection = (Button)findViewById(R.id.button_collection);
        buttonRecommend = (Button) findViewById(R.id.button_recommend);
        buttonCollection.setOnClickListener(this);
        buttonRecommend.setOnClickListener(this);
        initTitle();
//        startForeground();
    }

    private void startForeground() {
//        Notification notification = new NotificationCompat.Builder(this)
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setTicker(getResources().getString(R.string.app_name))
//                .setContentText("Running")
//                .setSmallIcon(R.drawable.unlock)
//                .setContentIntent(null)
//                .setOngoing(true)
//                .build();
//        startForeground(9999,notification);
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            //只在Android O之上需要渠道
            NotificationChannel notificationChannel = new NotificationChannel("channelid1","channelname",NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"channelid1");
        builder.setContentTitle("逼乎")
                .setContentText("Running...")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_bihu)
                .setLargeIcon(null)
                .setAutoCancel(true);
        manager.notify(1, builder.build());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doubleClickExitHelper.onKeyDown(keyCode);
    }

    private void initTitle() {
        buttonCollection.setTextColor(buttonCollection.getResources().getColor(R.color.colorBlack));
        buttonCollection.setBackgroundResource(R.drawable.button_new);
        buttonCollection.setTextColor(buttonCollection.getResources().getColor(R.color.colorGeryDark));
        buttonCollection.setBackgroundResource(R.color.colorGeryWhite);
        buttonRecommend.setTextColor(buttonRecommend.getResources().getColor(R.color.colorBlack));
        buttonRecommend.setBackgroundResource(R.drawable.button_new);
    }

    private void replaceFragmentHome(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.home_layout, fragment);
        transaction.commit();
    }
//    private void replaceFragmentTitle(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.home_fragment_layout, fragment);
//        transaction.commit();
//    }
//
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_mine:
                buttonHome.setBackgroundResource(R.drawable.ic_home_gery);
                textHome.setTextColor(textHome.getResources().getColor(R.color.colorGeryDark));
                buttonMine.setBackgroundResource(R.drawable.ic_mine_blue);
                textMine.setTextColor(textMine.getResources().getColor(R.color.colorBlue));
                titleLayout.setVisibility(View.GONE);
                replaceFragmentHome(new MineFragment());
                break;
            case R.id.button_home:
                buttonHome.setBackgroundResource(R.drawable.ic_home_blue);
                textHome.setTextColor(textHome.getResources().getColor(R.color.colorBlue));
                buttonMine.setBackgroundResource(R.drawable.ic_mine_gery);
                textMine.setTextColor(textMine.getResources().getColor(R.color.colorGeryDark));
                titleLayout.setVisibility(View.VISIBLE);
//                if (isCollection) {
//
//                } else {
//                    replaceFragmentTitle(new RecommendFragment());
//                }
                replaceFragmentHome(new HomeFragment());
                break;
            case R.id.button_add_question:
                Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
                startActivity(intent);
                break;
            case R.id.button_collection:
                isCollection = true;
                replaceFragmentHome(new HomeFragment());
                buttonCollection.setTextColor(buttonCollection.getResources().getColor(R.color.colorBlack));
                buttonCollection.setBackgroundResource(R.drawable.button_new);
                buttonRecommend.setTextColor(buttonRecommend.getResources().getColor(R.color.colorGeryDark));
                buttonRecommend.setBackgroundResource(R.color.colorGeryWhite);
                break;
            case R.id.button_recommend:
                isCollection = false;
                replaceFragmentHome(new HomeFragment());
                buttonCollection.setTextColor(buttonCollection.getResources().getColor(R.color.colorGeryDark));
                buttonCollection.setBackgroundResource(R.color.colorGeryWhite);
                buttonRecommend.setTextColor(buttonRecommend.getResources().getColor(R.color.colorBlack));
                buttonRecommend.setBackgroundResource(R.drawable.button_new);
                break;
            default:
                break;
        }
    }
}
