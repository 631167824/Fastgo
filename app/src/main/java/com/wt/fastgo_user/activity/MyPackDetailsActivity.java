package com.wt.fastgo_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.ImageAdapter;
import com.wt.fastgo_user.net.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的包裹详情界面
 */

public class MyPackDetailsActivity extends ProActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_yun_number)
    TextView tvYunNumber;
    @BindView(R.id.zhuan_yun_number)
    TextView zhuanYunNumber;
    @BindView(R.id.tv_wu_pin)
    TextView tvWuPin;
    @BindView(R.id.tv_in_ku)
    TextView tvInKu;
    @BindView(R.id.tv_yun_price)
    TextView tvYunPrice;
    @BindView(R.id.tv_sui_price)
    TextView tvSuiPrice;
    @BindView(R.id.tv_baojia)
    TextView tvBaojia;
    @BindView(R.id.tv_baojia_price)
    TextView tvBaojiaPrice;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.tv_in_time)
    TextView tvInTime;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_u_name)
    TextView tvUName;
    @BindView(R.id.tv_u_phone)
    TextView tvUPhone;
    @BindView(R.id.tv_u_address)
    TextView tvUAddress;
    @BindView(R.id.tv_u_youbian)
    TextView tvUYoubian;
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
    @BindView(R.id.tv_all_weight)
    TextView tvAllWeight;
    @BindView(R.id.fa_recycler)
    RecyclerView faRecycler;
    @BindView(R.id.fa_beizhu)
    TextView faBeizhu;
    @BindView(R.id.fa_linear_layout)
    LinearLayout faLinearLayout;
    int code;
    int packId;

    @Override
    public int getContextId() {
        return R.layout.my_pack_details_layout;
    }

    @Override
    public void initAllMembersView(Bundle bundle) {
        Intent intent = getIntent();
        code = intent.getIntExtra(Contact.CODE, 0);
        packId = intent.getIntExtra(Contact.ID, 0);
        if (code == 1) {
            faLinearLayout.setVisibility(View.GONE);
        } else {
            faLinearLayout.setVisibility(View.VISIBLE);
        }

    }


    public void initAdapter(List<String> list) {
        LinearLayoutManager manger = new LinearLayoutManager(this);
        manger.setOrientation(LinearLayoutManager.HORIZONTAL);
        faRecycler.setLayoutManager(manger);
        faRecycler.setAdapter(new ImageAdapter(this, list));
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
