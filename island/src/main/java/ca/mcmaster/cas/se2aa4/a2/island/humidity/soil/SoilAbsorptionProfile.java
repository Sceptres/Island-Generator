package ca.mcmaster.cas.se2aa4.a2.island.humidity.soil;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IHumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.IHumidityTransmitter;

public interface SoilAbsorptionProfile {

    /**
     *
     * @return The {@link IHumidityReceiver} of this profile
     */
    IHumidityReceiver getHumidityReceiver();

    /**
     *
     * @return The {@link IHumidityTransmitter} of this profile
     */
    IHumidityTransmitter getHumidityTransmitter();

}
