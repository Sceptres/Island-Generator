package ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.HumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.HumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.AbstractSoilAbsorption;

public class DrySoilAbsorption extends AbstractSoilAbsorption {
    public DrySoilAbsorption() {
        super(new HumidityReceiver(0.35f), new HumidityTransmitter(0.85f));
    }
}
