package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IHumidityReceiver;

public class NormalHumidityHandler implements HumidityHandler {
    @Override
    public void handleHumidity(IHumidity h, IHumidityReceiver receiver, float humidity) {
        receiver.receiveHumidity(h, humidity);
    }
}
