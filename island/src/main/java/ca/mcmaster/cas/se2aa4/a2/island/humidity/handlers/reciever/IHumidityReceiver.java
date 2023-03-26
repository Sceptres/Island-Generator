package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public interface IHumidityReceiver {

    void receiveHumidity(IHumidity h, float humidity);

}
