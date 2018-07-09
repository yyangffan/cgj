package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.method.ReplacementTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseSingleTextAdapter;
import com.lhkj.cgj.databinding.ActivityPerfectBinding;
import com.lhkj.cgj.databinding.PopToastBinding;
import com.lhkj.cgj.databinding.SmallListBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.request.HttpFileTask;
import com.lhkj.cgj.network.response.CarInfoResponse;
import com.lhkj.cgj.network.response.CarPostResponse;
import com.lhkj.cgj.network.response.RanliaoResponse;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.ui.mine.PerfectActivity;
import com.lhkj.cgj.utils.PopManager;
import com.lhkj.cgj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 浩琦 on 2017/6/21.
 * 完善信息
 */

public class PerfectLock {
    public PerfectData perfectData;
    private ActivityPerfectBinding perfectBinding;
    private Context context;
    private BaseSingleTextAdapter bindAdapter;
    private ArrayList<String> bindList;
    private ArrayList<String> brandIds;
    private ArrayList<Map<String, Object>> mCarIds;
    private int sex = 1;
    private boolean isUserEx;
    private boolean isCarEx;

    public PerfectLock(final Context context, ActivityPerfectBinding perfectBinding) {
        this.context = context;
        this.perfectBinding = perfectBinding;
        this.isCarEx = false;
        perfectData = new PerfectData();
        bindList = new ArrayList<>();
        brandIds = new ArrayList<>();
        mCarIds = new ArrayList<>();
        bindAdapter = new BaseSingleTextAdapter(context, bindList);
        bindAdapter.setTextSize(18f);
        bindAdapter.setTextGravity(Gravity.CENTER_HORIZONTAL);
        getPosId();//获取车牌号--地区
        /*新添加*/
        ranliao_list=new ArrayList<>();
        bindAdapter_ranliao = new BaseSingleTextAdapter(context, ranliao_list);
        bindAdapter_ranliao.setTextSize(18f);
        bindAdapter_ranliao.setTextGravity(Gravity.CENTER_HORIZONTAL);
        mRanliaoList = new ArrayList<>();
        ranliao_id=User.getUser().userOilId;
        getRanliao();


        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PerfectActivity) context).finish();
            }
        });
    }


    public void userInfo() {
        perfectBinding.perSelect.setVisibility(View.GONE);
        perfectBinding.perUser.setVisibility(View.VISIBLE);

        perfectBinding.headNote.setVisibility(View.GONE);
        perfectBinding.userName.setVisibility(View.VISIBLE);
        perfectBinding.sexWoman.setVisibility(View.GONE);
        perfectBinding.sexMan.setVisibility(View.GONE);
        perfectBinding.userNike.setGravity(Gravity.RIGHT);
        perfectBinding.userSex.setVisibility(View.VISIBLE);

        perfectBinding.userNike.setEnabled(false);
        perfectBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.userinfo);
        perfectBinding.include.getAppBarLock().barData.isRight = true;
        perfectBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.modify);
