package com.seven749.rainbowbihu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seven749.rainbowbihu.uitls.MyUtil;
import com.seven749.rainbowbihu.R;

public class MineFragment extends Fragment implements View.OnClickListener{

    private TextView toLogin;
    private LinearLayout itemPassword, itemHeadPortrait, itemAbout;
    private Button buttonCancelLogin;
    private Context context = getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        toLogin = (TextView)view.findViewById(R.id.my_login);
        itemPassword = (LinearLayout)view.findViewById(R.id.to_password);
        itemHeadPortrait = (LinearLayout)view.findViewById(R.id.to_head_portrait);
        itemAbout = (LinearLayout) view.findViewById(R.id.about);
        buttonCancelLogin = (Button) view.findViewById(R.id.button_cancel_login);
        if (MainActivity.isLogin) {
            toLogin.setText(MainActivity.username);
        } else {
            toLogin.setText("点击登录或注册");
            toLogin.setOnClickListener(this);
        }
        itemPassword.setOnClickListener(this);
        itemHeadPortrait.setOnClickListener(this);
        itemAbout.setOnClickListener(this);
        buttonCancelLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intentLogin = new Intent(getContext(), LoginActivity.class);
        Intent intentPassword = new Intent(getContext(), PasswordActivity.class);
        Intent intentAbout = new Intent(getContext(), AboutMeActivity.class);
        switch (v.getId()) {
            case R.id.my_login:
                startActivity(intentLogin);
                break;
            case R.id.to_password:
                if (MainActivity.isLogin) {
                    // 修改密码
                    startActivity(intentPassword);
                } else {
                    // 跳转登录页面
                    Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                }
                break;
            case R.id.to_head_portrait:
                Toast.makeText(getContext(),"下个版本更新...",
                        Toast.LENGTH_SHORT).show();
                if (MainActivity.isLogin) {
                    //打开相册选择
                } else {
                    // 跳转登录页面
                    Toast.makeText(context, "请先登录...", Toast.LENGTH_SHORT).show();
                    startActivity(intentLogin);
                }
                break;
            case R.id.about:
                startActivity(intentAbout);
                break;
            case R.id.button_cancel_login:
                MainActivity.isLogin = false;
                MainActivity.isCollection = false;
                MainActivity.token = null;
                MyUtil.restartApp(getContext());
                break;
            default:
                break;
        }
    }
}
