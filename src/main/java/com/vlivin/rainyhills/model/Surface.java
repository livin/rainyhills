package com.vlivin.rainyhills.model;

import java.util.Arrays;

/**
 * Describes Surface Profile.
 *
 * Surface is constructed by providing its heights array of integers.
 * Calculates water remaining after rain with calculateWaterVolume.
 *
 * @author Vladimir Livin
 */
public class Surface {
    /**
     * Represents heights of the surface profile
     */
    private int[] heights;

    Surface(int[] heights) {
        this.heights = heights;
    }

    public Surface(String profile) {
        this(parseString(profile));
    }

    private static int[] parseString(String profile) {
        if (profile == null || profile.trim().isEmpty())
            return new int[0];

        try {
            return Arrays.stream(profile.split("[\\s,]+")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid surface format. Valid formats are : 1 2 4 or 1,2,4", e);
        }
    }

    public int[] getHeights() {
        return heights;
    }
}
