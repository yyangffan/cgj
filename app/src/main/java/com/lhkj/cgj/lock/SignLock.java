package com.lhkj.cgj.lock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseSingleTextAdapter;
import com.lhkj.cgj.databinding.ActivitySignBinding;
import com.lhkj.cgj.databinding.SmallListBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.CarInfoResponse;
import com.lhkj.cgj.network.response.RanliaoResponse;
import com.lhkj.cgj.ui.login.AgreementActivity;
import com.lhkj.cgj.ui.login.SignActivity;
import com.lhkj.cgj.ui.other.UMengSave;
import com.lhkj.cgj.utils.PopManager;
import com.lhkj.cgj.utils.SharedPreferencesUtil;
import com.lhkj.cgj.utils.ToolUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class SignLock {
    private Context context;
    public SignData signData;
    private ActivitySignBinding signBinding;
    private int timer;
    private String tryCode;
    private BaseSingleTextAdapter bindAdapter;
    private ArrayList<String> bindList;
    private ArrayList<String> brandIds;
    private String signDiqu = "";
    private String signDiquId = "";
    private ArrayList<RanliaoResponse.Info> mRanliaoList;
    private String ranliao_id = "";
    private BaseSingleTextAdapter bindAdapter_ranliao;
    private ArrayList<String> ranliao_list;


    public SignLock(Context context, ActivitySignBinding signBinding) {
        this.context = context;
        this.signBinding = signBinding;
        signData = new SignData();
        signData.isArgee = true;
        timer = 30;
        bindList = new ArrayList<>();
        brandIds = new ArrayList<>();
        bindAdapter = new BaseSingleTextAdapter(context, bindList);
        bindAdapter.setTextSize(18f);
        bindAdapter.setTextGravity(Gravity.CENTER_HORIZONTAL);
        ranliao_list = new ArrayList<>();
        bindAdapter_ranliao = new BaseSingleTextAdapter(context, ranliao_list);
        bindAdapter_ranliao.setTextSize(18f);
        bindAdapter_ranliao.setTextGravity(Gravity.CENTER_HORIZONTAL);


        mRanliaoList = new ArrayList<>();
        getRanliao();
        getPosId();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timer--;
            if (timer <= 0) {
                signBinding.sendcode.setText(context.getResources().getString(R.string.sendcode));
                timer = 30;
            } else {
                signBinding.sendcode.setText("请稍后" + timer);
                sendTime(1000);
            }
        }
    };

    private void sendTime(long time) {
        handler.sendEmptyMessageDelayed(0, time);
    }

    public void sendCode() {
        if (ToolUtils.isMobileNO(signData.signPhone)) {
            if (timer == 30) {
                sendTime(1000);
                timer--;
                signBinding.sendcode.setText("请稍后" + timer);
                RunTime.operation.getMine().trySendCode(signData.signPhone, new User.AuthorizationListener() {
                    @Override
                    public void authorization(boolean isOk) {
                        if (isOk) {
                            tryCode = (String) RunTime.getRunTime(RunTime.SIGN_CODE);
                        } else {
                            ((SignActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        } else {
            Toast.makeText(context, "请填写有效的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (ToolUtils.isMobileNO(signData.signPhone) && signData.signPwd.equals(signData.signRepwd)) {
            if (!signData.isArgee) {
                Toast.makeText(context, "请同意协议", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!ToolUtils.isPwd(signData.signPwd)) {
                Toast.makeText(context, "密码格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }
            toSign();
        } else {
            Toast.makeText(context, "手机号填写错误或两次密码不一致", Toast.LENGTH_SHORT).show();
        }
    }

    public void showProtocol() {
        context.startActivity(new Intent(context, AgreementActivity.class));
//        RunTime.operation.tryPostRefresh(ProtocolReponse.class, RunTime.operation.getMine().PROTOCOL, new HashMap(), new Operation.Listener() {
//            @Override
//            public void tryReturn(int id, Object data) {
//                ProtocolReponse protocolReponse = (ProtocolReponse) data;
//                final PopManager popManager = new PopManager(context);
//                PopDefTextBinding defTextBinding = (PopDefTextBinding) popManager.showPop(signBinding.linearLayout15, R.layout.pop_def_text);
//                defTextBinding.textTitle.setText(protocolReponse.info.title);
//                defTextBinding.textNote.setText(protocolReponse.info.content);
//                defTextBinding.textEnter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popManager.stop();
//                    }
//                });
//            }
//        });//
    }

    // TODO: 2017/11/27 注册需要修改上传参数
    private void toSign() {
        String number = signBinding.platenumber.getText().toString();
        if (!tryCode.equals(signData.signCode)) {
            Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (number == null || number.equals("")) {
            Toast.makeText(context, "请填写车牌号", Toast.LENGTH_SHORT).show();
            return;
        }

        RunTime.operation.getMine().trySign(signData.signPhone, signData.signCode, signData.signPwd, signData.signShare, signDiquId, number, ranliao_id, new User.AuthorizationListener() {
            @Override
            public void authorization(boolean isOk) {
                if (isOk) {
//                    final PopManager popManager = new PopManager(context);
//                    final PopToastBinding toastBinding = (PopToastBinding) popManager.showPop(signBinding.linearLayout15, R.layout.pop_toast);
//                    toastBinding.popIms.setImageResource(R.mipmap.success);
//                    toastBinding.popText.setText("注册成功！点击确定直接登录");
////                    popManager.disPop(toastBinding.popEnter, true);
//                    toastBinding.popEnter.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
                    getIn();
//                            popManager.disPop(toastBinding.popEnter, true);
//                        }
//                    });
                    UMengSave.saveMsg(signData.signPhone, signData.signPwd);

                    SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_NAME, signData.signPhone);
                    SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_PWD, signData.signPwd);
                    Intent intent = new Intent();
                    intent.putExtra("phone", signData.signPhone);
                    ((SignActivity) context).setResult(Activity.RESULT_OK, intent);
                }
            }
        });
    }

    /*注册成功直接登录*/
    public void getIn() {
        RunTime.setData(RunTime.LOGIN_ID, true);

        RunTime.operation.getMine().tryLogin(signData.signPhone, signData.signPwd,
                new User.AuthorizationListener() {
                    @Override
                    public void authorization(boolean isOk) {
                        if (isOk) {
                            Intent intent = new Intent();
                            intent.putExtra("phone", signData.signPhone);
                            ((SignActivity) context).setResult(Activity.RESULT_OK, intent);
                            ((SignActivity) context).finish();
                            SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_NAME, signData.signPhone);
                            SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_PWD, signData.signPwd);
                        } else {
                            Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*获取车牌号列表*/
    public void getPosId() {
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_ADD, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    CarInfoResponse infoResponse = (CarInfoResponse) data;
                    for (CarInfoResponse.Info info : infoResponse.info) {
                        if (info.name.equals("冀")) {
                            signDiqu = "冀";
                            signDiquId = info.id;
                            signBinding.signTv.setText(signDiqu);
                        }
                    }
                }
            }
        });

    }

    /*选择车牌归属地区*/
    public void changeAftPlateNumber() {
        bindList.clear();
        brandIds.clear();
        RunTime.operation.tryPostRefresh(CarInfoResponse.class, RunTime.operation.getMine().CAR_POST_ADD, new HashMap(), new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    CarInfoResponse infoResponse = (CarInfoResponse) data;
                    for (CarInfoResponse.Info info : infoResponse.info) {
                        bindList.add(info.name);
                        brandIds.add(info.id);
                    }
                    showSmallList();
                }
            }
        });
    }

    /*选择燃料类型*/
    public void selectRanliao() {
        showRanliaoPop();
    }

    /*获取燃料类型*/
    public void getRanliao() {
//        RunTime.operation.getMine().GETRANLIAO
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
                        signBinding.signRanliao.setText(mRanliaoList.get(0).you_name);
                        ranliao_id = mRanliaoList.get(0).id;
                        for (RanliaoResponse.Info infoBean : ranliaoResponse.info) {
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
        final SmallListBinding smallListBinding = ((SmallListBinding) popManager.showAsDrowPop(signBinding.linearLayout21, R.layout.small_list));
        smallListBinding.setSmallAdapter(bindAdapter_ranliao);
        smallListBinding.smallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popManager.stop();
                signBinding.signRanliao.setText(ranliao_list.get(position));
                ranliao_id = mRanliaoList.get(position).id;
            }
        });
    }

    private void showSmallList() {
        bindAdapter.notifyDataSetChanged();
        final PopManager popManager = new PopManager(context);
        final SmallListBinding smallListBinding = ((SmallListBinding) popManager.showPop(signBinding.linearLayout2, R.layout.small_list));
        smallListBinding.setSmallAdapter(bindAdapter);
        smallListBinding.smallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popManager.stop();
                signBinding.signTv.setText(bindList.get(position));
                signDiquId = brandIds.get(position);
                signDiqu = bindList.get(position);
//                perfectData.aftPlateNumber = bindList.get(position);
//                perfectData.addId = brandIds.get(position);
            }
        });

    }

    public void sign() {
        ((SignActivity) context).finish();
    }

    public class SignData {
        public String signPhone;
        public String signCode = "";
        public String signPwd = "";
        public String signRepwd = "";
        public String signShare = "";
        public boolean isArgee;
    }
}
