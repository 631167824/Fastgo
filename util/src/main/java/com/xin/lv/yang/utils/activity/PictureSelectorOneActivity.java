package com.xin.lv.yang.utils.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xin.lv.yang.utils.R;
import com.xin.lv.yang.utils.info.PicItem;
import com.xin.lv.yang.utils.permission.PermissionsManager;
import com.xin.lv.yang.utils.photo.image.AlbumBitmapCacheHelper;
import com.xin.lv.yang.utils.utils.ImageUtil;
import com.xin.lv.yang.utils.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 选择一张图片
 */

public class PictureSelectorOneActivity extends BaseActivity {
    GridView gridView;
    RelativeLayout relativeLayout;
    private String mCurrentCatalog = "";
    private int perWidth;
    ListView mCatalogListView;
    TextView textView;
    LinearLayout linearLayout;
    ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_one_layout);
        gridView = (GridView) findViewById(R.id.gridlist);
        relativeLayout = (RelativeLayout) findViewById(R.id.catalog_window);
        mCatalogListView = (ListView) findViewById(R.id.catalog_listview);
        textView = (TextView) findViewById(R.id.type_text);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        imageButton = (ImageButton) findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState != null) {
            beifenList = savedInstanceState.getParcelableArrayList("ItemList");
        }


        AlbumBitmapCacheHelper.init(this);

        updatePictureItems();

        gridView.setAdapter(new PicAdapter());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicItem picItem = mAllItemList.get(position);
                String url = picItem.uri;
                Intent data = new Intent();
                data.putExtra(Intent.EXTRA_RETURN_RESULT, url);
                setResult(RESULT_OK, data);
                finish();

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relativeLayout.getVisibility() == View.VISIBLE) {
                    relativeLayout.setVisibility(View.GONE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        mCatalogListView.setAdapter(new CatalogAdapter());

        mCatalogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catalog;
                if (position == 0) {
                    catalog = "all";
                } else {
                    catalog = mCatalogList.get(position - 1);
                }

                if (catalog.equals(mCurrentCatalog)) {
                    relativeLayout.setVisibility(View.GONE);
                } else {
                    mCurrentCatalog = catalog;
                    TextView textView = (TextView) view.findViewById(R.id.name);
                    PictureSelectorOneActivity.this.textView.setText(textView.getText().toString());
                    relativeLayout.setVisibility(View.GONE);
                    // 筛选数据
                    if (catalog.equals("all")) {
                        mAllItemList = beifenList;
                        // 选择所有，无key值信息
                        mCurrentCatalog = "";
                    } else {
                        mAllItemList = mItemMap.get(mCurrentCatalog);
                    }

                    if (mAllItemList != null) {
                        ((PicAdapter) gridView.getAdapter()).notifyDataSetChanged();
                    }

                    ((CatalogAdapter) mCatalogListView.getAdapter()).notifyDataSetChanged();

                }
            }
        });

        perWidth = (((WindowManager) ((WindowManager) getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth() - Utils.dip2px(PictureSelectorOneActivity.this, 4.0F)) / 3;


    }


    protected void onSaveInstanceState(Bundle outState) {
        if (beifenList != null && beifenList.size() > 0) {
            outState.putParcelableArrayList("ItemList", beifenList);
        }

        super.onSaveInstanceState(outState);
    }


    private List<PicItem> mAllItemList;
    ArrayList<PicItem> beifenList;
    private Map<String, List<PicItem>> mItemMap;
    private List<String> mCatalogList;

    /**
     * 获取图片数据
     */
    private void updatePictureItems() {
        showDialog();
        String[] projection = new String[]{"_data", "date_added"};
        String orderBy = "datetaken DESC";
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, (String) null, (String[]) null, orderBy);
        mAllItemList = new ArrayList<>();
        mItemMap = new HashMap<>();
        mCatalogList = new ArrayList<>();
        beifenList = new ArrayList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    PicItem item = new PicItem();
                    item.uri = cursor.getString(0);
                    if (item.uri != null) {
                        // 添加所有图片
                        this.mAllItemList.add(item);

                        int last = item.uri.lastIndexOf("/");

                        String catalog;
                        if (last == 0) {
                            catalog = "/";
                        } else {
                            int itemList = item.uri.lastIndexOf("/", last - 1);
                            catalog = item.uri.substring(itemList + 1, last);
                        }

                        if (this.mItemMap.containsKey(catalog)) {
                            (mItemMap.get(catalog)).add(item);
                        } else {
                            ArrayList<PicItem> itemList1 = new ArrayList<>();
                            itemList1.add(item);

                            mItemMap.put(catalog, itemList1);
                            mCatalogList.add(catalog);
                        }

                    }
                } while (cursor.moveToNext());
            }

            cursor.close();
        }

        removeDialog();
        beifenList.addAll(mAllItemList);

    }


    /**
     * 图片显示的adapter
     */
    private class PicAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //  默认为 0
            int sum = 0;
            String key;
            if (mCurrentCatalog.isEmpty()) {
                for (Iterator i$ = PictureSelectorOneActivity.this.mItemMap.keySet().iterator(); i$.hasNext(); sum += (PictureSelectorOneActivity.this.mItemMap.get(key)).size()) {
                    key = (String) i$.next();
                }
            } else {
                sum += ((List) PictureSelectorOneActivity.this.mItemMap.get(PictureSelectorOneActivity.this.mCurrentCatalog)).size();
            }

            return sum;
        }

        @Override
        public Object getItem(int position) {
            return mAllItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PicItem item;
            if (PictureSelectorOneActivity.this.mCurrentCatalog.isEmpty()) {
                item = PictureSelectorOneActivity.this.mAllItemList.get(position);
            } else {
                item = getItemAt(mCurrentCatalog, position);
            }

            VH holder;

            if (convertView != null && convertView.getTag() != null) {
                holder = (VH) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(PictureSelectorOneActivity.this).inflate(R.layout.image_layout, parent, false);
                holder = new VH();
                holder.imageView = convertView.findViewById(R.id.image);
                int w = getW() / 3;
                holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(w, w));
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imageView.setPadding(2, 2, 2, 2);

                convertView.setTag(holder);
            }

            String path = null;
            if (holder.imageView.getTag() != null) {
                path = (String) holder.imageView.getTag();
                AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(path);
            }

            if (item != null) {
                path = item.uri;
                AlbumBitmapCacheHelper.getInstance().addPathToShowlist(path);
                holder.imageView.setTag(path);
            }

            Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(path, PictureSelectorOneActivity.this.perWidth, PictureSelectorOneActivity.this.perWidth, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                public void onLoadImageCallBack(Bitmap bitmap, String path1, Object... objects) {
                    if (bitmap != null) {
                        ImageView v = PictureSelectorOneActivity.this.gridView.findViewWithTag(path1);
                        if (v != null) {
                            v.setImageBitmap(bitmap);
                        }

                    }
                }

            }, new Object[]{Integer.valueOf(position)});

            if (bitmap != null) {
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rc_grid_image_default));
            }

            return convertView;

        }

        class VH {
            ImageView imageView;
        }


        private PicItem getItemAt(String catalog, int index) {
            if (!mItemMap.containsKey(catalog)) {
                return null;
            } else {
                int sum = 0;
                for (Iterator i$ = ((List) mItemMap.get(catalog)).iterator(); i$.hasNext(); ++sum) {
                    PicItem item = (PicItem) i$.next();
                    if (sum == index) {
                        return item;
                    }
                }

                return null;
            }
        }
    }


    /**
     * 筛选的adapter
     */
    private class CatalogAdapter extends BaseAdapter {
        private LayoutInflater mInflater = getLayoutInflater();

        public CatalogAdapter() {
        }

        public int getCount() {
            return mItemMap.size() + 1;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (convertView == null) {
                view = this.mInflater.inflate(R.layout.rc_picsel_catalog_listview, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) view.findViewById(R.id.image);
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.number = (TextView) view.findViewById(R.id.number);
                holder.selected = (ImageView) view.findViewById(R.id.selected);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String path;
            if (holder.image.getTag() != null) {
                path = (String) holder.image.getTag();
                AlbumBitmapCacheHelper.getInstance().removePathFromShowlist(path);
            }

            int num = 0;
            boolean showSelected = false;
            String name;
            Bitmap bitmap;
            BitmapDrawable bd;

            if (position == 0) {
                if (mItemMap.size() == 0) {
                    holder.image.setImageResource(R.drawable.rc_picsel_empty_pic);
                } else {
                    path = ((PicItem) ((List) mItemMap.get(mCatalogList.get(0))).get(0)).uri;

                    if (!path.equals("")) {
                        AlbumBitmapCacheHelper.getInstance().addPathToShowlist(path);
                        holder.image.setTag(path);
                        bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(path, perWidth, perWidth, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                            public void onLoadImageCallBack(Bitmap bitmap, String path1, Object... objects) {
                                if (bitmap != null) {
                                    BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                                    View v = gridView.findViewWithTag(path1);
                                    if (v != null) {
                                        v.setBackgroundDrawable(bd);
                                        notifyDataSetChanged();
                                    }

                                }

                            }
                        }, new Object[]{Integer.valueOf(position)});

                        if (bitmap != null) {
                            bd = new BitmapDrawable(getResources(), bitmap);
                            holder.image.setBackgroundDrawable(bd);
                        } else {
                            holder.image.setBackgroundResource(R.drawable.rc_grid_image_default);
                        }
                    }
                }

                name = getResources().getString(R.string.rc_picsel_catalog_allpic);
                holder.number.setVisibility(View.GONE);
                showSelected = mCurrentCatalog.isEmpty();

            } else {
                path = ((PicItem) ((List) mItemMap.get(mCatalogList.get(position - 1))).get(0)).uri;
                name = mCatalogList.get(position - 1);
                num = (mItemMap.get(mCatalogList.get(position - 1))).size();
                holder.number.setVisibility(View.VISIBLE);
                showSelected = name.equals(mCurrentCatalog);
                AlbumBitmapCacheHelper.getInstance().addPathToShowlist(path);
                holder.image.setTag(path);
                bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap(path, perWidth, perWidth, new AlbumBitmapCacheHelper.ILoadImageCallback() {
                    public void onLoadImageCallBack(Bitmap bitmap, String path1, Object... objects) {
                        if (bitmap != null) {
                            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                            View v = gridView.findViewWithTag(path1);
                            if (v != null) {
                                v.setBackgroundDrawable(bd);
                                notifyDataSetChanged();
                            }

                        }
                    }
                }, new Object[]{Integer.valueOf(position)});
                if (bitmap != null) {
                    bd = new BitmapDrawable(getResources(), bitmap);
                    holder.image.setBackgroundDrawable(bd);
                } else {
                    holder.image.setBackgroundResource(R.drawable.rc_grid_image_default);
                }
            }

            holder.name.setText(name);
            holder.number.setText(String.format(getResources().getString(R.string.rc_picsel_catalog_number), new Object[]{Integer.valueOf(num)}));
            holder.selected.setVisibility(showSelected ? View.VISIBLE : View.INVISIBLE);
            return view;
        }

        private class ViewHolder {
            ImageView image;
            TextView name;
            TextView number;
            ImageView selected;

            private ViewHolder() {
            }
        }
    }

}
