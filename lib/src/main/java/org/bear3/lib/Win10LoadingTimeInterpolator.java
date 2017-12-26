package org.bear3.lib;

import android.animation.TimeInterpolator;

/**
 * Created by tt on 2017/12/25.
 */

public class Win10LoadingTimeInterpolator implements TimeInterpolator {
    private float mOffset;

    public Win10LoadingTimeInterpolator(float offset) {
        this.mOffset = offset;
    }

    @Override
    public float getInterpolation(float v) {
        float fraction = v - mOffset;

        if (fraction > 0.8) {
            return v;
        } else {
            fraction = fraction / 0.8f;
            return 0 * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3 * 0.6f * fraction * (1 - fraction) * (1 - fraction)
                    + 3 * 0.2f * fraction * fraction * (1 - fraction) + 0.8f * fraction * fraction * fraction;
        }
    }
}
