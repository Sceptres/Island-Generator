package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.List;
import java.util.Random;

public class RandomIslandGenerator extends AbstractIslandGenerator {

    public RandomIslandGenerator(
            IslandMesh mesh,
            Shape shape,
            AltimeterProfile profile,
            int numLakes,
            int numAquifers,
            int numRivers
    ) {
        super(mesh, shape, profile, numLakes, numAquifers, numRivers);
    }

    @Override
    protected void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape) {
        List<Tile> landTiles = tiles.stream().filter(shape::contains).toList();
        land.addAllTiles(landTiles);

        List<Tile> oceanTiles = tiles.stream().filter(tile -> !shape.contains(tile)).toList();
        ocean.addAllTiles(oceanTiles);
    }

    @Override
    protected void generateLakes(Land land, int numLakes) {
        Random random = new Random();

        List<Tile> tiles = land.getTiles();

        for(int i=0; i < numLakes; i++) {
            int randIdx = random.nextInt(0, tiles.size());
            Tile lakeCenter = tiles.get(randIdx);

            Lake lake = new Lake(lakeCenter);
            this.generateLakePath(random, lake);
            land.addLake(lake);

            double minLand = lake.getNeighbors().stream().mapToDouble(Tile::getElevation).min().getAsDouble();
            lake.setElevation(minLand);

            tiles = tiles.stream().filter(
                    t -> t.getNeighbors().stream().noneMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER)
            ).toList();
        }
    }

    /**
     *
     * @param random The random instance used to generate lake path
     * @param lake The lake to generate
     */
    private void generateLakePath(Random random, Lake lake) {
        Tile next = Lake.getNextTile(lake, random);

        double num = random.nextDouble(0, 1);
        boolean shouldStop = num > 0.96 || next.getNeighbors().stream().filter(t -> t != next).anyMatch(t ->
                t.getNeighbors().stream().anyMatch(t1 ->
                        t1.getType() == TileType.OCEAN_TILE
                )
        );

        if(!shouldStop) {
            lake.addTile(next);
            generateLakePath(random, lake);
        }
    }
}