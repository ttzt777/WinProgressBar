package org.bear3.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tt on 2017/12/25.
 */

public class Win10LoadingHorizontal extends View{
    private static final String TAG = "Win10LoadingHorizontal";

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;

    private ValueAnimator mAnimator;

    private float mTimeFraction0;
    private float mTimeFraction1;
    private float mTimeFraction2;
    private float mTimeFraction3;
    private float mTimeFraction4;

    public Win10LoadingHorizontal(Context context) {
        this(context, null);
    }

    public Win10LoadingHorizontal(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(0xFF709900);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();

        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(3000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        mAnimator.setInterpolator(new Win10LoadingTimeInterpolator(0));
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();

                int num = (int) (fraction / 0.05);
                switch (num) {
                    default:
                    case 4:
                        mTimeFraction4 = getBezierTimeFraction(fraction, 0.2f);
                    case 3:
                        mTimeFraction3 = getBezierTimeFraction(fraction, 0.15f);
                    case 2:
                        mTimeFraction2 = getBezierTimeFraction(fraction, 0.10f);
                    case 1:
                        mTimeFraction1 = getBezierTimeFraction(fraction, 0.05f);
                    case 0:
                        mTimeFraction0 = getBezierTimeFraction(fraction, 0f);
                        break;
                }

                invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                mTimeFraction0 = mTimeFraction1 = mTimeFraction2 = mTimeFraction3 = mTimeFraction4 = 0;
            }
        });
        mAnimator.start();
    }

    private float getBezierTimeFraction(float fraction, float offset) {
        float offsetFraction = fraction - offset;
        if (offsetFraction > 0.8f) {
            return offsetFraction;
        } else if (offsetFraction < 0f) {
            return 0f;
        }

        return Util.getBezierFloat(offsetFraction / 0.8f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mPath.rewind();
        mPath.lineTo(w, 0);
        mPathMeasure = new PathMeasure(mPath, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(0, mHeight / 2);

        if (mPathMeasure == null) {
            return;
        }

        if (mTimeFraction0 <= 0.8 && mTimeFraction0 > 0f) {
            canvas.drawPoint(mPathMeasure.getLength() * mTimeFraction0 / 0.8f, 0, mPaint);
        }

        if (mTimeFraction1 <= 0.8 && mTimeFraction1 > 0f) {
            canvas.drawPoint(mPathMeasure.getLength() * mTimeFraction1 / 0.8f, 0, mPaint);
        }

        if (mTimeFraction2 <= 0.8 && mTimeFraction2 > 0f) {
            canvas.drawPoint(mPathMeasure.getLength() * mTimeFraction2 / 0.8f, 0, mPaint);
        }

        if (mTimeFraction3 <= 0.8 && mTimeFraction3 > 0f) {
            canvas.drawPoint(mPathMeasure.getLength() * mTimeFraction3 / 0.8f, 0, mPaint);
        }

        if (mTimeFraction4 <= 0.8 && mTimeFraction4 > 0f) {
            canvas.drawPoint(mPathMeasure.getLength() * mTimeFraction4 / 0.8f, 0, mPaint);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
    }
}
