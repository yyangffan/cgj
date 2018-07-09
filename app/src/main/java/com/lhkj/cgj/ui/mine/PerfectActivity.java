package com.lhkj.cgj.ui.mine;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityPerfectBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.PerfectLock;
import com.lhkj.cgj.spirit.SelectPic.SelectPicPopupWindow;
import com.lhkj.cgj.utils.BitmapUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class PerfectActivity extends BaseActivity {

    private ActivityPerfectBinding perfectBinding;
    private final String IMAGE_UNSPECIFIED = "image/*";
    private final int PHOTO_ZOOM = 2; // 缩放
    private final int PHOTO_RESOULT = 3;// 结果
    public String iconPath;
    private Uri corpUri;
    public File corpFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        perfectBinding = DataBindingUtil.setContentView(this, R.layout.activity_perfect);
        perfectBinding.setPerfect(new PerfectLock(this, perfectBinding));
        perfectBinding.include.setAppBarLock(new AppBarLock(
                this, R.string.perfect, R.mipmap.icon_back, 0, true, false));
    }

    public void chengeIcon(View v) {
        Intent intent = new Intent(this, SelectPicPopupWindow.class);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            corpUri = data.getData();
            Bitmap photo = decodeUriAsBitmap(corpUri, false);
            photo = BitmapUtils.makeRoundCorner(photo);
            corpUri = Uri.fromFile(saveFile(photo, "img_name.png"));
            iconPath = getRealFilePath(this, corpUri);
            perfectBinding.headPic.setImageBitmap(photo);
        }
    }

    //
    public Bitmap decodeUriAsBitmap(Uri uri, boolean type) {
        Bitmap bitmap = null;
        try {
            if (type) {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } else {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public File saveFile(Bitmap bm, String fileName) {
        String path = getExternalCacheDir().getPath();
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + File.separator + fileName);
        BufferedOutputStream bos = null;
        try {
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }
}
