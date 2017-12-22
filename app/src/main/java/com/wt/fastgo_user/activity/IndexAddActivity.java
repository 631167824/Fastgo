package com.wt.fastgo_user.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wt.fastgo_user.R;
import com.wt.fastgo_user.adapter.WuPinAdapter;
import com.wt.fastgo_user.info.UserInfo;
import com.wt.fastgo_user.info.WuInfo;
import com.xin.lv.yang.utils.activity.PictureSelectorActivity;
import com.xin.lv.yang.utils.adapter.OnBindRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 本地直邮添加信息
 */
public class IndexAddActivity extends ProActivity {
    private static final int CODE = 121;
    @BindView(R.id.index_back)
    ImageView indexBack;
    @BindView(R.id.index_pack)
    TextView indexPack;
    @BindView(R.id.index_add_people)
    Button indexAddPeople;
    @BindView(R.id.index_beizhu)
    EditText indexBeizhu;
    @BindView(R.id.wu_title)
    TextView wuTitle;
    @BindView(R.id.index_add_wu)
    Button indexAddWu;
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
    @BindView(R.id.et_fa_name)
    EditText etFaName;
    @BindView(R.id.et_fa_phone)
    EditText etFaPhone;
    @BindView(R.id.et_fa_address)
    EditText etFaAddress;
    @BindView(R.id.tv_close_2)
    TextView tvClose2;
    @BindView(R.id.button_send)
    Button buttonSend;
    List<WuInfo> list;
    @BindView(R.id.wuliu_linear_layout)
    LinearLayout wuliuLinearLayout;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.id_card_linear)
    LinearLayout idCardLinear;
    @BindView(R.id.choose_name)
    AppCompatSpinner spinner;
    @BindView(R.id.user_linear)
    LinearLayout userLinearLayout;
    List<String> spinnerList;
    @BindView(R.id.text_user_name)
    TextView tvName;
    @BindView(R.id.text_address)
    TextView tvAddress;
    @BindView(R.id.text_id_card)
    TextView tvIdCard;
    WuPinAdapter adapter;
    Dialog addUserDialog;
    Dialog addDialog;
    private List<String> typeList;

    @Override
    public int getContextId() {
        return R.layout.index_add_layout;
    }

    @Override
    public void initAllMembersView(Bundle bundle) {
        contentRecyclerView.setNestedScrollingEnabled(false);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        spinnerList = new ArrayList<>();
        initAdapter();
        indexBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initSpinner(List<String> arr) {
        spinnerList.add("");
        spinnerList.addAll(arr);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spinnerList));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("")) {

                    getUserFromUrl(str);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param str 用户名称
     */
    private void getUserFromUrl(String str) {
        userLinearLayout.setVisibility(View.VISIBLE);

        showUser(new UserInfo());


    }

    private void showUser(UserInfo userInfo) {
        tvName.setText(userInfo.getName());
        tvAddress.setText(userInfo.getAddress());
        tvIdCard.setText(userInfo.getIdCard());

    }

    private void initAdapter() {
        for (int i = 0; i < 5; i++) {
            list.add(new WuInfo());
        }
        if (list.size() != 0) {
            wuliuLinearLayout.setVisibility(View.GONE);
        }

        adapter = new WuPinAdapter(this, list);
        contentRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnBindRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
                showDeleteDialog();
            }
        });

    }

    /**
     * 删除商品信息
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否删除?").setPositiveButton("删除?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
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

    @OnClick({R.id.index_back, R.id.index_pack, R.id.button_up_idcard, R.id.index_add_people,
            R.id.button_send, R.id.index_add_wu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_back:
                finish();
                break;
            case R.id.index_pack:
                startActivity(new Intent(this, MyPackActivity.class));
                break;
            case R.id.button_up_idcard:
                startActivityForResult(new Intent(this, PictureSelectorActivity.class), CODE);
                break;
            case R.id.index_add_people:
                showAddUser();
                break;
            case R.id.button_send:
                break;
            case R.id.index_add_wu:
                showAddDialog();
                break;
        }
    }

    /**
     * 弹出框添加用户信息
     */
    private void showAddUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View userView = getLayoutInflater().inflate(R.layout.user_add_layout, null);
        initUserView(userView);
        builder.setView(userView);
        addUserDialog = builder.create();
        addUserDialog.show();
    }

    /**
     * 初始化添加用户信息的view
     *
     * @param userView view
     */
    private void initUserView(View userView) {
        ImageView imageQuit = userView.findViewById(R.id.add_user_quit);
        final EditText etName = userView.findViewById(R.id.et_add_user_name);
        final EditText etAddress = userView.findViewById(R.id.et_add_user_address);
        final EditText etIdCard = userView.findViewById(R.id.et_add_user_id_card);
        imageQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserDialog.dismiss();
            }
        });

        Button button = userView.findViewById(R.id.save_user_info);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etName.getText().toString();
                String address = etAddress.getText().toString();
                String idCard = etIdCard.getText().toString();
                if (userName.equals("") || address.equals("") || idCard.equals("")) {
                    showToastShort("请填写信息");
                    return;
                }

                saveUser(userName, address, idCard);
            }
        });

    }

    /**
     * 保存用户信息
     *
     * @param userName
     * @param address
     * @param idCard
     */
    private void saveUser(String userName, String address, String idCard) {
        addUserDialog.dismiss();
        userLinearLayout.setVisibility(View.VISIBLE);

        UserInfo userInfo = new UserInfo();
        userInfo.setName(userName);
        userInfo.setAddress(address);
        userInfo.setIdCard(idCard);
        showUser(userInfo);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == RESULT_OK) {
            List<Uri> list = data.getParcelableArrayListExtra(Intent.EXTRA_RETURN_RESULT);
            int len = list.size();
            if (len > 0) {
                idCardLinear.setVisibility(View.VISIBLE);
                switch (len) {
                    case 1:
                        image1.setImageBitmap(BitmapFactory.decodeFile(list.get(0).getEncodedPath()));
                        break;
                    case 2:
                        image1.setImageBitmap(BitmapFactory.decodeFile(list.get(0).getEncodedPath()));
                        image2.setImageBitmap(BitmapFactory.decodeFile(list.get(1).getEncodedPath()));
                        break;
                }
            }
        }

    }

    /**
     * 添加商品信息
     */
    public void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_shop_dialog, null);
        initView(view);
        builder.setView(view);
        addDialog = builder.create();
        addDialog.show();
    }

    private void initView(View view) {
        typeList = new ArrayList<>();
        ImageView imgQuit = view.findViewById(R.id.add_quit);
        final EditText etShopName = view.findViewById(R.id.et_add_name);
        final EditText etPenPai = view.findViewById(R.id.et_add_pp);
        final EditText etGuiGe = view.findViewById(R.id.et_add_guige);
        final EditText etNum = view.findViewById(R.id.et_add_num);
        final EditText etPrice = view.findViewById(R.id.et_add_price);
        final TextView tvAllPrice = view.findViewById(R.id.tv_add_all);

        final AppCompatSpinner spinner = view.findViewById(R.id.spinner);
        Button btSave = view.findViewById(R.id.bt_save);
        imgQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.dismiss();

            }
        });

        final String spinnerStr = "";
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etShopName.getText().toString();
                String peiPai = etPenPai.getText().toString();
                String guige = etGuiGe.getText().toString();
                String num = etNum.getText().toString();
                String prize = etPrice.getText().toString();
                String allPrice = tvAllPrice.getText().toString();
                if (spinnerStr.equals("")) {
                    showToastShort("请选择规格");
                    return;
                }
                if (name.equals("") || peiPai.equals("") || guige.equals("") || num.equals("") || prize.equals("")) {
                    showToastShort("请填写信息");
                    return;
                }
                saveShop(spinnerStr, name, peiPai, guige, num, prize, allPrice);

            }
        });

        spinner.setAdapter(new ArrayAdapter<String>(IndexAddActivity.this, android.R.layout.simple_list_item_1, typeList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 添加商品信息
     *
     * @param spinnerStr
     * @param name
     * @param peiPai
     * @param guige
     * @param num
     * @param prize
     * @param allPrice
     */
    private void saveShop(String spinnerStr, String name, String peiPai, String guige, String num, String prize, String allPrice) {
    }

}
