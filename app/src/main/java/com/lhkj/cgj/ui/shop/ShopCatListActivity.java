package com.lhkj.cgj.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.ShopLock;
import com.lhkj.cgj.network.response.ShopResponse;
import com.lhkj.cgj.spirit.GridViewForScrollView;
import com.lhkj.cgj.ui.main.ShopListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/********************************************************************
  @version: 1.0.0
  @description: 点击礼品分类之后的跳转界面用来显示分类列表
  @author: user
  @time: 2018/3/28 14:59
  @变更历史:
********************************************************************/

public class ShopCatListActivity extends AppCompatActivity {

    private GridViewForScrollView mGridViewForScrollView;
    private ImageView mimgv_back;
    private TextView mtv_title;


    public ShopListAdapter shopListAdapter;
    private List<ShopLock.ShopItem> shopListData;

    private String cat_id = "";
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cat_list);
        initViews();
        init();
    }

    public void initViews() {
        mGridViewForScrollView = (GridViewForScrollView) findViewById(R.id.shop_list);
        mimgv_back= (ImageView) findViewById(R.id.ims_left);
        mtv_title= (TextView) findViewById(R.id.app_title);
        mimgv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopCatListActivity.this.finish();
            }
        });


    }

    public void init() {
        Intent intent = this.getIntent();
        if (intent != null) {
            cat_id = intent.getStringExtra("cat_id");
            name=intent.getStringExtra("name");
            mtv_title.setText(name);
        }
        shopListData = new ArrayList<>();
        shopListAdapter = new ShopListAdapter(this, shopListData, R.layout.shop_item, BR.shopItem);
        mGridViewForScrollView.setAdapter(shopListAdapter);
        defShow();
        mGridViewForScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectCommodity(position);
            }
        });

    }

    public void selectCommodity(int id) {
        RunTime.setData(RunTime.SHOPID, shopListData.get(id).id);
        RunTime.setData(RunTime.SHOP_TYPE, 0);
        startActivity(new Intent(this, ShopDetailsActivity.class));
    }

    private void defShow() {
        shopListData.clear();
        HashMap h = new HashMap();
        h.put("cat_id", cat_id);
        RunTime.operation.tryPostRefreshF(ShopResponse.class, RunTime.operation.getShop().LIFE_LIST, h, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200 && User.isLogin()) {
                    ShopResponse shopResponse = (ShopResponse) data;
                    for (ShopResponse.Info info : shopResponse.info) {
                        shopListData.add(new ShopLock.ShopItem().change(info));
                    }
                }
                shopListAdapter.notifyDataSetChanged();
            }
        });

    }

}
