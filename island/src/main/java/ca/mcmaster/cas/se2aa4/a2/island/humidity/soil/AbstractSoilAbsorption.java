package ca.mcmaster.cas.se2aa4.a2.island.humidity.soil;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.HumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IHumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;

public abstract class AbstractSoilAbsorption implements SoilAbsorptionProfile{

    private final IHumidityReceiver humidityReceiver;

    private final IHumidityTransmitter humidityTransmitter;

    protected AbstractSoilAbsorption(IHumidityReceiver humidityReceiver, IHumidityTransmitter humidityTransmitter){
        this.humidityReceiver = humidityReceiver;
        this.humidityTransmitter = humidityTransmitter;
    }

    @Override
    public IHumidityReceiver getHumidityReceiver() {
        return this.humidityReceiver;
    }

    @Override
    public IHumidityTransmitter getHumidityTransmitter() {
        return this.humidityTransmitter;
    }
}
