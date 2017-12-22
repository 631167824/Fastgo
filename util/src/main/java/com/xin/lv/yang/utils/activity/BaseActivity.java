package com.xin.lv.yang.utils.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xin.lv.yang.utils.R;
import com.xin.lv.yang.utils.fragment.BaseFragment;
import com.xin.lv.yang.utils.net.BaseHandler;
import com.xin.lv.yang.utils.net.OnNetError;
import com.xin.lv.yang.utils.permission.PermissionsManager;
import com.xin.lv.yang.utils.utils.ImageUtil;
import com.xin.lv.yang.utils.utils.TextUtils;
import com.xin.lv.yang.utils.view.CustomProgressDialog;
import com.xin.lv.yang.utils.view.CustomToast;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseActivity extends AppCompatActivity {

    public Gson gson;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseHandler.setOnNetError(new OnNetError() {
            @Override
            public void onError() {
                showToastShort("网络连接超时");
                removeDialog();
            }
        });

        gson = new Gson();

    }


    /**
     * 设置透明度
     *
     * @param f 透明度
     */
    public void setAlpha(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);

    }


    CustomProgressDialog dialog;

    public void showDialog() {
        if (dialog == null) {
            dialog = new CustomProgressDialog(this);
        }
        dialog.setCancelable(true);
        dialog.show();

    }

    public void removeDialog() {
        if (dialog != null) {
            dialog.setCancelable(true);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }


    ActionBar actionBar;

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ActivityCompat.getColor(activity, colorResId));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideKeyboard(InputMethodManager input) {
        View view = getCurrentFocus();
        if (view != null) {
            if (input != null) {
                input.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 初始化actionBar
     *
     * @param resId 布局文件id
     */
    public void initActionBar(int resId) {
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(resId);
            actionBar.show();
        }
    }


    public void showText(TextView textView, int start, String str, int color) {
        SpannableString sp = new SpannableString(str);
        TextUtils.getInstance().setColor(this, sp, start, str.length() - 1, color, TextUtils.COLOR);
        textView.setText(sp, TextView.BufferType.SPANNABLE);
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

    public void showToastShort(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    public void showToastLong(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }


    public void showCustomToast(String str, int resId) {
        CustomToast.showToast(this, Gravity.TOP, resId, str);
    }


    public int getW() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }


    public int getH() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素）
        return height;
    }


    /**
     * 获取系统版本号
     *
     * @return 系统版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = String.valueOf(info.versionCode);
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    protected static String str = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";


    /**
     * 判断是否是电话号码
     *
     * @param mobiles 电话号码字符串
     * @return 是否为电话号码  true 是   false  不是
     */
    public static boolean isMobileNum(String mobiles) {
        if (mobiles.length() == 11) {
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(mobiles);
            return m.matches();
        } else {
            return false;
        }
    }


    /**
     * TabLayout 设置下画线的长度
     *
     * @param tabs     TabLayout对象
     * @param leftDip  左边的距离
     * @param rightDip 右边的距离
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }

    List<View> viewPagerList;

    public void initViewPager(Handler handler, List<String> list, ViewPager viewPager, final LinearLayout linearLayout) {
        viewPagerList = new ArrayList<>();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
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
                ImageView v = new ImageView(this);
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
                View v = LayoutInflater.from(this).inflate(R.layout.image_layout, null);
                ImageView imageView = (ImageView) v.findViewById(R.id.image);
                ImageUtil.getInstance().loadImageNoTransformation(this, imageView, 0, t);
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
     * 用于记录当前显示的Fragment
     */
    public int currentIndex = 0;

    List<BaseFragment> fragmentList;

    protected void initData(FragmentManager fragmentManager, List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        int len = fragmentList.size();
        if (len == 1) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commitAllowingStateLoss();
            }
        } else if (len == 2) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commitAllowingStateLoss();
            }
            Fragment fragment1 = fragmentManager.findFragmentByTag("1");
            if (fragment1 != null) {
                fragmentManager.beginTransaction().remove(fragment1).commitAllowingStateLoss();
            }

        } else if (len == 3) {
            Fragment fragment0 = fragmentManager.findFragmentByTag("0");
            if (fragment0 != null) {
                fragmentManager.beginTransaction().remove(fragment0).commitAllowingStateLoss();
            }
            Fragment fragment1 = fragmentManager.findFragmentByTag("1");
            if (fragment1 != null) {
                fragmentManager.beginTransaction().remove(fragment1).commitAllowingStateLoss();
            }
            Fragment fragment2 = fragmentManager.findFragmentByTag("2");
            if (fragment2 != null) {
                fragmentManager.beginTransaction().remove(fragment2).commitAllowingStateLoss();
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
        fragmentManager.beginTransaction().add(resId, fragmentList.get(id), String.valueOf(id)).commitAllowingStateLoss();
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
            fragmentTransaction.commitAllowingStateLoss();

            currentIndex = index;
        }
    }


    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || packageName.equals("")) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            if (info != null) {
                return true;
            } else {
                return false;
            }

        } catch (PackageManager.NameNotFoundException e) {
            return false;
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


}
