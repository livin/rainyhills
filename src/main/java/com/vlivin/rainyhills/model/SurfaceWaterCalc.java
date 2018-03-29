package com.vlivin.rainyhills.model;

import javax.ejb.Stateless;

import static java.lang.Math.max;

/**
 * Calculates the volume of water which remained after the rain, in units.
 *
 * Algorithm performance:
 * Speed: O(n)
 * Memory: O(1)
 *
 * Algorithm is based on hypothesis that at each hill the potential water remained
 * is min of the tallest hill to the left and to the right from current hill.
 *
 * It performs in a single pass of the surface and scans it from left and right edges moving
 * to the middle and at each step calculates water volume for current hill.
 *
 * The extra water remainedAt array is provided only if saveRemainedAt
 * option is used.
 *
 * @author Vladimir Livin
 */
@Stateless
public class SurfaceWaterCalc {
    private boolean saveRemainedAt;

    /**
     * Creates new SurfaceWaterCalc.
     *
     * @param saveRemainedAt true if extra details about water remained at particular hill.
     */
    public SurfaceWaterCalc(boolean saveRemainedAt) {
        this.saveRemainedAt = saveRemainedAt;
    }

    public SurfaceWaterCalc() {
        this(true);
    }

    /**
     * Calculates water volume.
     *
     * @return water volume remained after the rain, units.
     */
    public WaterVolumeResult calculateWaterVolume(Surface surface) {
        if (surface.getHeights().length < 3)
            return new WaterVolumeResult(0, new int[surface.getHeights().length]);

        int a = 0, b = surface.getHeights().length - 1,
                maxLeft = 0, maxRight = 0,
                totalWater = 0;

        int[] waterRemainedAt = new int[0];
        if (saveRemainedAt)
            waterRemainedAt = new int[surface.getHeights().length];

        int remainedAt;
        int hill;

        // move from both edges to the middle
        while (a <= b) {
            maxLeft = max(maxLeft, surface.getHeights()[a]);
            maxRight = max(maxRight, surface.getHeights()[b]);
            if (maxLeft <= maxRight) {
                remainedAt = maxLeft - surface.getHeights()[a];
                hill = a;
                a++;
            } else {
                remainedAt = maxRight - surface.getHeights()[b];
                hill = b;
                b--;
            }

            totalWater += remainedAt;

            if (saveRemainedAt)
                waterRemainedAt[hill] = remainedAt;
        }
        return new WaterVolumeResult(totalWater, waterRemainedAt);
    }
}
