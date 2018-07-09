package com.lhkj.cgj.ui.bbs;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityNewsBinding;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.NewsLock;


/**
 * Created by 浩琦 on 2017/6/22.
 */

public class NewsActivity extends BaseActivity {

    public static final String MAINTAIN="maintain";
    public static final String COSMETOLOGY="cosmetology";
    public static final String INSURANCE="insurance";
    public static final String DRIVE="drive";
    public static final String RESCUE="rescue";
    public static final String LLLEGAL="lllegal";
    public static final String ACTIVE="active";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        ActivityNewsBinding newsBinding= DataBindingUtil.setContentView(this,R.layout.activity_news);
        switch ((String)RunTime.getRunTime(RunTime.NEWSTYPE)){
            case MAINTAIN:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.maintain));
                break;
            case COSMETOLOGY:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.cosmetology));
                break;
            case INSURANCE:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.insurance));
                break;
            case DRIVE:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.drive));
                break;
            case RESCUE:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.rescue));
                break;
            case LLLEGAL:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.lllegal));
                break;
            case ACTIVE:
                newsBinding.include8.setAppBarLock(new AppBarLock(this,R.string.active));
                break;
        }
        newsBinding.setNewsLock(new NewsLock(this,newsBinding));
    }

}
