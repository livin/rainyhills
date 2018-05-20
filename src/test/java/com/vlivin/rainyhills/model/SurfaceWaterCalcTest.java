package com.vlivin.rainyhills.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies Surface Water Calc behavior.
 * It checks known samples and edges cases.
 *
 * @author Vladimir Livin
 */
class SurfaceWaterCalcTest {

    private SurfaceWaterCalc calc;

    @BeforeEach
    void setUp() {
        calc = new SurfaceWaterCalc(false);
    }

    @ParameterizedTest
    @MethodSource("surfaceProfileAndCalculatedWaterVolumeFixtures")
    void calculateWaterVolume(int[] profile, int expectedVolume) {
        assertEquals(expectedVolume, calc.calculateWaterVolume(new Surface(profile)).getTotal());
    }

    private static Stream<Arguments> surfaceProfileAndCalculatedWaterVolumeFixtures() {
        return Stream.of(
                Arguments.of(new int[]{3, 2, 4, 1, 2}, 2),
                Arguments.of(new int[]{4, 1, 1, 0, 2, 3}, 8),
                Arguments.of(new int[]{5, 3, 7, 2, 6, 4, 5, 9, 1, 2}, 14),
                Arguments.of(new int[]{1, 2, 1, 1, 4, 1, 1, 2, 5, 3, 4}, 11)
        );
    }

    @Test
    void calculateWaterVolume_whenEmpty_shouldBeZero() {
        assertEquals(0, calc.calculateWaterVolume(new Surface(new int[] {})).getTotal());
    }

    @Test
    void calculateWaterVolume_when2Heights_shouldBeZero() {
        assertEquals(0, calc.calculateWaterVolume(new Surface(new int[] {4, 1})).getTotal());
    }

    @Test
    void calculateWaterVolume_when411023_shouldBeTotal8_withWaterRemained022310() {
        calc = new SurfaceWaterCalc(true);
        WaterVolumeResult waterVolumeResult = calc.calculateWaterVolume(new Surface(new int[]{4, 1, 1, 0, 2, 3}));
        assertEquals(8, waterVolumeResult.getTotal());
        assertArrayEquals(new int[] {0, 2, 2, 3, 1, 0}, waterVolumeResult.getRemainedAt());
    }

    @Test
    void calculateWaterVolume_whenBetweenTwoTallest_shouldBeBasedOnSmallest() {
        assertEquals(5, calc.calculateWaterVolume(new Surface(new int[] {5, 0, 100})).getTotal());
    }
}
