package ca.mcmaster.cas.se2aa4.a2.island.geography.generator;

import ca.mcmaster.cas.se2aa4.a2.island.geography.TiledGeography;

import java.util.List;

public interface GeographyGenerator<T extends TiledGeography> {
    /**
     *
     * @param num The number of the {@link TiledGeography} to
     * @return The list of generated {@link TiledGeography}
     */
    List<T> generate(int num);
}