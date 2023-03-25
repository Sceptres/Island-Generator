package ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.GeographyGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LakeGenerator implements GeographyGenerator<Lake> {
    private final Land land;

    public LakeGenerator(Land land) {
        this.land = land;
    }

    @Override
    public List<Lake> generate(int num) {
        Random random = new Random();

        List<Tile> tiles = land.getTiles();
        List<Lake> generatedLakes = new ArrayList<>();

        for(int i=0; i < num; i++) {
            int randIdx = random.nextInt(0, tiles.size());
            Tile lakeCenter = tiles.get(randIdx);

            Lake lake = this.generateLake(lakeCenter, random);

            double minLand = lake.getNeighbors().stream().mapToDouble(Tile::getElevation).min().getAsDouble();
            lake.setElevation(minLand);

            generatedLakes.add(lake);

            tiles = tiles.stream().filter(
                    t -> t.getNeighbors().stream().noneMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER)
            ).toList();
        }

        return generatedLakes;
    }

    /**
     *
     * @param lakeBeginning The beginning {@link Tile} that the lake starts at
     * @param random The {@link Random} instance used to generate the lakes path
     * @return The generated {@link Lake}
     */
    private Lake generateLake(Tile lakeBeginning, Random random) {
        Lake lake = new Lake(lakeBeginning);
        this.generateLakePath(random, lake);
        return lake;
    }

    /**
     *
     * @param random The {@link Random} instance to determine lake path
     * @param lake The {@link Lake} to generate
     */
    private void generateLakePath(Random random, Lake lake) {
        Tile next = this.getNextTile(lake, random);

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

    /**
     *
     * @param lake The {@link Lake} being currently generated
     * @param random The {@link Random} instance being used to generate the lake
     * @return The generated {@link Lake}
     */
    private Tile getNextTile(Lake lake, Random random) {
        List<Tile> tiles = lake.getTiles();
        Tile tile = tiles.get(tiles.size()-1);
        List<Tile> neighbors = tile.getNeighbors();
        return neighbors.get(random.nextInt(neighbors.size()));
    }
}
