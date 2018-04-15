package com.vlivin.rainyhills.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies Surface behavior.
 *
 * @author Vladimir Livin
 */
class SurfaceTest {
    @Test
    void create() {
        int[] sample = new int[]{1, 2, 3};
        Surface surface = new Surface(sample);
        assertEquals(sample, surface.getHeights());
    }
}
