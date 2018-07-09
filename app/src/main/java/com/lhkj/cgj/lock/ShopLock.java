package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.FragmentShopBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.NowLllReponse;
import com.lhkj.cgj.network.response.ShopCatResponse;
import com.lhkj.cgj.network.response.ShopResponse;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.main.ShopCatListAdapter;
import com.lhkj.cgj.ui.main.ShopHotAdapter;
import com.lhkj.cgj.ui.main.ShopListAdapter;
import com.lhkj.cgj.ui.main.ShopTypeAdapter;
import com.lhkj.cgj.ui.shop.ShopCatListActivity;
import com.lhkj.cgj.ui.shop.ShopDetailsActivity;
import com.lhkj.cgj.ui.shop.ShopExchangeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 2018-3-28去掉了轮播图以及下方的展示GridView
 * 积分商品
 */

public class ShopLock {
    public ShopListAdapter shopListAdapter;
    public ShopData shopData;
    private Context context;
    private ArrayList<ShopItem> shopListData;
    private ArrayList networkImages;
    private FragmentShopBinding shopBinding;
    private ArrayList<String> catList;
    private ArrayList<ShopCatItem> shopTypeNames;
    private ShopTypeAdapter shopTypeAdapter;
    private Handler handler;
    public static TextView selectView;
    public static int seeWhat = 0;
//    public static boolean refresh = true;

    private ArrayList<ShopHotItem> hots = new ArrayList<>();
    private ShopHotAdapter shopHotAdapter;

    private ShopCatListAdapter mShopCatListAdapter;
    private List<ShopCatResponse.Info> mInfos;

    public ShopLock(Context context, final FragmentShopBinding shopBinding) {
        this.context = context;
        this.shopBinding = shopBinding;
//        this.selectView = shopBinding.list0;
        shopData = new ShopData();
        shopTypeNames = new ArrayList<>();
        shopListData = new ArrayList<>();
        catList = new ArrayList<>();
        shopListAdapter = new ShopListAdapter(context, shopListData, R.layout.shop_item, BR.shopItem);
//        shopBinding.shopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selectCommodity(position);
//            }
//        });
        init();
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
//                shopBinding.scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }


    private void init() {
        mInfos = new ArrayList<>();
        mShopCatListAdapter = new ShopCatListAdapter(context, mInfos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopBinding.shopCatList.setLayoutManager(gridLayoutManager);
        shopBinding.shopCatList.setAdapter(mShopCatListAdapter);
        mShopCatListAdapter.setOnItemClickListener(new ShopCatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                seeWhat = position;
                ShopCatResponse.Info info = mInfos.get(position);
                Intent intent = new Intent(context, ShopCatListActivity.class);
                intent.putExtra("cat_id", info.id);
                intent.putExtra("name",info.name);
                context.startActivity(intent);
            }
        });
//        getBoo();
        if (!User.isLogin()) {
            shopBinding.shopHotList.setVisibility(View.INVISIBLE);
            return;
        } else {
            shopBinding.shopHotList.setVisibility(View.VISIBLE);
        }
//        if (refresh) {
            getCatList();
//            refresh = false;
//        }
        getLll();
        getHotList();
    }

    //轮播图
//    private void getBoo() {
//        networkImages = new ArrayList();
//        HashMap hashMap = new HashMap();
//        hashMap.put("user_id", User.getUser().userId);
//        //boo
//        RunTime.operation.tryPostRefresh(BannerResponse.class, RunTime.operation.getShop().SHOP_BANNER, hashMap, new Operation.Listener() {
//            @Override
//            public void tryReturn(int id, Object data) {
//                if (((BannerResponse) data).info != null) {
//                    for (BannerResponse.Info info : ((BannerResponse) data).info) {
//                        networkImages.add(info.img_url);
//                    }
//                }
//                shopBinding.shopIms.startTurning(2500);
//                shopBinding.shopIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//                    @Override
//                    public NetworkImageHolderView createHolder() {
//                        return new NetworkImageHolderView();
//                    }
//                }, networkImages);
////                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
//            }
//        });
//    }

