package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.info.WuInfo;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/21.
 */

public class WuPinAdapter extends OnBindRecyclerAdapter<WuInfo> {
    public WuPinAdapter(Context context, List<WuInfo> list) {
        super(context, list);
    }

    @Override
    protected void showViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        if (position != 0) {
            vh.shopAllPrize.setVisibility(View.GONE);
            vh.shopGuigeTitle.setVisibility(View.GONE);
            vh.shopNameTitle.setVisibility(View.GONE);
            vh.shopNumTitle.setVisibility(View.GONE);
            vh.shopPinpaiTitle.setVisibility(View.GONE);
            vh.shopPriceTitle.setVisibility(View.GONE);
            vh.shopTypeTitle.setVisibility(View.GONE);
        }


    }

    @Override
    protected void updateViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    protected RecyclerView.ViewHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.index_wu_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_name_title)
        TextView shopNameTitle;
        @BindView(R.id.content_name)
        TextView contentName;
        @BindView(R.id.shop_type_title)
        TextView shopTypeTitle;
        @BindView(R.id.content_type)
        TextView contentType;
        @BindView(R.id.shop_pinpai_title)
        TextView shopPinpaiTitle;
        @BindView(R.id.content_pinpai)
        TextView contentPinpai;
        @BindView(R.id.shop_guige_title)
        TextView shopGuigeTitle;
        @BindView(R.id.content_guige)
        TextView contentGuige;
        @BindView(R.id.shop_num_title)
        TextView shopNumTitle;
        @BindView(R.id.content_num)
        TextView contentNum;
        @BindView(R.id.shop_price_title)
        TextView shopPriceTitle;
        @BindView(R.id.content_price)
        TextView contentPrice;
        @BindView(R.id.shop_all_prize)
        TextView shopAllPrize;
        @BindView(R.id.content_all_price)
        TextView contentAllPrice;
        @BindView(R.id.wuliu_linear_layout)
        LinearLayout wuliuLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
