package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.LagoonAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;
import java.util.Random;

public class LagoonIslandGenerator extends AbstractIslandGenerator {
    public LagoonIslandGenerator(IslandMesh mesh, Shape shape, long seed, int numAquifers, int numRivers) {
        super(mesh, shape, new LagoonAltimeter(), seed, 1, numAquifers, numRivers);
    }

    @Override
    protected void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape) {
        List<Tile> oceanTiles = tiles.stream().filter(t -> !shape.contains(t)).toList();
        ocean.addAllTiles(oceanTiles);

        List<Tile> landTiles = tiles.stream().filter(shape::contains).toList();
        land.addAllTiles(landTiles);
    }

    @Override
    protected void generateLakes(Random random, Land land, int numLakes) {
        List<Tile> mainLandTiles = land.getTiles();
        List<Tile> lakeTiles = mainLandTiles.stream().filter(t -> t.getElevation() < 0.2).toList();
        Lake lake = new Lake(lakeTiles.get(0));
        lake.addAllTiles(lakeTiles.subList(1, lakeTiles.size()));
        lake.setElevation(0.2);
        land.addLake(lake);
    }
}