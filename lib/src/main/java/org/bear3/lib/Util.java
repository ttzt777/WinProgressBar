package org.bear3.lib;

/**
 * Created by tt on 2017/12/25.
 */

public class Util {
    public static float getBezierFloat(float fraction) {
        return 0 * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3 * 0.6f * fraction * (1 - fraction) * (1 - fraction)
                + 3 * 0.2f * fraction * fraction * (1 - fraction) + 0.8f * fraction * fraction * fraction;
    }
}
