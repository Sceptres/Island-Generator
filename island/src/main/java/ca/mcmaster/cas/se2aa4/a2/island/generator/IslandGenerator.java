package ca.mcmaster.cas.se2aa4.a2.island.generator;

public interface IslandGenerator {

    long getSeed();

    /**
     * Generates the island terrain
     */
    void generate();
}
