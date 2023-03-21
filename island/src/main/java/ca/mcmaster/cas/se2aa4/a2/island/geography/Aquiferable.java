package ca.mcmaster.cas.se2aa4.a2.island.geography;

public interface Aquiferable {
    /**
     *
     * @return true if the tile/structure has an aquifer
     */
    boolean hasAquifer();

    /**
     * sets aquifer boolean to true
     */
    void putAquifer();

    /**
     * sets aquifer to false
     */
    void removeAquifer();



}
