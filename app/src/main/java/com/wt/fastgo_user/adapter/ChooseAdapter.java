package com.wt.fastgo_user.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.info.OrderInfo;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseAdapter extends OnBindRecyclerAdapter<OrderInfo> {

    int num = 0;
    private OnCheckChange change;

    public ChooseAdapter(Context context, List<OrderInfo> list) {
        super(context, list);
    }

    @Override
    protected void showViewHolder(RecyclerView.ViewHolder holder, int position) {
        showData((ViewHolder) holder, list.get(position));

    }

    @Override
    protected void updateViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        Bundle bundle = (Bundle) payloads.get(0);
        Set<String> key = bundle.keySet();
        for (String t : key) {
            switch (t) {
                case "bundler":
                    OrderInfo orderInfo = bundle.getParcelable("bundler");
                    showData((ViewHolder) holder, orderInfo);
                    break;
            }
        }

    }

    public void showData(ViewHolder vh, final OrderInfo orderInfo) {
        vh.checkbox.setChecked(orderInfo.isCheck());
        vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    num++;
                } else {
                    num--;
                }

                if (num == list.size()) {
                    if (change != null) {
                        change.onChange(true);
                    }

                } else {
                    if (change != null) {
                        change.onChange(false);
                    }
                }

                changeCheck(orderInfo, isChecked);

            }
        });

    }

    private void changeCheck(OrderInfo orderInfo, boolean isCheck) {
        for (OrderInfo t : list) {
            if (t.getId() == orderInfo.getId()) {
                t.setCheck(isCheck);
            }
        }
    }

    @Override
    protected RecyclerView.ViewHolder createRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.choose_pack_item, parent, false);
        return new ViewHolder(view);
    }

    public OnCheckChange getChange() {
        return change;
    }

    public void setChange(OnCheckChange change) {
        this.change = change;
    }

    public interface OnCheckChange {
        void onChange(boolean isCheck);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox)
        AppCompatCheckBox checkbox;
        @BindView(R.id.choose_title)
        TextView chooseTitle;
        @BindView(R.id.choose_name)
        TextView chooseName;
        @BindView(R.id.choose_content)
        TextView chooseContent;
        @BindView(R.id.choose_time)
        TextView chooseTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
