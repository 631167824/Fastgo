package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.activity.ChoosePackgetActivity;
import com.wt.fastgo_user.info.OrderInfo;
import com.wt.fastgo_user.net.Contact;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WaitAdapter extends OnBindRecyclerAdapter<OrderInfo> {


    private List<String> imageList;
    private OnLoadData onLoadData;

    public WaitAdapter(Context context, List<OrderInfo> list) {
        super(context, list);
        imageList = new ArrayList<>();
        imageList.add("http://img02.sogoucdn.com/app/a/100520093/10e8b9550acde0b8-459f14f6a089053d-e879bbcb128a7b240716f9531571fc2c.jpg");
        imageList.add("http://img02.sogoucdn.com/app/a/100520093/ca86e620b9e623ff-e7ae36db714776c0-b0158348187351632005e109f7faff29.jpg");
        imageList.add("http://img04.sogoucdn.com/app/a/100520093/12400ee0679b6e1e-af640e269b3a3e1c-af0336e27774103cd1e0dfa5d634abb6.jpg");
    }

    @Override
    protected void showViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;
        final OrderInfo orderInfo = list.get(position);

        vh.toSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChoosePackgetActivity.class);
                intent.putExtra(Contact.ID, orderInfo.getId());
                context.startActivity(intent);
            }
        });

        vh.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLoadData != null) {
                    onLoadData.onLoad(vh.getAdapterPosition());
                }

            }
        });

        showOne(vh, orderInfo);
        showTwo(vh, orderInfo);

    }

    private void showOne(ViewHolder vh, OrderInfo orderInfo) {

    }

    @Override
    protected void updateViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        Bundle bundle = (Bundle) payloads.get(0);
        Set<String> keySet = bundle.keySet();
        for (String t : keySet) {
            switch (t) {
                case Contact.NAME:
                    OrderInfo orderInfo = bundle.getParcelable(Contact.NAME);
                    showTwo((ViewHolder) holder, orderInfo);
                    break;
            }
        }

    }

    /**
     * 显示详情信息
     *
     * @param holder
     * @param orderInfo
     */
    private void showTwo(ViewHolder holder, OrderInfo orderInfo) {
        if (orderInfo.isCheck()) {
            LinearLayoutManager manger = new LinearLayoutManager(context);
            manger.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.imageRecycler.setLayoutManager(manger);
            holder.imageRecycler.setAdapter(new ImageAdapter(context, imageList));

            holder.twoLinear.setVisibility(View.VISIBLE);

        } else {
            holder.twoLinear.setVisibility(View.GONE);
        }

    }

    @Override
    protected RecyclerView.ViewHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.wait_store_item, parent, false);
        return new ViewHolder(view);
    }

    public OnLoadData getOnLoadData() {
        return onLoadData;
    }

    public void setOnLoadData(OnLoadData onLoadData) {
        this.onLoadData = onLoadData;
    }

    public interface OnLoadData {
        void onLoad(int p);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wait_name)
        TextView waitName;
        @BindView(R.id.wait_km)
        TextView waitKm;
        @BindView(R.id.to_send)
        Button toSend;
        @BindView(R.id.two_linear)
        LinearLayout twoLinear;
        @BindView(R.id.image_recycler)
        RecyclerView imageRecycler;
        @BindView(R.id.one_linear_layout)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
