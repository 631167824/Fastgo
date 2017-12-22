package com.xin.lv.yang.utils.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 图片加载相关操作
 */

public class ImageUtil {

    private static ImageUtil imageUtil;
    private static final int TIME = 100;

    public static synchronized ImageUtil getInstance() {
        if (imageUtil == null) {
            imageUtil = new ImageUtil();
        }
        return imageUtil;

    }


    /**
     * 加载网络图片，使用glide框架
     *
     * @param context   上下文对象 context
     * @param imageView ImageView
     * @param uri       图片的地址
     */
    public void loadImageByTransformation(Context context, ImageView imageView, String uri, int resId, Transformation<Bitmap> transformation) {
        if (context != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .transform(transformation)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);  //缓存策略

            RequestManager manager = Glide.with(context);
            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);
        }
    }

    public void loadImageByTransformation(Activity activity, ImageView imageView, String uri, int resId, Transformation<Bitmap> transformation) {
        if (!activity.isFinishing()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .transform(transformation)
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            RequestManager manager = Glide.with(activity);
            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);
        }
    }


    public void loadImageByTransformation(Fragment fragment, ImageView imageView, String uri, int resId, Transformation<Bitmap> transformation) {
        if (fragment != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .transform(transformation)
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略
            RequestManager manager = Glide.with(fragment);
            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);
        }
    }


    public void loadImageNoTransformationByOverride(Context context, ImageView imageView, int w, int h, int resId, String uri) {
        if (context != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            RequestManager manager = Glide.with(context);

            RequestBuilder<Drawable> builder = manager.load(uri).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(imageView);

        }
    }

    public void loadImageNoTransformationByOverride(Activity activity, ImageView imageView, int w, int h, int resId, String uri) {
        if (!activity.isFinishing()) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略
            RequestManager manager = Glide.with(activity);

            RequestBuilder<Drawable> builder = manager.load(uri).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(imageView);

        }
    }

    public void loadImageNoTransformationByOverride(Fragment fragment, ImageView imageView, int w, int h, int resId, String uri) {
        if (fragment != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            RequestManager manager = Glide.with(fragment);

            RequestBuilder<Drawable> builder = manager.load(uri).transition(new DrawableTransitionOptions().crossFade(TIME)).apply(options);
            builder.preload(w, h);
            builder.into(imageView);

        }
    }


    public void loadImageByBitmap(Context context,int resId, String uri,SimpleTarget<Bitmap> target){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(resId)    //预加载图片
                .error(resId)          //加载失败显示图片
                .priority(Priority.HIGH)   //优先级
                .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略
        RequestBuilder<Bitmap> build=Glide.with(context).asBitmap().load(uri).apply(options);
        build.into(target);

    }


    public void loadImageNoTransformation(Context context, ImageView imageView, int resId, String uri) {
        if (context != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            RequestManager manager = Glide.with(context);
            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);

        }
    }

    public void loadImageNoTransformation(Activity activity, ImageView imageView, int resId, String uri) {
        if (!activity.isFinishing()) {
            RequestManager manager = Glide.with(activity);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略

            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);
        }
    }

    public void loadImageNoTransformation(Fragment fragment, ImageView imageView, int resId, String uri) {
        if (fragment != null) {
            RequestManager manager = Glide.with(fragment);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE);  //缓存策略

            manager.load(uri).apply(options).transition(new DrawableTransitionOptions().crossFade(TIME)).into(imageView);
        }
    }


    /**
     * 加载图片 实现宽度满屏，高度自适应
     *
     * @param context   上下文对象
     * @param imageView ImageView
     * @param resId     加载失败显示图片
     * @param uri       加载图片地址
     */
    public void loadImageNoTransformationWithW(Context context, final ImageView imageView, int resId, String uri) {
        if (context != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(resId)    //预加载图片
                    .error(resId)          //加载失败显示图片
                    .priority(Priority.HIGH)   //优先级
                    .diskCacheStrategy(DiskCacheStrategy.NONE); //缓存策略
            RequestManager manager = Glide.with(context);

            manager.load(uri)
                    .transition(new DrawableTransitionOptions().crossFade(TIME))
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (imageView == null) {
                                return false;
                            }
                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }

                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                            float scale = (float) vw / (float) resource.getIntrinsicWidth();
                            int vh = Math.round(resource.getIntrinsicHeight() * scale);
                            params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                            imageView.setLayoutParams(params);

                            return false;

                        }
                    }).into(imageView);
        }
    }


    /**
     * 剪裁bitmap 图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (w <= 0 || h <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }


    /**
     * Drawable into Bitmap
     */

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * Get round Bitmap
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    /**
     * Get reflection Bitmap
     */

    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }


    // Create adaptive drawable by drawableId
    public static Drawable createAdaptiveDrawableByDrawableId(Activity activity, int drawableId) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        Bitmap bitmap = ((BitmapDrawable) activity.getResources().getDrawable(drawableId)).getBitmap();
        bitmap = ImageUtil.zoomBitmap(bitmap, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    // Create adaptive drawable by bitmap
    public static Drawable createAdaptiveDrawableByBitmap(Activity activity, Bitmap bitmap) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        bitmap = ImageUtil.zoomBitmap(bitmap, width, height);
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }


    /**
     * 图片质量压缩
     *
     * @param image
     * @return
     */
    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 图片比例大小压缩
     *
     * @param srcPath 本地图片路径
     * @return
     */
    public Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        // 此时返回bm为空
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;  //这里设置高度为800f
        float ww = 480f;  //这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;
        //be=1表示不缩放
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }


    /**
     * 根据bitmap 对象压缩
     *
     * @param image bitmap对象
     * @return
     */
    private Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) { //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset(); //重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos); //这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;   //降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap); //压缩好比例大小后再进行质量压缩
    }


    /**
     * Returns the bitmap position inside an imageView.
     *
     * @param imageView source ImageView
     * @return 0: left, 1: top, 2: width, 3: height
     */
    public static int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH) / 2;
        int left = (int) (imgViewW - actW) / 2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }


    public static boolean createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath) {
        try {
            if (content == null || "".equals(content)) {
                return false;
            }

            // 配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
//            hints.put(EncodeHintType.MARGIN, 2); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            return bitmap != null && bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }


}
