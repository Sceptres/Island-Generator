package ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;

public class HumidityReceiver implements IReceiver {
    private final float ratio;

    public HumidityReceiver(float ratio){
        this.ratio = ratio;
    }


    @Override
    public void receiveHumidity(IHumidity h, float humidity) {
       h.setHumidity(this.ratio*humidity);
    }
}
