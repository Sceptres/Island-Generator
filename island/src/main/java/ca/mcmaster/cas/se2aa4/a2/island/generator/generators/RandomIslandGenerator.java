package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.util.List;
import java.util.Random;

public class RandomIslandGenerator extends AbstractIslandGenerator {
    public RandomIslandGenerator(Mesh mesh, Shape shape, int numLakes) {
        super(mesh, shape, numLakes);
    }

    @Override
    protected void generateIsland(List<Tile> tiles, Shape shape) {
        tiles.stream().filter(shape::contains).forEach(tile -> tile.setType(TileType.LAND_TILE));
        tiles.stream().filter(tile -> !shape.contains(tile)).forEach(tile -> tile.setType(TileType.OCEAN_TILE));
    }

    @Override
    protected void generateLakes(List<Tile> mainLandTiles, int numLakes) {
        Random random = new Random();

        List<Tile> tiles = mainLandTiles;

        for(int i=0; i < numLakes; i++) {
            int randIdx = random.nextInt(0, tiles.size());
            Tile lakeCenter = tiles.get(randIdx);
            lakeCenter.setType(TileType.LAND_WATER_TILE);

            List<Tile> neighbors = lakeCenter.getNeighbors();
            Tile neighbor = neighbors.get(random. nextInt(neighbors.size()));
            this.generateLakePath(random, neighbor);

            tiles = mainLandTiles.stream().filter(
                    t -> t.getNeighbors().stream().noneMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER)
            ).toList();
        }
    }

    /**
     *
     * @param random The random instance used to generate lake path
     * @param start The tile to start generating this lake at
     */
    private void generateLakePath(Random random, Tile start) {
        double num = random.nextDouble(0, 1);
        boolean shouldStop = num > 0.96 || start.getNeighbors().stream().filter(t -> t != start).anyMatch(t ->
                t.getNeighbors().stream().anyMatch(t1 ->
                        t1.getType() == TileType.OCEAN_TILE
                )
        );


        if(!shouldStop) {
            start.setType(TileType.LAND_WATER_TILE);

            List<Tile> neighbors = start.getNeighbors();
            Tile neighbor = neighbors.get(random.nextInt(neighbors.size()));
            generateLakePath(random, neighbor);
        }
    }



}