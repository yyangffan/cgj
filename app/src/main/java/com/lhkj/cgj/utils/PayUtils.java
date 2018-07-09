package com.lhkj.cgj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lhkj.cgj.R;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.PayResult;
import com.lhkj.cgj.network.request.GsonUtil;
import com.lhkj.cgj.network.response.PayNumReponse;
import com.lhkj.cgj.network.response.PayOrderReponse;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by 浩琦 on 2017/8/14.
 */

public class PayUtils implements IWXAPIEventHandler{
    private Context context;
    private String orderid;
    private String num;
    private String type;
    private int payTag;

    public static final String WXAPP_ID = "wx4c41b416265916cf ";
    private IWXAPI api;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public void tryPay(Context context, String orderid, String type) {
        this.context = context;
        this.orderid = orderid;
        this.type = type;
        // 调用支付
        if (type.equals("2")) {
            lllPay();
        } else {
            rmbPay();
        }
    }

    private void lllPay() {
        HashMap hashMap = new HashMap();
        hashMap.put("order_id", orderid);
        hashMap.put("type", type);
        RunTime.operation.tryPostRefreshF(PayNumReponse.class, RunTime.operation.getShop().GO_PAY, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                switch (id) {
                    case 200:
                        showDialog();
                        break;
                    case 202:
                        Toast.makeText(context, "积分不足", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void rmbPay() {
        showPayDialog();
    }

    private void showPayDialog() {
        final String[] items = {"微信", "支付宝"};
        payTag = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(context);
        singleChoiceDialog.setTitle("请选择支付方式");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payTag = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (payTag) {
                            case 0:  // 微信
                                wxOrder();
                                dialog.dismiss();
                                break;
                            case 1:  // 支付宝
                                zfbOrder();
                                dialog.dismiss();
                                break;
                            default:
                                Toast.makeText(context, "请选择支付方式", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        singleChoiceDialog.show();
        payTag = 0;
    }

    private void wxOrder() {
        HashMap hashMap = new HashMap();
        hashMap.put("order_id", orderid);
        hashMap.put("type", type);
        RunTime.operation.tryPostRefreshF(PayOrderReponse.class, RunTime.operation.getShop().WX_PAY, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                PayOrderReponse payOrder = (PayOrderReponse) data;
                switch (payOrder.getResultcode()){
                    case "200":
                        wxPay(payOrder);
                        break;
                    case "201":
                        Toast.makeText(context, "订单不存在", Toast.LENGTH_SHORT).show();
                        break;
                    case "202":
                        Toast.makeText(context, "积分不足", Toast.LENGTH_SHORT).show();
                        break;
                    case "203":
                        Toast.makeText(context, "订单已支付", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "支付失败请重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void zfbOrder() {
        HashMap hashMap = new HashMap();
        hashMap.put("order_id", orderid);
        hashMap.put("type", type);
        RunTime.operation.tryPostRefreshF(PayOrderReponse.class, RunTime.operation.getShop().ZFB_PAY, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                PayOrderReponse payOrder = (PayOrderReponse) data;
                switch (payOrder.getResultcode()){
                    case "200":
                        zfbPay(payOrder);
                        break;
                    case "201":
                        Toast.makeText(context, "订单不存在", Toast.LENGTH_SHORT).show();
                        break;
                    case "202":
                        Toast.makeText(context, "积分不足", Toast.LENGTH_SHORT).show();
                        break;
                    case "203":
                        Toast.makeText(context, "订单已支付", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "支付失败请重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void wxPay(PayOrderReponse payOrder){
        api = WXAPIFactory.createWXAPI(context, WXAPP_ID, true);
        PayReq req = new PayReq();
        try {
            req.appId = payOrder.data.appid;
            req.partnerId = payOrder.data.partnerid;
            req.prepayId = payOrder.data.prepayid;
            req.nonceStr = payOrder.data.noncestr;
            req.timeStamp = payOrder.data.timestamp;
            req.packageValue = payOrder.data.packageX;
            req.sign = payOrder.data.sign;
            Log.i("user ", "支付-------" + req.appId + "  " + req.partnerId + "  " + req.packageValue + "  " + req.sign);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "微信支付错误", Toast.LENGTH_LONG).show();
        }
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.registerApp(req.appId);
        api.sendReq(req);
    }

    private void zfbPay(PayOrderReponse payOrder){
        final String orderInfo = payOrder.info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private android.os.Handler mHandler = new android.os.Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        showDialog();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        int errCode = baseResp.errCode;
        String tip = "";
        if (errCode == 0) {
            tip = "支付成功";
            showDialog();
            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
        } else if (errCode == -1) {
            tip = "支付失败";
            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
        } else if (errCode == -2) {
            tip = "取消支付";
            Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(((Activity) context));

        builder.setIcon(R.mipmap.pay);
        builder.setTitle("支付成功");
        builder.setMessage("请您在“我的订单”及时查看\n并在" + User.getUser().userOilName + "及时兑换");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });

        builder.create().show();
    }

}
