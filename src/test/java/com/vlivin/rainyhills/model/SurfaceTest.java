package com.vlivin.rainyhills.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Verifies Surface behavior.
 *
 * @author Vladimir Livin
 */
public class SurfaceTest {
    @Test
    public void create() {
        int[] sample = new int[]{1, 2, 3};
        Surface surface = new Surface(sample);
        assertEquals(sample, surface.getHeights());
    }
}
