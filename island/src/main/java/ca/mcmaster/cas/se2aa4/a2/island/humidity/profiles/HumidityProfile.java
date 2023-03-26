package ca.mcmaster.cas.se2aa4.a2.island.humidity.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public class HumidityProfile implements IHumidity {

    private float humidity;

    public HumidityProfile() {
        this.humidity = 0;
    }


    @Override
    public float getHumidity() {
        return this.humidity;
    }

    @Override
    public void setHumidity(float humidity) {
        if(humidity > 500)
            this.humidity = 500;
        else if(humidity < 0)
            this.humidity = 0;
        else
            this.humidity = humidity;
    }

    @Override
    public void giveHumidity(IHumidity h) {
        throw new UnsupportedOperationException();
    }

}
