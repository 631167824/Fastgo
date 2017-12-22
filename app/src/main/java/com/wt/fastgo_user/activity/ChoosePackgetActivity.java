package com.wt.fastgo_user.activity;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.ChooseAdapter;
import com.wt.fastgo_user.info.OrderInfo;
import com.wt.fastgo_user.net.Contact;
import com.xin.lv.yang.utils.adapter.AdapterCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择包裹界面
 */
public class ChoosePackgetActivity extends ProActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.choose_recycler)
    RecyclerView chooseRecycler;
    @BindView(R.id.all_check_box)
    AppCompatCheckBox allCheckBox;
    @BindView(R.id.ti_button)
    Button tiButton;

    List<OrderInfo> list;
    List<OrderInfo> newList;

    boolean isAllCheck = false;


    /**
     * 揽收点id
     */
    int orderId;
    ChooseAdapter adapter;

    @Override
    public int getContextId() {
        return R.layout.choose_pack_layout;
    }

    @Override
    public void initAllMembersView(Bundle bundle) {
        list = new ArrayList<>();
        newList = new ArrayList<>();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        orderId = getIntent().getIntExtra(Contact.ID, 0);

        chooseRecycler.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

        allCheckBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isAllCheck = true;
                return false;
            }
        });

        allCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isAllCheck) {
                    if (isChecked) {
                        for (OrderInfo t : newList) {
                            t.setCheck(true);
                        }
                    } else {
                        for (OrderInfo t : newList) {
                            t.setCheck(false);
                        }
                    }

                    changeCheck();
                }
            }
        });

    }

    @Override
    public void handler(Message msg) {

    }

    private void changeCheck() {
        adapter.update(newList, new AdapterCallBack<OrderInfo>(list, newList) {
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("bundler", newList.get(newItemPosition));
                return bundle;
            }
        });
        isAllCheck = false;
    }

    public void initAdapter() {
        for (int i = 0; i < 5; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(i);
            orderInfo.setCheck(false);
            list.add(orderInfo);
        }

        newList.addAll(list);
        adapter = new ChooseAdapter(this, list);
        chooseRecycler.setAdapter(adapter);
        adapter.setChange(new ChooseAdapter.OnCheckChange() {
            @Override
            public void onChange(boolean isCheck) {
                if (isAllCheck) {
                    if (isCheck) {
                        allCheckBox.setChecked(true);
                    } else {
                        allCheckBox.setChecked(false);
                    }
                }

            }
        });

    }
}
