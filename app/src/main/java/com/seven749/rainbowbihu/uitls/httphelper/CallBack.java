package com.seven749.rainbowbihu.uitls.httphelper;

public interface CallBack {

    void onResponse(String response);

    void onFailed(Exception e);
}
