package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.HumidityHandler;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IReceiver;

public class NormalHumidityHandler implements HumidityHandler {
    @Override
    public void handleHumidity(IHumidity h, IReceiver receiver, float humidity) {
        receiver.receiveHumidity(h, humidity);
    }
}