    //分类列表
    private void getCatList() {
        catList.clear();
        HashMap hashMap1 = new HashMap();
        hashMap1.put("bind_oil", User.getUser().userOilId);
        RunTime.operation.tryPostRefreshF(ShopCatResponse.class, RunTime.operation.getShop().CAT_LIST, hashMap1, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    ShopCatResponse catResponse = (ShopCatResponse) data;
                    for (ShopCatResponse.Info info : catResponse.info) {
                        catList.add(info.name);
                        shopTypeNames.add(new ShopCatItem(info.id, info.name));
                    }
                    mInfos.clear();
                    mInfos.addAll(catResponse.info);
                    mShopCatListAdapter.notifyDataSetChanged();
                    /*不使用原布局逻辑*/
//                    if (catList.size() > 0) {
//                        initLayout();
//                    }
                }
            }
        });
    }

    //热门列表
    private void getHotList() {
        HashMap hashMap2 = new HashMap();
        hashMap2.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefreshF(ShopResponse.class, RunTime.operation.getShop().HOT_LIST, hashMap2, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ShopResponse shopResponse = (ShopResponse) data;
                initHotList(shopResponse);
            }
        });
    }

    private void initHotList(ShopResponse shopResponse) {
        if (shopResponse.info == null) {
            return;
        }

        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        shopBinding.shopHotList.setLayoutManager(ms);
        shopHotAdapter = new ShopHotAdapter(context, hots);
        shopBinding.shopHotList.setAdapter(shopHotAdapter);
        hots.clear();

        for (ShopResponse.Info info : shopResponse.info) {
            hots.add(new ShopHotItem(
                    info.goods_id,
                    info.goods_name,
                    info.shop_type,
                    info.shop_price,
                    info.exchange_integral,
                    info.store_count.length() > 4 ? (info.store_count.substring(0, 4) + "...") : info.store_count,
                    info.original_img));
        }
        shopHotAdapter.notifyDataSetChanged();
    }

    private void initLayout() {
        shopTypeAdapter = new ShopTypeAdapter(context, catList);
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        shopBinding.shopCatList.setLayoutManager(ms);
        shopBinding.shopCatList.setAdapter(shopTypeAdapter);
        shopTypeAdapter.setItemClickListener(new ShopTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectView((TextView) view);
                defShow(position);
                seeWhat = position;
            }
        });
        defShow(seeWhat);
    }

    private void defShow(int position) {
        shopListData.clear();
        HashMap h = new HashMap();
        h.put("cat_id", shopTypeNames.get(position).catId);
        RunTime.operation.tryPostRefreshF(ShopResponse.class, RunTime.operation.getShop().LIFE_LIST, h, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200 && User.isLogin()) {
                    ShopResponse shopResponse = (ShopResponse) data;
                    for (ShopResponse.Info info : shopResponse.info) {
                        shopListData.add(new ShopItem().change(info));
                    }
                }
                refresh();
                if (selectView == null) {
                    selectView(shopTypeAdapter.getOneText());
                }
            }
        });

    }

    private void getLll() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NowLllReponse.class, RunTime.operation.getHome().NOWLLL, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                shopData.myLll = " " + ((NowLllReponse) data).info;
                User.getUser().nowLll = ((NowLllReponse) data).info;
                shopData.notifyChange();
            }
        });
    }

    public void toHis() {
        if (User.isLogin()) {
            ((MainActivity) context).startActivity(ShopExchangeActivity.class);
        }
    }

    private void selectView(TextView textView) {
        if (textView == null) {
            return;
        }
        if (selectView == null) {
            selectView = textView;
        }
        selectView.setTextColor(context.getResources().getColor(R.color.gray));
        selectView.setBackgroundColor(context.getResources().getColor(R.color.white));
        textView.setTextColor(context.getResources().getColor(R.color.main_color));
        textView.setBackground(context.getResources().getDrawable(R.drawable.select_red));
        selectView = textView;
    }

    private void refresh() {
        shopListAdapter.notifyDataSetChanged();
    }

    public void selectCommodity(int id) {
        RunTime.setData(RunTime.SHOPID, shopListData.get(id).id);
        RunTime.setData(RunTime.SHOP_TYPE, 0);
        ((MainActivity) context).startActivity(ShopDetailsActivity.class);
    }

    public void flushShop() {
        init();
    }

    public static class ShopItem {
        public String url;
        public String name;
        public String integral;
        public String id;
        public String storeCount;

        public ShopItem change(ShopResponse.Info info) {
            url = info.original_img;
            name = info.goods_name;
            id = info.goods_id;
            if (info.shop_type.equals("1")) {
                integral = "￥" + info.shop_price;
            } else if (info.shop_type.equals("2")) {
                integral = info.exchange_integral + "积分";
            } else if (info.shop_type.equals("3")) {
                integral = info.exchange_integral + "积分";
            }
            storeCount = "库存：" + (info.store_count.length() > 4 ? info.store_count.substring(0, 4) + "..." : info.store_count);
            return this;
        }
    }

    public class ShopData extends BaseObservable {
        public String myLll = "0";
    }

    public class ShopHotItem {

        public ShopHotItem(String id, String name, String type,
                           String price, String lll,
                           String stock, String url) {
            this.id = id;
            this.name = name;

            if ("1".equals(type)) {
                this.pay = "￥" + price;
            } else if ("2".equals(type)) {
                this.pay = "积分" + lll;
            } else {
                this.pay = "￥" + price;
            }

            this.stock = "库存：" + stock;
            this.url = url;
        }

        public String id;
        public String name;
        public String pay;
        public String stock;
        public String url;

    }

    public class ShopCatItem {
        public ShopCatItem(String catId, String catName) {
            this.catId = catId;
            this.catName = catName;
        }

        public String catId;
        public String catName;

    }
}
