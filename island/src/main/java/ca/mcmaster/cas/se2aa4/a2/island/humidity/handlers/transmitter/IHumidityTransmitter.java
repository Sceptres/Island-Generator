package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.transmitter;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public interface IHumidityTransmitter {
    /**
     *
     * @param humidity The humidity element that is giving off humidity
     * @return The humidity given off by the element
     */
    double giveHumidity(IHumidity humidity);
}
