package com.lhkj.cgj.ui.station;

import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityBindBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.BindLock;
import com.lhkj.cgj.network.response.BindListResponse;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.utils.PoiOverlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
public class BindActivity extends BaseActivity implements SensorEventListener, OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
    private ActivityBindBinding bindBinding;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private final int accuracyCircleFillColor = 0xAAFFFF88;
    private final int accuracyCircleStrokeColor = 0xAA00FF00;
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    //搜索
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest;
    private int searchType = 0;  // 搜索的类型，在显示时区分
    LatLng center = new LatLng(39.92235, 116.380338);
    LatLng southwest = new LatLng(39.92235, 116.380338);
    LatLng northeast = new LatLng(39.947246, 116.414977);
    LatLngBounds searchbound = new LatLngBounds.Builder().include(southwest).include(northeast).build();
    private int radius = 100;
    private int loadIndex = 0;
    // UI相关
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private BindLock bindLock;
    //绘制
    BitmapDescriptor bd;
    private ArrayList<Marker> masList;
    private List<BindListResponse.InfoBean> sreachList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplication());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        bindBinding = DataBindingUtil.setContentView(this, R.layout.activity_bind);
        bindLock = new BindLock(this, bindBinding);
        bindBinding.setBindLock(bindLock);
        bindBinding.include4.setAppBarLock(new AppBarLock(this, R.string.bind, R.mipmap.icon_back, 0, true, false));
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = LocationMode.NORMAL;
        bd = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_oil);
        // 地图初始化
        mMapView = bindBinding.llmap;
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_user);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Marker m : masList) {
                    if (m == marker) {
                        String oil = m.getTitle();
                        final String oilId = oil.substring(0, oil.indexOf("*"));
                        final String oilName = oil.substring(oil.indexOf("*") + 1, oil.length());
                        HashMap hashMap = new HashMap();
                        hashMap.put("user_id", User.getUser().userId);
                        hashMap.put("oil_id", oilId);
                        RunTime.operation.tryPostRefreshF(SuccessResponse.class, RunTime.operation.getHome().BIND, hashMap, new Operation.Listener() {
                            @Override
                            public void tryReturn(int id, Object data) {
                                if (id == 200) {
                                    User.getUser().userOilId = oilId;
                                    User.getUser().userOilName = oilName;
                                    Toast.makeText(BindActivity.this, "绑定加油站成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(BindActivity.this, "绑定失败,三个月内不可更改绑定加油站", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });
                    }
                }
                return false;
            }
        });
        masList = new ArrayList();
        getBind();
    }

    private void getBind() {
        RunTime.operation.tryPostRefresh(BindListResponse.class, RunTime.operation.getHome().BIND_LIST, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                sreachList = ((BindListResponse) data).getInfoX();
                for (BindListResponse.InfoBean info : ((BindListResponse) data).getInfoX()) {
                    if (info.getAdmin_id() == null || info.getLat() == null || info.getLng() == null) {

                    } else {
                        initBd(info.getAdmin_id() + "*" + info.getName(), Double.parseDouble(info.getLat()), Double.parseDouble(info.getLng()));
                        initText(info.getName(), Double.parseDouble(info.getLat()), Double.parseDouble(info.getLng()));
                    }
                }
            }
        });
    }

    private void initBd(String idAndName, double w, double h) {
        //添加标记
        LatLng llA = new LatLng(w, h);
        MarkerOptions ooA = new MarkerOptions().position(llA).icon(bd)
                .zIndex(15).draggable(true);
        Marker mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        mMarkerA.setTitle(idAndName);
        masList.add(mMarkerA);

    }

    private void initText(String text, double w, double h) {
        //添加文字
        LatLng llText = new LatLng(w - 0.00001, h - 0.0000001);
        OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
                .fontSize(36).fontColor(0xFFFF00FF).text(text).rotate(0)
                .position(llText);
        mBaiduMap.addOverlay(ooText);
    }

    public void sreachOil(View view) {
        if (bindLock.bindData.selectBind != null && !bindLock.bindData.selectBind.equals("")) {
            for (BindListResponse.InfoBean info : sreachList) {
                if (info.getName() != null && info.getName().equals(bindLock.bindData.selectBind)) {
                    if (info.getAdmin_id() == null || info.getLat() == null || info.getLng() == null) {

                    } else {
                        locData = new MyLocationData.Builder()
//                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
//                                .direction(mCurrentDirection)
                                .latitude(Double.parseDouble(info.getLat()))
                                .longitude(Double.parseDouble(info.getLng())).build();
                        mBaiduMap.setMyLocationData(locData);
//                        initBd(info.getAdmin_id(), Double.parseDouble(info.getLat()), Double.parseDouble(info.getLng()));
                    }
                } else {
                    Toast.makeText(this, "油品惠暂时不支持该加油站", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "请填写加油站名称", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 响应城市内搜索按钮点击事件
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
//        searchType = 1;
//        String citystr = editCity.getText().toString();
//        String keystr = keyWorldsView.getText().toString();
//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city(citystr).keyword(keystr).pageNum(loadIndex));
    }

    /**
     * 响应周边搜索按钮点击事件
     *
     * @param v
     */
    public void searchNearbyProcess(View v) {
        searchType = 2;
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(bindLock.bindData.selectBind).sortType(PoiSortType.distance_from_near_to_far).location(center)
                .radius(radius).pageNum(loadIndex);
        mPoiSearch.searchNearby(nearbySearchOption);
    }


    /**
     * 响应区域搜索按钮点击事件
     *
     * @param v
     */
    public void searchBoundProcess(View v) {
        searchType = 3;
        mPoiSearch.searchInBound(new PoiBoundSearchOption().bound(searchbound)
                .keyword(bindLock.bindData.selectBind));

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection)
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(poiResult);
            overlay.addToMap();
            overlay.zoomToSpan();

            switch (searchType) {
                case 2:
                    showNearbyArea(center, radius);
                    break;
                case 3:
                    showBound(searchbound);
                    break;
                default:
                    break;
            }

            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(this, poiDetailResult.getName() + ": " + poiDetailResult.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(mCurrentDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

//        public void onReceivePoi(BDLocation poiLocation) {
//        }
    }

    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param res
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
//        sugAdapter = new ArrayAdapter<String>(PoiSearchDemo.this, android.R.layout.simple_dropdown_item_1line, suggest);
//        keyWorldsView.setAdapter(sugAdapter);
//        sugAdapter.notifyDataSetChanged();
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * 对周边检索的范围进行绘制
     *
     * @param center
     * @param radius
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_user);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        OverlayOptions ooCircle = new CircleOptions().fillColor(0xCCCCCC00)
                .center(center).stroke(new Stroke(5, 0xFFFF00FF));
//                .radius(radius);
        mBaiduMap.addOverlay(ooCircle);
    }

    /**
     * 对区域检索的范围进行绘制
     *
     * @param bounds
     */
    public void showBound(LatLngBounds bounds) {
        BitmapDescriptor bdGround = BitmapDescriptorFactory
                .fromResource(R.mipmap.map_user);

        OverlayOptions ooGround = new GroundOverlayOptions()
                .positionFromBounds(bounds).image(bdGround).transparency(0.8f);
        mBaiduMap.addOverlay(ooGround);

        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);

        bdGround.recycle();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
//        searchNearbyProcess(null);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);

        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


}