//        perfectBinding.include.setAppBarLock(new AppBarLock(context, R.string.userinfo, R.mipmap.icon_back, R.string.modify).setLeft(-1));
        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        perfectBinding.include.titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userExchange();
            }
        });
        userFlush();

        perfectBinding.include.getAppBarLock().setLeft(AppBarLock.OTHER);
        perfectBinding.include.getAppBarLock().barData.notifyChange();
    }


    public void seletSex(int sex) {
        if (!isUserEx) {
            return;
        }
        if (sex == 1) {
            perfectBinding.sexMan.setBackground(context.getResources().getDrawable(R.drawable.red_curr));
            perfectBinding.sexWoman.setBackground(context.getResources().getDrawable(R.drawable.gray_radius));
            perfectData.usersex = "男";
        } else {
            perfectBinding.sexMan.setBackground(context.getResources().getDrawable(R.drawable.gray_radius));
            perfectBinding.sexWoman.setBackground(context.getResources().getDrawable(R.drawable.red_curr));
            perfectData.usersex = "女";
        }
        this.sex = sex;
    }

    private void userFlush() {
        String name = SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_NAME, "");
        String pwd = SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_PWD, "");
        RunTime.operation.getMine().tryLogin(name, pwd, new User.AuthorizationListener() {
            @Override
            public void authorization(boolean isOk) {
                perfectData.flush();
                perfectData.notifyChange();
            }
        });
    }

    private void userExchange() {
        if (isUserEx) {
            userPost();
        } else {
            isUserEx = true;
            perfectBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.save);
            perfectBinding.include.getAppBarLock().barData.notifyChange();
            perfectBinding.userNike.setEnabled(true);
            perfectBinding.headNote.setVisibility(View.VISIBLE);
            perfectBinding.userName.setVisibility(View.GONE);
            perfectBinding.sexWoman.setVisibility(View.VISIBLE);
            perfectBinding.sexMan.setVisibility(View.VISIBLE);
            perfectBinding.userNike.setGravity(Gravity.LEFT);
            perfectBinding.userSex.setVisibility(View.GONE);

            if (perfectData.usersex != null && "女".equals(perfectData.usersex)) {
                perfectBinding.sexMan.setBackground(context.getResources().getDrawable(R.drawable.gray_radius));
                perfectBinding.sexWoman.setBackground(context.getResources().getDrawable(R.drawable.red_curr));
            } else {
                perfectBinding.sexMan.setBackground(context.getResources().getDrawable(R.drawable.red_curr));
                perfectBinding.sexWoman.setBackground(context.getResources().getDrawable(R.drawable.gray_radius));
            }

            perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUserEx = false;
                    userInfo();
                }
            });

            perfectBinding.include.getAppBarLock().barData.notifyChange();

        }
    }
    /*选择燃料类型*/
    public void selectRanliao() {
        showRanliaoPop();
    }

    /*获取燃料类型*/
    private ArrayList<RanliaoResponse.Info> mRanliaoList;
    private String ranliao_id = "";
    private BaseSingleTextAdapter bindAdapter_ranliao;
    private ArrayList<String> ranliao_list;

    public void getRanliao() {
        HashMap hashMap = new HashMap();
        RunTime.operation.tryPostRefresh(RanliaoResponse.class, RunTime.operation.getMine().GETRANLIAO, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                RanliaoResponse ranliaoResponse = (RanliaoResponse) data;
                if (id == 100) {
                    if (ranliaoResponse.info != null) {
                        mRanliaoList.clear();
                        ranliao_list.clear();
                        mRanliaoList.addAll(ranliaoResponse.info);
//                        perfectBinding.signRanliao.setText(mRanliaoList.get(0).you_name);
//                        ranliao_id = mRanliaoList.get(0).id;
                        for(RanliaoResponse.Info infoBean:ranliaoResponse.info){
                            ranliao_list.add(infoBean.you_name);
                        }
                    }
                }
            }
        });
    }
    /*展示燃料Pop*/
    public void showRanliaoPop() {
        bindAdapter_ranliao.notifyDataSetChanged();
        final PopManager popManager = new PopManager(context);
        final SmallListBinding smallListBinding = ((SmallListBinding) popManager.showAsDrowPop(perfectBinding.linearLayout21, R.layout.small_list));
        smallListBinding.setSmallAdapter(bindAdapter_ranliao);
        smallListBinding.smallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popManager.stop();
                perfectBinding.signRanliao.setText(ranliao_list.get(position));
                ranliao_id = mRanliaoList.get(position).id;
            }
        });
    }

    private void userPost() {
        if (perfectData.usernike.isEmpty()) {
            Toast.makeText(context, "请填写昵称", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("mobile", perfectData.username);
        hashMap.put("nickname", perfectData.usernike);
        hashMap.put("sex", sex + "");
        if (((PerfectActivity) context).iconPath == null) {
            BitmapDrawable bd = (BitmapDrawable) User.getUser().userIcon;
            hashMap.put("file", ((PerfectActivity) context).saveFile(bd.getBitmap(), "img_name.png").getPath());
        } else {
            hashMap.put("file", ((PerfectActivity) context).iconPath);
        }
        new HttpFileTask().UpLoadPicture(RunTime.operation.getMine().USER_SUB, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(final int id, final Object data) {
                ((PerfectActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (id == 200) {
                            userFlush();
                            //刷新
                            final PopManager popManager = new PopManager(context);
                            PopToastBinding popToastBinding = ((PopToastBinding) popManager.showPop(perfectBinding.linearLayout20, R.layout.pop_toast));
                            popToastBinding.popText.setText(context.getResources().getString(R.string.subsucce));
                            popManager.setMissLisenter(new PopManager.MissLisenter() {
                                @Override
                                public void miss() {
                                    isUserEx = false;
                                    userInfo();
                                }
                            });
                            popToastBinding.popEnter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    popManager.stop();
                                }
                            });
                        } else {
                            Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void exit() {
        goBack();
        if (((PerfectActivity) context).corpFile != null) {
            ((PerfectActivity) context).corpFile.delete();
        }
    }

    /*刚进入车辆信息*/
    public void carInfo() {
        perfectBinding.perSelect.setVisibility(View.GONE);
        perfectBinding.perCar.setVisibility(View.VISIBLE);
        perfectBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.carinfo);
        perfectBinding.include.getAppBarLock().barData.isRight = true;
        perfectBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.modify);
        perfectBinding.include.titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carExchange();
            }
        });
        perfectBinding.plateMin.setEnabled(false);
        perfectBinding.platenumber.setEnabled(false);
        perfectBinding.signRanliao.setEnabled(false);
        perfectBinding.signRanliaoImgv.setEnabled(false);
        perfectBinding.platenumber.setTransformationMethod(new A2bigA());
//        perfectBinding.include.setAppBarLock(new AppBarLock(context, R.string.carinfo, R.mipmap.icon_back, 0, true, false).setLeft(-1));
        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        perfectBinding.include.getAppBarLock().setLeft(AppBarLock.OTHER);
        perfectBinding.include.getAppBarLock().barData.notifyChange();

        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CarPostResponse.class, RunTime.operation.getMine().CAR_POST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CarPostResponse carPostResponse = (CarPostResponse) data;
                if (carPostResponse.getInfo().getBrand_name() != null) {
                    perfectData.brand = carPostResponse.getInfo().getBrand_name();
                }
                if (carPostResponse.getInfo().getCar_km() != null) {
                    perfectData.plateMin = carPostResponse.getInfo().getCar_km();
                }
                if (carPostResponse.getInfo().getDiqu_name() != null) {
                    perfectData.aftPlateNumber = carPostResponse.getInfo().getDiqu_name();
                    perfectData.brandId = carPostResponse.getInfo().car_brand;
                } else {
                    for (Map<String, Object> m : mCarIds) {
                        if (m.get("brand_name").equals("冀")) {
                            perfectData.aftPlateNumber = "冀";
                            perfectData.brandId = (String) m.get("id");
                        } else {
                            perfectData.brandId = carPostResponse.getInfo().car_brand;
                        }
                    }
                }
                if (carPostResponse.getInfo().getCar_number() != null) {
                    perfectData.plateNumber = carPostResponse.getInfo().getCar_number();
                }
                if (carPostResponse.getInfo().getCar_date() != null) {
                    perfectData.plateAge = carPostResponse.getInfo().getCar_date();
                }
                perfectData.addId = carPostResponse.getInfo().car_guishu;
                if (carPostResponse.getInfo().getSeries_name() != null && !carPostResponse.getInfo().getSeries_name().equals("")) {
                    perfectData.brandAft = carPostResponse.getInfo().getSeries_name();
                }
                perfectData.brandAftId = carPostResponse.getInfo().car_type;
                perfectData.notifyChange();
            }
        });
    }

    /*进行修改车辆信息*/
    private void carExchange() {
        if (isCarEx) {
            carInfo();
        } else {
            isCarEx = true;
            perfectBinding.include.getAppBarLock().barData.isRight = false;
            perfectBinding.include.getAppBarLock().barData.notifyChange();
            perfectBinding.plateMin.setEnabled(true);
            perfectBinding.platenumber.setEnabled(true);

            perfectBinding.signRanliao.setEnabled(true);
            perfectBinding.signRanliaoImgv.setEnabled(true);

            perfectBinding.include.getAppBarLock().barData.isRight = true;
            perfectBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.save);
            perfectBinding.include.titleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeCar();
                }
            });
            perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCarEx = false;
                    carInfo();
                }
            });
            perfectBinding.include.getAppBarLock().barData.notifyChange();
        }
    }

    /*获取车牌号列表*/
    public void getPosId() {
        mCarIds.clear();
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_ADD, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CarInfoResponse infoResponse = (CarInfoResponse) data;
                for (CarInfoResponse.Info info : infoResponse.info) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("brand_name", info.name);
                    map.put("id", info.id);
                    mCarIds.add(map);
                }
            }
        });

    }

    public void changeBrand() {
        if (!isCarEx) {
            return;
        }
        bindList.clear();
        brandIds.clear();
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_BRAND, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CarInfoResponse infoResponse = (CarInfoResponse) data;
                for (CarInfoResponse.Info info : infoResponse.info) {
                    bindList.add(info.brand_name);
                    brandIds.add(info.id);
                }
                showSmallList(0);
            }
        });

    }

    public void changeBrandAft() {
        if (!isCarEx) {
            return;
        }
        bindList.clear();
        brandIds.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("pid", perfectData.brandId);
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_BRAND_AFT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (100 == id) {
                    Toast.makeText(context, "该品牌下暂无型号", Toast.LENGTH_SHORT).show();
                    return;
                }
                CarInfoResponse infoResponse = (CarInfoResponse) data;
                if (infoResponse.info.size() == 0) {
                    Toast.makeText(context, "该品牌下暂无型号", Toast.LENGTH_SHORT).show();
                }
                for (CarInfoResponse.Info info : infoResponse.info) {
                    bindList.add(info.series_name);
                    brandIds.add(info.id);
                }
                showSmallList(1);
            }
        });

    }

    public void changeAftPlateNumber() {
        if (!isCarEx) {
            return;
        }
        bindList.clear();
        brandIds.clear();
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_ADD, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CarInfoResponse infoResponse = (CarInfoResponse) data;
                for (CarInfoResponse.Info info : infoResponse.info) {
                    bindList.add(info.name);
                    brandIds.add(info.id);
                }
                showSmallList(2);
            }
        });

    }

    public void changePlateAge() {
        if (!isCarEx) {
            return;
        }
        bindList.clear();
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        for (int i = 1901 + date.getYear() - 20; i < 1901 + date.getYear(); i++) {
            bindList.add("" + i);
        }
        showSmallList(3);
    }

    private void showSmallList(final int i) {
        bindAdapter.notifyDataSetChanged();
        final PopManager popManager = new PopManager(context);
        final SmallListBinding smallListBinding = ((SmallListBinding) popManager.showPop(perfectBinding.linearLayout20, R.layout.small_list));
        smallListBinding.setSmallAdapter(bindAdapter);
        smallListBinding.smallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popManager.stop();
                switch (i) {
                    case 0:
                        perfectData.brand = bindList.get(position);
                        perfectData.brandId = brandIds.get(position);
                        perfectData.isBrand = true;
                        perfectData.brandAft = "请选择";
                        break;
                    case 1:
                        perfectData.brandAft = bindList.get(position);
                        perfectData.brandAftId = brandIds.get(position);
                        break;
                    case 2:
                        perfectData.aftPlateNumber = bindList.get(position);
                        perfectData.addId = brandIds.get(position);
                        break;
                    case 3:
                        perfectData.plateAge = bindList.get(position);
                        break;
                }
                perfectData.notifyChange();
            }
        });

    }


    public void changeIcon(View v) {
        if (!isUserEx) {
            return;
        }
        ((PerfectActivity) context).chengeIcon(v);
    }

    /*提交车信息*/
    public void changeCar() {
        if (perfectData.plateNumber == null || perfectData.plateNumber.equals("")) {
            Toast.makeText(context, "请填写车牌号", Toast.LENGTH_SHORT).show();
            return;
        } else if (perfectData.plateNumber.length() < 6) {
            Toast.makeText(context, "车牌号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("brand_id", perfectData.brandId);
        if (!perfectData.brandAft.equals(context.getResources().getString(R.string.pleaseselect))) {
            hashMap.put("mark_id", perfectData.brandAftId);
        }
        hashMap.put("diqu", perfectData.addId);
        hashMap.put("number", perfectData.plateNumber);
        hashMap.put("car_date", perfectData.plateAge == null ? "" : perfectData.plateAge);
        hashMap.put("car_km", perfectData.plateMin);
        hashMap.put("oil_type", ranliao_id);
        RunTime.operation.tryPostRefreshF(SuccessResponse.class, RunTime.operation.getMine().CAR_SUB, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                SuccessResponse bean= (SuccessResponse) data;
                if (200 == id) {
                    perfectBinding.signRanliao.setEnabled(false);
                    perfectBinding.signRanliaoImgv.setEnabled(false);
                    User.getUser().oil_type=ranliao_id;
                    User.getUser().oil_type_name=perfectBinding.signRanliao.getText().toString();
                    User.getUser().aftplateNumber=perfectBinding.diqu.getText().toString()+perfectBinding.platenumber.getText().toString();


                    final PopManager popManager = new PopManager(context);
                    PopToastBinding popToastBinding = ((PopToastBinding) popManager.showPop(perfectBinding.linearLayout20, R.layout.pop_toast));
                    popToastBinding.popText.setText(context.getResources().getString(R.string.subsucce));
                    popManager.setMissLisenter(new PopManager.MissLisenter() {
                        @Override
                        public void miss() {
                            isCarEx = false;
                            carInfo();
                        }
                    });
                    popToastBinding.popEnter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popManager.stop();
                        }
                    });
                } else if (100 == id) {
                    Toast.makeText(context, "您未做修改", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, bean.getResultmsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void goBack() {
        perfectBinding.perSelect.setVisibility(View.VISIBLE);
        perfectBinding.perCar.setVisibility(View.GONE);
        perfectBinding.perUser.setVisibility(View.GONE);
        perfectBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.perfect);
        perfectBinding.include.getAppBarLock().barData.isRight = false;
        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PerfectActivity) context).finish();
            }
        });
