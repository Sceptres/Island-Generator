package ca.mcmaster.cas.se2aa4.a2.island.geography.generator;

import ca.mcmaster.cas.se2aa4.a2.island.geography.TiledGeography;

import java.util.List;
import java.util.Random;

public interface GeographyGenerator<T extends TiledGeography> {
    /**
     *
     * @param random The {@link Random} instance to use in the generation of the geography
     * @param num The number of the {@link TiledGeography} to generate
     * @return The list of generated {@link TiledGeography}
     */
    List<T> generate(Random random, int num);
}