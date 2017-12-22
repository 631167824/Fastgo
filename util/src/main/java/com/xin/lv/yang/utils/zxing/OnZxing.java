package com.xin.lv.yang.utils.zxing;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;

import com.google.zxing.Result;
import com.xin.lv.yang.utils.zxing.camera.CameraManager;

public interface OnZxing {
    CameraManager getCameraManager();

    Handler getHandler();

    Rect getCropRect();

    void handleDecode(Result result, Bundle bundle);
}
