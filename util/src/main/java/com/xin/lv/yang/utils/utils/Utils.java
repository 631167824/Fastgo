package com.xin.lv.yang.utils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.xin.lv.yang.utils.activity.ImageGridActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * 操作类
 */

public class Utils {
    private static final String TAG = "ScreenUtil";
    private static double RATIO = 0.85D;
    public static int screenWidth;
    public static int screenHeight;
    public static int screenMin;
    public static int screenMax;
    public static float density;
    public static float scaleDensity;
    public static float xdpi;
    public static float ydpi;
    public static int densityDpi;
    public static int dialogWidth;
    public static int statusbarheight;
    public static int navbarheight;

    public Utils() {
    }

    /**
     * dp转px
     *
     * @param dipValue dp数据
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        density = dm.density;
        return (int) (dipValue * density + 0.5F);
    }

    /**
     * px转dp
     *
     * @param pxValue px像素
     * @return
     */

    public static int px2dip(Context context, float pxValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        density = dm.density;
        return (int) (pxValue / density + 0.5F);
    }


    /**
     * sp 转换成 px显示
     *
     * @param context 上下文
     * @param spValue 文本 sp值
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static int getDialogWidth() {
        dialogWidth = (int) ((double) screenMin * RATIO);
        return dialogWidth;
    }


    public static void init(Context context) {
        if (null != context) {
            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            screenMin = screenWidth > screenHeight ? screenHeight : screenWidth;
            density = dm.density;
            scaleDensity = dm.scaledDensity;
            xdpi = dm.xdpi;
            ydpi = dm.ydpi;
            densityDpi = dm.densityDpi;
        }
    }

    public static void GetInfo(Context context) {
        if (null != context) {
            DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
            screenWidth = dm.widthPixels;
            screenHeight = dm.heightPixels;
            screenMin = screenWidth > screenHeight ? screenHeight : screenWidth;
            screenMax = screenWidth < screenHeight ? screenHeight : screenWidth;
            density = dm.density;
            scaleDensity = dm.scaledDensity;
            xdpi = dm.xdpi;
            ydpi = dm.ydpi;
            densityDpi = dm.densityDpi;
            statusbarheight = getStatusBarHeight(context);
            navbarheight = getNavBarHeight(context);
            Log.d("ScreenUtil", "screenWidth=" + screenWidth + " screenHeight=" + screenHeight + " density=" + density);
        }
    }

    public static int getStatusBarHeight(Context context) {
        Class c = null;
        Object obj = null;
        Field field = null;
        boolean x = false;
        int sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x1 = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x1);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return sbar;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? resources.getDimensionPixelSize(resourceId) : 0;
    }

    public static Drawable getDrawable(Context context, String resource) {
        InputStream is = null;

        try {
            Resources e = context.getResources();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = 240;
            options.inScreenDensity = e.getDisplayMetrics().densityDpi;
            options.inTargetDensity = e.getDisplayMetrics().densityDpi;
            is = context.getAssets().open(resource);
            Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(), options);
            BitmapDrawable var6 = new BitmapDrawable(context.getResources(), bitmap);
            return var6;
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return null;
    }

    public static View getHorizontalThinLine(Context context) {
        View line = new View(context);
        line.setBackgroundColor(context.getResources().getColor(android.R.color.black));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(-1, 1);
        line.setLayoutParams(llp);
        return line;
    }

    public static View getVerticalThinLine(Context context) {
        View line = new View(context);
        line.setBackgroundColor(context.getResources().getColor(android.R.color.black));
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(1, -1);
        line.setLayoutParams(llp);
        return line;
    }

    public static Bitmap getResizedBitmap(Context context, Uri uri, int widthLimit, int heightLimit) throws IOException {
        String path = null;
        Bitmap result = null;
        if (uri.getScheme().equals("file")) {
            path = uri.getPath();
        } else {
            if (!uri.getScheme().equals("content")) {
                return null;
            }

            Cursor exifInterface = context.getContentResolver().query(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
            exifInterface.moveToFirst();
            path = exifInterface.getString(0);
            exifInterface.close();
        }

        ExifInterface exifInterface1 = new ExifInterface(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int orientation = exifInterface1.getAttributeInt("Orientation", 0);
        int width;
        if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
            width = widthLimit;
            widthLimit = heightLimit;
            heightLimit = width;
        }

        width = options.outWidth;
        int height = options.outHeight;
        int sampleW = 1;

        int sampleH;
        for (sampleH = 1; width / 2 > widthLimit; sampleW <<= 1) {
            width /= 2;
        }

        while (height / 2 > heightLimit) {
            height /= 2;
            sampleH <<= 1;
        }

        boolean sampleSize = true;
        options = new BitmapFactory.Options();
        int sampleSize1;
        if (widthLimit != 2147483647 && heightLimit != 2147483647) {
            sampleSize1 = Math.max(sampleW, sampleH);
        } else {
            sampleSize1 = Math.max(sampleW, sampleH);
        }

        options.inSampleSize = sampleSize1;

        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError var22) {
            var22.printStackTrace();
            options.inSampleSize <<= 1;
            bitmap = BitmapFactory.decodeFile(path, options);
        }

        Matrix matrix = new Matrix();
        if (bitmap == null) {
            return bitmap;
        } else {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            if (orientation == 6 || orientation == 8 || orientation == 5 || orientation == 7) {
                int xS = w;
                w = h;
                h = xS;
            }

            switch (orientation) {
                case 2:
                    matrix.preScale(-1.0F, 1.0F);
                    break;
                case 3:
                    matrix.setRotate(180.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 4:
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 5:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 6:
                    matrix.setRotate(90.0F, (float) w / 2.0F, (float) h / 2.0F);
                    break;
                case 7:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
                    matrix.preScale(1.0F, -1.0F);
                    break;
                case 8:
                    matrix.setRotate(270.0F, (float) w / 2.0F, (float) h / 2.0F);
            }

            float xS1 = (float) widthLimit / (float) bitmap.getWidth();
            float yS = (float) heightLimit / (float) bitmap.getHeight();
            matrix.postScale(Math.min(xS1, yS), Math.min(xS1, yS));

            try {
                result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                return result;
            } catch (OutOfMemoryError var21) {
                var21.printStackTrace();
                Log.e("ResourceCompressHandler", "OOMHeight:" + bitmap.getHeight() + "Width:" + bitmap.getHeight() + "matrix:" + xS1 + " " + yS);
                return null;
            }
        }
    }


    public static String md5(Object object) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(toByteArray(object));
        } catch (NoSuchAlgorithmException var7) {
            throw new RuntimeException("Huh, MD5 should be supported?", var7);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        byte[] arr$ = hash;
        int len$ = hash.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            byte b = arr$[i$];
            if ((b & 255) < 16) {
                hex.append("0");
            }

            hex.append(Integer.toHexString(b & 255));
        }

        return hex.toString();
    }


    private static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream ex = new ObjectOutputStream(bos);
            ex.writeObject(obj);
            ex.flush();
            bytes = bos.toByteArray();
            ex.close();
            bos.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        return bytes;
    }

    private static Uri mTakePictureUri;

    /**
     * 获取uri数据
     *
     * @return uri
     */
    public static Uri getmTakePictureUri() {
        return mTakePictureUri;
    }


