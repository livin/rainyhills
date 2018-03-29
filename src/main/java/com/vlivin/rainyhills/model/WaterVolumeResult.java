package com.vlivin.rainyhills.model;

/**
 * Represents the results of water volume
 * remained after rain calculation.
 *
 * @author Vladimir Livin
 */
public class WaterVolumeResult {
    private int total;
    
    private int[] remainedAt;

    public WaterVolumeResult(int total) {
        this(total, null);
    }

    public WaterVolumeResult(int total, int[] remainedAt) {
        this.total = total;
        this.remainedAt = remainedAt;
    }

    public int getTotal() {
        return total;
    }

    public int[] getRemainedAt() {
        return remainedAt;
    }
}
