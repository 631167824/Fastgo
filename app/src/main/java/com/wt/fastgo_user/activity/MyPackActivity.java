package com.wt.fastgo_user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.MyPackAdapter;
import com.wt.fastgo_user.info.WuInfo;
import com.wt.fastgo_user.net.Contact;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的包裹activity
 */
public class MyPackActivity extends ProActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.image_search)
    ImageView imageSearch;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<WuInfo> list;
    MyPackAdapter adapter;

    @Override
    public int getContextId() {
        return R.layout.my_pack_layout;
    }

    @Override
    public void initAllMembersView(final Bundle bundle) {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setIndicator(tabLayout, 10, 10);
        initAdapter();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initAdapter() {
        for (int i = 0; i < 5; i++) {
            list.add(new WuInfo());
        }

        adapter = new MyPackAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MyPackAdapter.OnEditOrQuitListener() {
            @Override
            public void onEdit(int p) {
                // 点击编辑
                Intent intent = new Intent(MyPackActivity.this, EditActivity.class);
                intent.putExtra(Contact.NAME, list.get(p));
                startActivity(intent);


            }

            @Override
            public void onQuit(int p) {
                // 点击退件
            }

            @Override
            public void onErWei(int p) {
                // 点击图片二维码
            }
        });

        adapter.setOnItemClickListener(new OnBindRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyPackActivity.this, MyPackDetailsActivity.class);
                intent.putExtra(Contact.CODE, 1);
                intent.putExtra(Contact.ID, list.get(position).getId());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

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
