package com.vlivin.rainyhills.controller;

import com.vlivin.rainyhills.model.Surface;
import com.vlivin.rainyhills.model.SurfaceWaterCalc;
import com.vlivin.rainyhills.model.WaterVolumeResult;
import org.primefaces.extensions.component.gchart.model.GChartModel;
import org.primefaces.extensions.component.gchart.model.GChartModelBuilder;
import org.primefaces.extensions.component.gchart.model.GChartType;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Handles Surface input and calculation.
 * In addition it builds beautiful google chart model for
 * given surface profile.
 *
 * @author Vladimir Livin
 */
@Named
@RequestScoped
public class SurfaceController {
    private static final String GRAY_BUILDING = "#808080";
    private static final String BLUE_WATER = "#4fadea";

    @Inject
    private SurfaceWaterCalc surfaceWaterCalc;

    private String surfaceProfile;
    private Surface surface;

    private WaterVolumeResult waterVolumeResult;

    public String getSurfaceProfile() {
        return surfaceProfile;
    }

    public void setSurfaceProfile(String surfaceProfile) {
        this.surfaceProfile = surfaceProfile;
    }

    public void validateSurfaceProfile(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String surfaceProfile = (String) value;
        try {
            new Surface(surfaceProfile);
        } catch (IllegalArgumentException e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void calculate() {
        setSurface(new Surface(getSurfaceProfile()));
        setWaterVolumeResult(surfaceWaterCalc.calculateWaterVolume(getSurface()));
    }

    public GChartModel getSurfaceGoogleChart() {
        GChartModelBuilder chart = new GChartModelBuilder()
                .setChartType(GChartType.COLUMN);

        HashMap<String, Object> barOptions = new HashMap<>();
        barOptions.put("groupWidth", "100%");
        chart.addOption("bar", barOptions);

        chart.addOption("colors", Arrays.asList(GRAY_BUILDING, BLUE_WATER));
        chart.addOption("isStacked", true);

        if (getSurface() != null) {
            chart.addColumns("Hill", "Height", "Water");
            int[] heights = getSurface().getHeights();
            int[] remainedWater = getWaterVolumeResult().getRemainedAt();

            for (int i = 0; i < heights.length; i++) {
                chart.addRow("" + (i+1), heights[i], remainedWater[i]);
            }
        }

        return chart.build();
    }

    public void setWaterVolumeResult(WaterVolumeResult waterVolumeResult) {
        this.waterVolumeResult = waterVolumeResult;
    }

    public WaterVolumeResult getWaterVolumeResult() {
        return waterVolumeResult;
    }
}
