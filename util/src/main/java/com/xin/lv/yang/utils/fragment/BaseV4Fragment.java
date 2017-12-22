package com.xin.lv.yang.utils.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xin.lv.yang.utils.R;
import com.xin.lv.yang.utils.utils.ImageUtil;
import com.xin.lv.yang.utils.view.CustomProgressDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class BaseV4Fragment extends Fragment {

    Activity activity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activity = getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }


     List<View> viewPagerList;
    /**
     * 初始化 viewpager 轮播图
     *
     * @param list         轮播图集合
     * @param viewPager    ViewPager 对象
     * @param linearLayout 指示器对象
     */
    public void initViewPager(final Handler handler, final List<String> list, final ViewPager viewPager, final LinearLayout linearLayout) {

        viewPagerList=new ArrayList<>();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                this.position = position;
                bangImageView(linearLayout, position);

            }

            @Override
            public void onPageSelected(int position) {

            }

            int position;

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


        if (list != null) {
            int len = list.size();
            for (int i = 0; i < len; i++) {
                ImageView v = new ImageView(activity);
                if (i == 0) {
                    v.setEnabled(true);
                } else {
                    v.setEnabled(false);
                }
                v.setPadding(0, 0, 10, 0);
                v.setImageResource(R.drawable.view_selecter);
                linearLayout.addView(v);

            }

            for (String t : list) {
                View v = LayoutInflater.from(activity).inflate(R.layout.image_layout, null);
                ImageView imageView = (ImageView) v.findViewById(R.id.image);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setAdjustViewBounds(false);

                ImageUtil.getInstance().loadImageNoTransformation(activity, imageView, R.mipmap.ic_launcher, t);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                viewPagerList.add(v);

            }

            viewPager.setCurrentItem(0);
            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return viewPagerList == null ? 0 : viewPagerList.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return (view == object);
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(viewPagerList.get(position));
                    return viewPagerList.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(viewPagerList.get(position));
                }
            });

            initRunnable(handler, viewPager, linearLayout);
        }
    }


    Runnable viewpagerRunnable;

    public static final int TIME = 30 * 1000;

    protected void initRunnable(final Handler handler, final ViewPager viewPager, final LinearLayout linearLayout) {
        viewpagerRunnable = new Runnable() {

            @Override
            public void run() {
                int nowIndex = viewPager.getCurrentItem();
                int count = viewPager.getAdapter().getCount();
                // 如果下一张的索引大于最后一张，则切换到第一张
                if (nowIndex + 1 >= count) {
                    viewPager.setCurrentItem(0);
                    bangImageView(linearLayout, 0);
                } else {
                    viewPager.setCurrentItem(nowIndex + 1);
                    bangImageView(linearLayout, nowIndex + 1);
                }
                handler.postDelayed(viewpagerRunnable, TIME);
            }
        };
        handler.postDelayed(viewpagerRunnable, TIME);
    }


    CustomProgressDialog customProgressDialog;

    public void createDialog() {
        if(customProgressDialog==null){
            customProgressDialog = new CustomProgressDialog(activity);
        }
        customProgressDialog.setCancelable(true);
        customProgressDialog.show();
    }


    public void removeDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.setCancelable(true);
            if(customProgressDialog.isShowing()){
                customProgressDialog.dismiss();
            }

        }
    }


    /**
     * 绑定指示器
     *
     * @param linearLayout 指示器容器
     * @param p            位置
     */
    public void bangImageView(LinearLayout linearLayout, int p) {
        int count = linearLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView image = (ImageView) linearLayout.getChildAt(i);
            if (i == p) {
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
        }

    }

    /**
     * 保留两位小数
     *
     * @param s
     * @return
     */
    public String floatToString(float s) {
        DecimalFormat myFormat = new DecimalFormat("0.00");
        String strFloat = myFormat.format(s);
        return strFloat;
    }

    public void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    public void showShortToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
    }

    public void showSnackBarShort(View view, String str) {
        final Snackbar snackbar = Snackbar.make(view, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
        snackbar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
    }


    public void showSnackBarLong(View view, String str) {
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show();
    }


    public int getW() {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }

    public int getH() {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素）
        return height;
    }
}
