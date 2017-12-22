package com.xin.lv.yang.utils.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.xin.lv.yang.utils.R;


/**
 * 自定义加载dialog
 */

public class CustomProgressDialog extends Dialog {
    Context context;

    public CustomProgressDialog(@NonNull Context context) {
        super(context, R.style.style);
        this.context = context;
        init();
    }

    public CustomProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }


    private void init() {
        View view = getLayoutInflater().inflate(R.layout.load_layout, null);
        AVLoadingIndicatorView avl = view.findViewById(R.id.avl);
        this.setContentView(view);

        avl.show();

    }


    private static final String[] INDICATORS = new String[]{
            "BallPulseIndicator",
            "BallGridPulseIndicator",
            "BallClipRotateIndicator",
            "BallClipRotatePulseIndicator",
            "SquareSpinIndicator",
            "BallClipRotateMultipleIndicator",
            "BallPulseRiseIndicator",
            "BallRotateIndicator",
            "CubeTransitionIndicator",
            "BallZigZagIndicator",
            "BallZigZagDeflectIndicator",
            "BallTrianglePathIndicator",
            "BallScaleIndicator",
            "LineScaleIndicator",
            "LineScalePartyIndicator",
            "BallScaleMultipleIndicator",
            "BallPulseSyncIndicator",
            "BallBeatIndicator",
            "LineScalePulseOutIndicator",
            "LineScalePulseOutRapidIndicator",
            "BallScaleRippleIndicator",
            "BallScaleRippleMultipleIndicator",
            "BallSpinFadeLoaderIndicator",
            "LineSpinFadeLoaderIndicator",
            "TriangleSkewSpinIndicator",
            "PacmanIndicator",
            "BallGridBeatIndicator",
            "SemiCircleSpinIndicator",

    };

}
