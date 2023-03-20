package ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;

public class ElevationProfile implements IElevation {
    private double elevation;

    public ElevationProfile() {
        this.elevation = 0;
    }

    @Override
    public double getElevation() {
        return this.elevation;
    }

    @Override
    public void setElevation(double elevation) {
        if(elevation > 100)
            this.setElevation(100);
        else if(elevation < 0)
            this.setElevation(0);
        else
            this.elevation = elevation;
    }
}
