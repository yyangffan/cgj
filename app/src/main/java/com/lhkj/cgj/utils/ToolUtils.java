package com.lhkj.cgj.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizc on 2016/4/13.
 * 类描述：APP中所用的小工具类
 * 创建人：lizc
 * 创建时间：2016/4/13 16:29
 * 修改人：lizc
 * 修改时间：2016/4/13 16:29
 * 修改备注：
 */
public class ToolUtils {
    /**
     * 验证是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobiles==null){
            return false;
        }
        Pattern p = Pattern.compile("^[1]+[3,5,7,8,4,6,9]+\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 验证是否是6位数字短信验证码
     *
     * @param mmscode
     * @return
     */
    public static boolean isMMSCode(String mmscode) {
        Pattern p = Pattern.compile("(^\\d{6}$)");
        Matcher m = p.matcher(mmscode);
        return m.matches();
    }

    /**
     * 验证是否符合中文姓名类型
     * @param name
     * @return
     */
    public static boolean isName(String name){
        Pattern p= Pattern.compile("^[\\u4E00-\\u9FA5]{2,4}");
        Matcher m= p.matcher(name);
        return m.matches();
    }

    /**
     * 验证是否符合身份证18位类型
     * @param id
     * @return
     */
    public static boolean isId(String id){
        Pattern p= Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        Matcher m=p.matcher(id);
        return m.matches();
    }
    /**
     * 验证是否有空数据
     * @param data
     * @return
     */
    public static boolean isDataEmpty(String ...data){
        for (String arg : data) {
            if ( arg.equals("")){
                return true;
            }
        }
        return false;
    }

    public static boolean isPwd(String pwd){
        if (pwd==null){
            return false;
        }
        if (pwd.length()<6||pwd.length()>18){
            return false;
        }else {
//            Pattern p= Pattern.compile("([a-z]+[0-9]+)|([0-9]+[a-z])*");
//            Matcher m=p.matcher(pwd);
//            return m.matches();
            return true;
        }
    };


    //文字转换为图片
    public Drawable TextToDrawable(String str) {
        Bitmap bitmap = Bitmap.createBitmap(180, 220, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(45);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        String s = str;
        Paint.FontMetrics fm = paint.getFontMetrics();
        canvas.drawText(s, 0, 220 + fm.top - fm.ascent, paint);
        canvas.save();
        Drawable drawableright = new BitmapDrawable(bitmap);
        drawableright.setBounds(0, 0, drawableright.getMinimumWidth(),
                drawableright.getMinimumHeight());
        return drawableright;
    }


    public static String toMd5Param(String data) {
        String Md5String = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            Md5String = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Md5String;
    }

    /**
     * 验证是否符合中文姓名类型
     * @param name
     * @return
     */
    public static boolean isOkChar(String name){
        Pattern p= Pattern.compile("^[\\u4E00-\\u9FA5,a-z,A-Z,0-9]");
        Matcher m= p.matcher(name);
        return m.matches();
    }

    public static String isOk(String name){
        char[]aa= name.toCharArray();
        for (int i = 0; i <aa.length ; i++) {
            if (!isOkChar(aa[i]+"")){
                aa[i]=' ';
            }
        }
        return new String(aa);
    }
}
