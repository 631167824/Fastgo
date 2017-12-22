package com.wt.fastgo_user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.info.WuInfo;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的包裹适配器
 */

public class MyPackAdapter extends OnBindRecyclerAdapter<WuInfo> {
    private OnEditOrQuitListener listener;

    public MyPackAdapter(Context context, List<WuInfo> list) {
        super(context, list);
    }

    @Override
    protected void showViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.tvPackEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onEdit(holder.getAdapterPosition());
                }

            }
        });
        vh.tvPackExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onQuit(holder.getAdapterPosition());
                }
            }
        });
        vh.ivErwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onErWei(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    protected void updateViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

    }

    @Override
    protected RecyclerView.ViewHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_pack_item, parent, false);

        return new ViewHolder(view);
    }

    public void setListener(OnEditOrQuitListener listener) {
        this.listener = listener;
    }

    public interface OnEditOrQuitListener {
        void onEdit(int p);

        void onQuit(int p);

        void onErWei(int p);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv2)
        TextView tv2;
        @BindView(R.id.tv_people)
        TextView tvPeople;
        @BindView(R.id.tv3)
        TextView tv3;
        @BindView(R.id.tv_aptamil)
        TextView tvAptamil;
        @BindView(R.id.pack_state)
        TextView packState;
        @BindView(R.id.pack_time)
        TextView packTime;
        @BindView(R.id.iv_erwei)
        ImageView ivErwei;
        @BindView(R.id.tv_pack_edit)
        TextView tvPackEdit;
        @BindView(R.id.tv_pack_exit)
        TextView tvPackExit;
        @BindView(R.id.pack_title)
        TextView packTitle;
        @BindView(R.id.image_state)
        ImageView imageState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
