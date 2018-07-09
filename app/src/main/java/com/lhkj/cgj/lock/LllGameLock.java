package com.lhkj.cgj.lock;

import android.content.Context;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityLllgameBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.BannerResponse;
import com.lhkj.cgj.ui.mine.LllGameAdapter;
import com.lhkj.cgj.utils.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 * 积分游戏
 */

public class LllGameLock {
    public LllGameAdapter lllGameAdapter;
    private ArrayList<LllGameItem> lllGameData;
    private ArrayList networkImages;
    private ActivityLllgameBinding lllgameBinding;
    public LllGameLock(Context context,ActivityLllgameBinding lllgameBinding){
        lllGameData=new ArrayList();
        lllGameData.add(new LllGameItem("","坦克大战",""));
        lllGameAdapter=new LllGameAdapter(context,lllGameData, R.layout.lllgame_item, BR.lllGameItem);
        networkImages=new ArrayList();
        this.lllgameBinding=lllgameBinding;
        getData();
    }
    private void getData(){
        HashMap hashMap=new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(BannerResponse.class, RunTime.operation.getMine().GAME_BANNER, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((BannerResponse) data).info != null) {
                    for (BannerResponse.Info info : ((BannerResponse) data).info) {
                        networkImages.add(info.img_url);
                    }
                    lllgameBinding.lllgameIms.startTurning(2500);
                    lllgameBinding.lllgameIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, networkImages);
//                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
                }
            }
        });

        RunTime.operation.tryPostRefresh(null, RunTime.operation.getMine().GAME_LIST, null, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {

            }
        });
    }
    public class LllGameItem{
        public LllGameItem(String gameUrl,String gameName,String gameimUrl){
            this.gameimUrl=gameimUrl;
            this.gameName=gameName;
            this.gameUrl=gameUrl;
        }
       public String gameUrl;
        public String gameName;
        public String gameimUrl;
    }
}
