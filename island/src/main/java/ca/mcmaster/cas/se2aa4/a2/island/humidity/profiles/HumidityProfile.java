package ca.mcmaster.cas.se2aa4.a2.island.humidity.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public class HumidityProfile implements IHumidity {

    private double humidity;
    @Override
    public double getHumidity() {
        return 0;
    }

    @Override
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

}
