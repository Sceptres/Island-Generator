package ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.profiles;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.HumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter.HumidityTransmitter;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.AbstractSoilAbsorption;

public class WetSoilAbsorption extends AbstractSoilAbsorption {

    public WetSoilAbsorption(){
        super(new HumidityReceiver(0.85f), new HumidityTransmitter(0.2f));
    }


}
