package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IHumidityReceiver;

public interface HumidityHandler {
    void handleHumidity(IHumidity h, IHumidityReceiver receiver, float humidity);
}
