package com.wt.fastgo_user.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xin.lv.yang.utils.activity.BaseActivity;
import com.xin.lv.yang.utils.net.BaseHandler;
import com.xin.lv.yang.utils.permission.PermissionsManager;
import com.xin.lv.yang.utils.permission.PermissionsResultAction;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ProActivity extends BaseActivity {
    public InputMethodManager inputMethodManager;
    public Handler handler;
    Unbinder unbinder;

    /**
     * 获取视图layout的id
     *
     * @return 返回id
     */
    public abstract int getContextId();

    public abstract void initAllMembersView(Bundle bundle);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   // 竖屏

        setContentView(getContextId());   // 设置View 视图

        handler = new ProHandler(this);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        /// 绑定activity
        unbinder = ButterKnife.bind(this);

        // 允许所有权限
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {

            }
        });

        getNetWork();

        initAllMembersView(savedInstanceState);

        hideKeyboard(inputMethodManager);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isHideInput(getCurrentFocus(), event)) {
                hideKeyboard(inputMethodManager);
            }
        }
        return super.dispatchTouchEvent(event);
    }


    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取网络连接状态
     */
    private void getNetWork() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (!activeNetwork.isAvailable()) {


                }
            } else {


            }
        }
    }


    /**
     * handler 网络请求返回数据
     *
     * @param msg msg 对象
     */
    public abstract void handler(Message msg);

    public void initTool(Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void initTextTitle(TextView text, String str) {
        text.setText(str);
    }

    public void initRight(TextView tvRight, String str) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(str);
        tvRight.setTextColor(ActivityCompat.getColor(this, android.R.color.darker_gray));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private static class ProHandler extends BaseHandler<ProActivity> {

        ProActivity p = weak.get();

        private ProHandler(ProActivity proActivity) {
            super(proActivity);
        }

        @Override
        protected void hanMessage(Message msg) {
            Log.i("wwwwwww", "json======" + msg.obj);

            p.handler(msg);

        }

    }

    private class Token {
        String account;
        String password;
    }

}
