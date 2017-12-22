package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;
import com.xin.lv.yang.utils.utils.ImageUtil;

import java.util.List;


/**
 * 图片显示
 */
public class ImageAdapter extends OnBindRecyclerAdapter<String> {
    public ImageAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected void showViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.itemView;
        ImageUtil.getInstance().loadImageNoTransformation(context, imageView, 0, list.get(position));

    }

    @Override
    protected void updateViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    protected RecyclerView.ViewHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        int w = getW() / 4;
        imageView.setLayoutParams(new ViewGroup.LayoutParams(w, w));
        return new VH(imageView);

    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