    /**
     * 调用相机拍照
     */
    protected Intent requestCamera(Context context) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!path.exists()) {
            path.mkdirs();
        }
        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(path, name);
        Utils.mTakePictureUri = Uri.fromFile(file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        List resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Uri uri = null;

        try {
            uri = FileProvider.getUriForFile(context, context.getPackageName() + ".FileProvider", file);
        } catch (Exception var10) {
            var10.printStackTrace();
            throw new RuntimeException("uri 出现错误");
        }

        Iterator i$ = resInfoList.iterator();

        while (i$.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) i$.next();
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.putExtra("output", uri);
        return intent;
    }


    public void paiXu(List<Integer> list) {
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                if (integer.intValue() > t1.intValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

    }


    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    @SuppressLint("NewApi")
    public static void enableStrictMode() {
        if (hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            if (Utils.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder
                        .setClassInstanceLimit(ImageGridActivity.class, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }


    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;

    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static List<Camera.Size> getResolutionList(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        return parameters.getSupportedPreviewSizes();
    }

    public static String getVideoPath() {
        if (isSdcardExist()) {
            String path = Environment.getExternalStorageDirectory().getPath();
            return path + "/video";
        }
        return "";
    }


    public static String getImagePath() {
        if (isSdcardExist()) {
            String path = Environment.getExternalStorageDirectory().getPath();
            return path + "/image";
        }
        return "";
    }

    public static class ResolutionComparator implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.height != rhs.height)
                return lhs.height - rhs.height;
            else
                return lhs.width - rhs.width;
        }

    }

    public static String toTime(int var0) {
        var0 /= 1000;
        int var1 = var0 / 60;
        boolean var2 = false;
        if (var1 >= 60) {
            int var4 = var1 / 60;
            var1 %= 60;
        }

        int var3 = var0 % 60;
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(var1), Integer.valueOf(var3)});
    }


    /**
     * 获取视频第一帧 作为封面
     *
     * @param filePath 视频路径
     * @return 封面图片
     */

    public static Bitmap createVideoThumbnail(String filePath) {
        Class<?> clazz = null;
        Object instance = null;
        try {
            try {
                clazz = Class.forName("android.media.MediaMetadataRetriever");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                try {
                    instance = clazz.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            /// The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }


    /**
     * 获取视频缩略图
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    public static String longToTime(long l) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return format.format(new Date(l * 1000));
    }


    public static String longToTime(long l,String type) {
        SimpleDateFormat format = new SimpleDateFormat(type, Locale.US);
        return format.format(new Date(l * 1000));
    }

}
