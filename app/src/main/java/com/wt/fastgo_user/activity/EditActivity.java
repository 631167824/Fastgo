package com.wt.fastgo_user.activity;


import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 编辑包裹信息
 */
public class EditActivity extends ProActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.edit_user_name)
    TextView textUserName;
    @BindView(R.id.edit_address)
    TextView textAddress;
    @BindView(R.id.edit_id_card)
    TextView textIdCard;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.id_card_linear)
    LinearLayout idCardLinear;
    @BindView(R.id.user_linear)
    LinearLayout userLinear;
    @BindView(R.id.wu_title)
    TextView wuTitle;
    @BindView(R.id.index_add_wu)
    Button indexAddWu;
    @BindView(R.id.content_all_price)
    TextView contentAllPrice;
    @BindView(R.id.wuliu_linear_layout)
    LinearLayout wuliuLinearLayout;
    @BindView(R.id.content_recycler_view)
    RecyclerView contentRecyclerView;
    @BindView(R.id.index_weight)
    TextView indexWeight;
    @BindView(R.id.index_check_box)
    CheckBox indexCheckBox;
    @BindView(R.id.buy_money)
    TextView buyMoney;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_fa_name)
    TextView tvFaName;
    @BindView(R.id.tv_fa_phone)
    TextView tvFaPhone;
    @BindView(R.id.tv_fa_address)
    TextView tvFaAddress;

    @Override
    public int getContextId() {
        return R.layout.edit_pack_layout;
    }

    @Override
    public void initAllMembersView(Bundle bundle) {
        initTool(toolBar);
        title.setText("编辑");

    }

    @Override
    public void handler(Message msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
