package com.xin.lv.yang.utils.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.lang.ref.WeakReference;

/**
 * 基础handler
 */
public abstract class BaseHandler<T> extends Handler {

    protected WeakReference<T> weak;

    private static final int ERROR = 404;

    public BaseHandler(T t) {
        if (weak == null) {
            weak = new WeakReference<>(t);
        }
    }

    private static OnNetError onNetError;

    public static void setOnNetError(OnNetError onNetError) {
        BaseHandler.onNetError = onNetError;
    }

    protected abstract void hanMessage(Message msg);

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case ERROR:
                // 网络请求错误提示
                if (onNetError != null) {
                    onNetError.onError();
                }
                break;
            default:
                hanMessage(msg);
                break;
        }
    }
}
