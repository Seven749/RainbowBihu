package com.seven749.rainbowbihu.uitl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class DoubleClickExitHelper {

    private Context mContext;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DoubleClickExitHelper(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     * */
    public boolean onKeyDown(int keyCode) {
        if(keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if(isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if(mBackToast != null){
                mBackToast.cancel();
            }
            Log.i("nnn", "isOnKeyBacking***********************");
//Activity 堆栈式管理，退出应用时，退出所有的Activity
            MyUtil.finishAll();
            return true;
        } else {
            isOnKeyBacking = true;
            if(mBackToast == null) {
                Log.i("nnn", "null***********************");
                mBackToast = Toast.makeText(mContext, "再按一次，即退出应用",
                        Toast.LENGTH_LONG);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };
}
