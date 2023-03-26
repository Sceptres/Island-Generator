package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators.LakeGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;
import java.util.Random;

public class RandomIslandGenerator extends AbstractIslandGenerator {

    public RandomIslandGenerator(
            IslandMesh mesh,
            Shape shape,
            AltimeterProfile profile,
            long seed,
            int numLakes,
            int numAquifers,
            int numRivers
    ) {
        super(mesh, shape, profile, seed, numLakes, numAquifers, numRivers);
    }

    @Override
    protected void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape) {
        List<Tile> landTiles = tiles.stream().filter(shape::contains).toList();
        land.addAllTiles(landTiles);

        List<Tile> oceanTiles = tiles.stream().filter(tile -> !shape.contains(tile)).toList();
        ocean.addAllTiles(oceanTiles);
    }

    @Override
    protected void generateLakes(Random rand, Land land, int numLakes) {
        LakeGenerator generator = new LakeGenerator(land);
        List<Lake> generatedLakes = generator.generate(rand, numLakes);
        generatedLakes.forEach(land::addLake);
    }
}