//        perfectBinding.include.setAppBarLock(new AppBarLock(context, R.string.perfect, R.mipmap.icon_back, 0, true, false));
        perfectBinding.include.getAppBarLock().barData.notifyChange();
    }

    public class PerfectData extends BaseObservable {
        PerfectData() {
            username = User.getUser().username;
            usernike = User.getUser().usernike;
            usersex = User.getUser().usersex;
            userIcon = User.getUser().userIconClone;
            oilName=User.getUser().oil_type_name;
            oilId=User.getUser().oil_type;
        }

        public String username;
        public String usernike;
        public String usersex;
        public Drawable userIcon;
        public String oilName;
        public String oilId;

        public String brandId;
        public String brand = context.getResources().getString(R.string.pleaseselect);
        public boolean isBrand = false;
        public String brandAft = context.getResources().getString(R.string.pleaseselect);
        public String brandAftId;
        public String plateNumber;
        public String aftPlateNumber = context.getResources().getString(R.string.pleaseselect);
        public String addId;
        public String plateAge = context.getResources().getString(R.string.pleaseselect);
        public String plateMin;

        public void flush() {
            username = User.getUser().username;
            usernike = User.getUser().usernike;
            if (User.getUser().usersex.equals("2")) {
                sex = 2;
            } else {
                sex = 1;
            }
            userIcon = User.getUser().userIcon;
        }
    }

    public class A2bigA extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }

    }
}
