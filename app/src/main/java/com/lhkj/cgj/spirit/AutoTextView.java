package com.lhkj.cgj.spirit;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;


public class AutoTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;
    //mInUp,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    //mInDown,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mInDown;
    private Rotate3dAnimation mOutDown;

    private Timer timer;
    public AutoTextView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        //mHeight = a.getDimension(R.styleable.auto3d_textSize, 36);
        mContext = context;
        timer=new Timer();
        init();
        auto();
    }

    private void init() {
        // TODO Auto-generated method stub
        setFactory(this);
        mInUp = createAnim(-90, 0, true, true);
        mOutUp = createAnim(0, 90, false, true);
        mInDown = createAnim(90, 0, true, false);
        mOutDown = createAnim(0, -90, false, false);
        //TextSwitcher主要用于文件切换，比如 从文字A 切换到 文字 B，
        //setInAnimation()后，A将执行inAnimation，
        //setOutAnimation()后，B将执行OutAnimation
        setInAnimation(mInUp);
        setOutAnimation(mOutUp);
    }

    private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        //动画持续时间
        rotation.setDuration(300);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    public void setData() {

    }

    //这里返回的TextView，就是我们看到的View
    @Override
    public View makeView() {
        // TODO Auto-generated method stub
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.LEFT);
        t.setTextSize(15);
        t.setMaxLines(2);
        t.setPadding(0, 0, 0, 0);
        //设置文字颜色
        t.setTextColor(Color.GRAY);
        return t;
    }

    //定义动作，向下滚动翻页
    private void previous() {
        if (getInAnimation() != mInDown) {
            setInAnimation(mInDown);
        }
        if (getOutAnimation() != mOutDown) {
            setOutAnimation(mOutDown);
        }
    }

    //定义动作，向上滚动翻页
    private void next() {
        if (getInAnimation() != mInUp) {
            setInAnimation(mInUp);
        }
        if (getOutAnimation() != mOutUp) {
            setOutAnimation(mOutUp);
        }
    }

    private void auto(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                next();
            }
        },1500);
    }

    class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }
}
