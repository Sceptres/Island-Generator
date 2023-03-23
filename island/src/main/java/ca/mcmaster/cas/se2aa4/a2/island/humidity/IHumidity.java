package ca.mcmaster.cas.se2aa4.a2.island.humidity;

public interface IHumidity {
    /**
     *
     * @return The humidity belonging to this element
     */
    float getHumidity();

    /**
     *
     * @param humidity The humidity to set this element to
     */
    void setHumidity(float humidity);

    /**
     *
     * @param h The {@link IHumidity} element to give humidity
     */
    void giveHumidity(IHumidity h);


}
