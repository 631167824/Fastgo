package com.xin.lv.yang.utils.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.xin.lv.yang.utils.R;
import com.xin.lv.yang.utils.utils.ImageUtil;
import com.xin.lv.yang.utils.view.CustomProgressDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础 fragment
 */
public class BaseFragment extends Fragment {

    List<View> viewPagerList;
    Activity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
    }


    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    int position;

    /**
     * 初始化 viewpager 轮播图
     *
     * @param list         轮播图集合
     * @param viewPager    ViewPager 对象
     * @param linearLayout 指示器对象
     */
    public void initViewPager(Context context, final Handler handler, final List<String> list,
                              final ViewPager viewPager, final LinearLayout linearLayout) {
        viewPagerList = new ArrayList<>();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BaseFragment.this.position = position;
                bangImageView(linearLayout, position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (list != null) {
            int len = list.size();
            for (int i = 0; i < len; i++) {
                ImageView v = new ImageView(context);
                if (i == 0) {
                    v.setEnabled(true);
                } else {
                    v.setEnabled(false);
                }
                v.setPadding(0, 0, 10, 0);
                v.setImageResource(R.drawable.view_selecter);
                linearLayout.addView(v);
            }

            for (final String t : list) {
                View v = LayoutInflater.from(context).inflate(R.layout.image_layout, null);
                final ImageView imageView = v.findViewById(R.id.image);
                ImageUtil.getInstance().loadImageByBitmap(context, 0, t, new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int bw = resource.getWidth();
                        int bh = resource.getHeight();
                        int w = getW(activity);
                        int h = w * bh / bw;

                        // 设置宽高
                        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(w, h));

                        imageView.setImageBitmap(resource);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("wwww", "点击的数据====" + t);

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

    public static final int TIME = 10 * 1000;

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


    public void createDialog(Context context) {
        if (customProgressDialog == null) {
            customProgressDialog = new CustomProgressDialog(context);
        }
        customProgressDialog.setCancelable(true);
        customProgressDialog.show();
    }


    public void removeDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.setCancelable(true);
            if (customProgressDialog.isShowing()) {
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


    public int getW(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }


    public int getH(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素）
        return height;
    }


    /**
     * 用于记录当前显示的Fragment
     */
    public int currentIndex = 0;

    public List<BaseFragment> fragmentList;

    /**
     * 初始化fragment
     * @param fragmentManager
     * @param fragmentList
     */
    protected void initData(FragmentManager fragmentManager, List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        int len = fragmentList.size();
        if (len == 1) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commit();
            }
        } else if (len == 2) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commit();
            }
            Fragment fragment1 = fragmentManager.findFragmentByTag("1");
            if (fragment1 != null) {
                fragmentManager.beginTransaction().remove(fragment1).commit();
            }

        } else if (len == 3) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commit();
            }
            Fragment fragment1 = fragmentManager.findFragmentByTag("1");
            if (fragment1 != null) {
                fragmentManager.beginTransaction().remove(fragment1).commit();
            }
            Fragment fragment2 = fragmentManager.findFragmentByTag("2");
            if (fragment2 != null) {
                fragmentManager.beginTransaction().remove(fragment2).commit();
            }
        }

    }


    /**
     * 初始显示的fragment
     *
     * @param fragmentManager fragment管理器
     * @param resId           view的id
     * @param id              在集合中的位置
     */
    protected void showFirstFragment(FragmentManager fragmentManager, int resId, int id) {
        fragmentManager.beginTransaction().add(resId, fragmentList.get(id), String.valueOf(id)).commit();
        currentIndex = id;
    }


    /**
     * 切换fragment
     *
     * @param fragmentManager fragment 管理器
     * @param index           显示的位置
     * @param resId           显示的视图id
     */
    public void toFragment(FragmentManager fragmentManager, int resId, int index) {
        if (index != currentIndex) {
            BaseFragment baseFragment = fragmentList.get(index);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //先判断该Fragment是否已经添加到Activity了，如果没有则添加，如果有，则显示
            if (baseFragment.isAdded()) {
                //则显示
                fragmentTransaction.show(baseFragment);
            } else {
                //添加
                fragmentTransaction.add(resId, baseFragment, String.valueOf(index));
            }

            //隐藏之前显示的Fragment
            fragmentTransaction.hide(fragmentList.get(currentIndex));
            //提交事务
            fragmentTransaction.commit();

            currentIndex = index;
        }
    }
}
