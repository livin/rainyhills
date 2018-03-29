package com.vlivin.rainyhills.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Verifies Surface Water Calc behavior.
 * It checks known samples and edges cases.
 *
 * @author Vladimir Livin
 */
public class SurfaceWaterCalcTest {

    private SurfaceWaterCalc calc;

    @Before
    public void setUp() {
        calc = new SurfaceWaterCalc(false);
    }

    @Test
    public void calculateWaterVolume_whenEmpty_shouldBeZero() {
        assertEquals(0, calc.calculateWaterVolume(new Surface(new int[] {})).getTotal());
    }

    @Test
    public void calculateWaterVolume_when2Heights_shouldBeZero() {
        assertEquals(0, calc.calculateWaterVolume(new Surface(new int[] {4, 1})).getTotal());
    }

    @Test
    public void calculateWaterVolume_when32412_shouldBe2() {
        assertEquals(2, calc.calculateWaterVolume(new Surface(new int[] {3, 2, 4, 1, 2})).getTotal());
    }

    @Test
    public void calculateWaterVolume_when411023_shouldBeTotal8_withWaterRemained022310() {
        calc = new SurfaceWaterCalc(true);
        WaterVolumeResult waterVolumeResult = calc.calculateWaterVolume(new Surface(new int[]{4, 1, 1, 0, 2, 3}));
        assertEquals(8, waterVolumeResult.getTotal());
        assertArrayEquals(new int[] {0, 2, 2, 3, 1, 0}, waterVolumeResult.getRemainedAt());
    }

    @Test
    public void calculateWaterVolume_when5372645912_shouldBe14() {
        assertEquals(14, calc.calculateWaterVolume(new Surface(new int[] {5,3,7,2,6,4,5,9,1,2})).getTotal());
    }

    @Test
    public void calculateWaterVolume_when12114112534_shouldBe11() {
        assertEquals(11, calc.calculateWaterVolume(new Surface(new int[] {1, 2, 1, 1, 4, 1, 1, 2, 5, 3, 4})).getTotal());
    }

    @Test
    public void calculateWaterVolume_whenBetweenTwoTallest_shouldBeBasedOnSmallest() {
        assertEquals(5, calc.calculateWaterVolume(new Surface(new int[] {5, 0, 100})).getTotal());
    }
}
