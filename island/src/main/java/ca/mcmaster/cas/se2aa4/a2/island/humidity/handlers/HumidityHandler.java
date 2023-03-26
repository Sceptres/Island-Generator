package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

public interface HumidityHandler {
    void handleHumidity(IHumidity h, IReceiver receiver, float humidity);
}
