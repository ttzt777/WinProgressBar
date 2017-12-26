package org.bear3.lib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tt on 2017/12/25.
 */

public class Win10LoadingCircle extends View{
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;

    private ValueAnimator mAnimator;

    private int mWidth;
    private int mHeight;

    private float mTimeFraction0;
    private float mTimeFraction1;
    private float mTimeFraction2;
    private float mTimeFraction3;
    private float mTimeFraction4;

    public Win10LoadingCircle(Context context) {
        this(context, null);
    }

    public Win10LoadingCircle(Context context, @Nullable AttributeSet attrs) {
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

        Path path = new Path();
        RectF rectF = new RectF(-100, -100, 100, 100);
        path.addArc(rectF, 90, 359.9999f);
        mPathMeasure = new PathMeasure(path, false);

        mAnimator = ValueAnimator.ofFloat(0, 1).setDuration(3000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();

                int num = (int) (fraction / 0.05);
                switch (num) {
                    default:
                    case 4:
                        mTimeFraction4 = getTimeFraction(fraction, 0.2f);
                    case 3:
                        mTimeFraction3 = getTimeFraction(fraction, 0.15f);
                    case 2:
                        mTimeFraction2 = getTimeFraction(fraction, 0.10f);
                    case 1:
                        mTimeFraction1 = getTimeFraction(fraction, 0.05f);
                    case 0:
                        mTimeFraction0 = getTimeFraction(fraction, 0f);
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);

        if (mTimeFraction0 <= 0.8 && mTimeFraction0 > 0f) {
            if (mTimeFraction0 >= 0.4) mTimeFraction0 = mTimeFraction0 - 0.4f;
            float progress = mPathMeasure.getLength() * mTimeFraction0 / 0.4f;
            mPathMeasure.getSegment(progress, progress + 1, mPath, true);
        }

        if (mTimeFraction1 <= 0.8 && mTimeFraction1 > 0f) {
            if (mTimeFraction1 >= 0.4) mTimeFraction1 = mTimeFraction1 - 0.4f;
            float progress = mPathMeasure.getLength() * mTimeFraction1 / 0.4f;
            mPathMeasure.getSegment(progress, progress + 1, mPath, true);
        }

        if (mTimeFraction2 <= 0.8 && mTimeFraction2 > 0f) {
            if (mTimeFraction2 >= 0.4) mTimeFraction2 = mTimeFraction2 - 0.4f;
            float progress = mPathMeasure.getLength() * mTimeFraction2 / 0.4f;
            mPathMeasure.getSegment(progress, progress + 1, mPath, true);
        }

        if (mTimeFraction3 <= 0.8 && mTimeFraction3 > 0f) {
            if (mTimeFraction3 >= 0.4) mTimeFraction3 = mTimeFraction3 - 0.4f;
            float progress = mPathMeasure.getLength() * mTimeFraction3 / 0.4f;
            mPathMeasure.getSegment(progress, progress + 1, mPath, true);
        }

        if (mTimeFraction4 <= 0.8 && mTimeFraction4 > 0f) {
            if (mTimeFraction4 >= 0.4) mTimeFraction4 = mTimeFraction4 - 0.4f;
            float progress = mPathMeasure.getLength() * mTimeFraction4 / 0.4f;
            mPathMeasure.getSegment(progress, progress + 1, mPath, true);
        }

        canvas.drawPath(mPath, mPaint);
        mPath.rewind();
    }

    private float getTimeFraction(float fraction, float offset) {
        float offsetFraction = fraction - offset;
        if (offsetFraction > 0.8f) {
            return offsetFraction;
        } else if (offsetFraction > 0.6f) {
            return 0.2f * (((offsetFraction - 0.6f) / 0.2f) * ((offsetFraction - 0.6f) / 0.2f)) + 0.6f;
        } else if (offsetFraction > 0.2f) {
            return (float) (0.4f * ((Math.cos(((offsetFraction - 0.2) / 0.4 + 1) * Math.PI) / 2) + 0.5f) + 0.2f);
        } else if (offsetFraction > 0f) {
            return (float) (0.2 * Math.sin(offsetFraction / 0.2 * Math.PI / 2));
        } else {
            return 0f;
        }
    }

}
