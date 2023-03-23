package ca.mcmaster.cas.se2aa4.a2.island.humidity;

public interface IHumidity {
    /**
     *
     * @return The humidity belonging to this element
     */
    double getHumidity();

    /**
     *
     * @param humidity The humidity to set this element to
     */
    void setHumidity(double humidity);

    /**
     *
     * @param h
     */
    void giveHumidity(IHumidity h);


}
