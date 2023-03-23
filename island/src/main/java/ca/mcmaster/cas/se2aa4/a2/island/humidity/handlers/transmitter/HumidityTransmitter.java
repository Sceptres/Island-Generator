package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public class HumidityTransmitter implements IHumidityTransmitter {
    private final float ratio;

    public HumidityTransmitter(float ratio) {
        this.ratio = ratio;
    }

    @Override
    public float giveHumidity(IHumidity humidity) {
        return this.ratio * humidity.getHumidity();
    }
}
