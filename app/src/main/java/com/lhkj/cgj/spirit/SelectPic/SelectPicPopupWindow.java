package com.lhkj.cgj.spirit.SelectPic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.PopImgBinding;
import com.lhkj.cgj.utils.PixelUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SelectPicPopupWindow extends Activity {
    private Intent intent;
    private Uri corpUri;
    private File corpFile;
    private final int PHOTO_TAKE = 1;
    private final int PHOTO_ZOOM = 2; // 缩放
    private final int PHOTO_RESOULT = 3;// 结果
    private Uri pickUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PopImgBinding imgBinding = DataBindingUtil.setContentView(this, R.layout.pop_img);
        intent = getIntent();
        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        imgBinding.popLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    public void onCancel(View view) {
        finish();
    }

    public void pickPhoto(View view) {
        try {
            // 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
            // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PHOTO_ZOOM);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void takePhoto(View view) {
        try {
            corpFile = new File(getExternalCacheDir().getPath() + File.separator + "img_name1.png");
            try {
                corpFile.createNewFile();
                pickUri = Uri.fromFile(corpFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
            // 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            //指定拍照后的缓存路径(Uri)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, pickUri);
            startActivityForResult(intent, PHOTO_TAKE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            // 如果是调用相机拍照时
            case PHOTO_TAKE:
                startPhotoZoom(pickUri, PixelUtil.dpToPx(this, 60));
                setPicToView(data);
                break;
            // 如果是直接从相册获取
            case PHOTO_ZOOM:
                if (data != null) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        if (DocumentsContract.isDocumentUri(SelectPicPopupWindow.this, data.getData())) {
                            String imagePath = null;
                            String wholeID = DocumentsContract.getDocumentId(data
                                    .getData());
                            String id = wholeID.split(":")[1];
                            String[] column = {MediaColumns.DATA};
                            String sel = BaseColumns._ID + "=?";
                            Cursor cursor = SelectPicPopupWindow.this
                                    .getContentResolver()
                                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                            column, sel, new String[]{id}, null);
                            int columnIndex = cursor.getColumnIndex(column[0]);
                            if (cursor.moveToFirst()) {
                                imagePath = cursor.getString(columnIndex);
                            }
                            cursor.close();
                            Uri newUri = Uri.parse("file:///" + imagePath); // 将绝对路径转换为URL
                            startPhotoZoom(newUri, PixelUtil.dpToPx(this, 60));
                        } else {
                            startPhotoZoom(data.getData(), PixelUtil.dpToPx(this, 60));

                        }
                    } else {
                        startPhotoZoom(data.getData(), PixelUtil.dpToPx(this, 60));
                    }

                }
                break;
            case PHOTO_RESOULT:
                if (data != null)
                    setPicToView(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri, int size) {
        corpFile = new File(getExternalCacheDir().getPath() + File.separator + "img_name.png");
        try {
            corpFile.createNewFile();
            corpUri = Uri.fromFile(corpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, corpUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    // 保存裁剪之后的图片数据
    private void setPicToView(Intent data) {
        // 选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用
        intent.setData(corpUri);
        setResult(RESULT_OK, intent);
        finish();

    }

    // 获得照片的文件名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }

    private int getPreviewDegree(Activity activity) {
        // 获得手机的方向
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degree = 0;
        // 根据手机的方向计算相机预览画面应该选择的角度
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

}
