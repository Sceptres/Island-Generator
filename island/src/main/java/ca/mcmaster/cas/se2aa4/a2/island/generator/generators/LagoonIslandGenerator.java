package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.LagoonAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.util.List;

public class LagoonIslandGenerator extends AbstractIslandGenerator {
    public LagoonIslandGenerator(Mesh mesh, Shape shape,int numAquifers) {
        super(mesh, shape, new LagoonAltimeter(), 1, numAquifers);
    }

    @Override
    protected void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape) {
        List<Tile> oceanTiles = tiles.stream().filter(t -> !shape.contains(t)).toList();
        ocean.addAllTiles(oceanTiles);

        List<Tile> landTiles = tiles.stream().filter(shape::contains).toList();
        land.addAllTiles(landTiles);
        land.getTiles().stream().filter(t ->
                t.getNeighbors().stream().anyMatch(t1 -> t1.getType() == TileType.OCEAN_TILE)
        ).forEach(t -> t.setType(TileType.BEACH_TILE));
    }

    @Override
    protected void generateLakes(Land land, int numLakes) {
        List<Tile> mainLandTiles = land.getTiles();
        List<Tile> lakeTiles = mainLandTiles.stream().filter(t -> t.getElevation() < 0.2).toList();
        Lake lake = new Lake(lakeTiles.get(0));
        lake.addAllTiles(lakeTiles.subList(0, lakeTiles.size()));
        lake.setElevation(0.2);
        land.addLake(lake);

        lake.getNeighbors().forEach(t -> t.setType(TileType.BEACH_TILE));
    }
}