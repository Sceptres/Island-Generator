package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.River;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators.RiverGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;

import java.util.List;
import java.util.Random;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    private final Land land;
    private final Ocean ocean;
    private final int numLakes;
    private final int numRivers;
    private final int numAquifers;
    private final Shape shape;
    private final AltimeterProfile altimeterProfile;
    protected final IslandMesh mesh;

    protected AbstractIslandGenerator(
            IslandMesh mesh,
            Shape shape,
            AltimeterProfile altimeterProfile,
            int numLakes,
            int numAquifers,
            int numRivers
    ) {
        this.land = new Land();
        this.ocean = new Ocean();
        this.mesh = mesh;
        this.shape = shape;
        this.altimeterProfile = altimeterProfile;
        this.numLakes = numLakes;
        this.numRivers = numRivers;
        this.numAquifers = numAquifers;
    }

    @Override
    public final void generate() {
        List<Tile> tiles = this.mesh.getTiles();

        this.generateIsland(tiles, this.ocean, this.land, this.shape);
        this.generateElevation(this.land);
        this.generateAquifers(this.land, this.numAquifers);
        this.generateLakes(this.land, this.numLakes);
        this.generateRivers(this.land, this.ocean, this.numRivers);

    }

    /**
     * Generates the land tiles and ocean tiles
     *
     * @param tiles The {@link List} of tiles given by the mesh
     */
    protected abstract void generateIsland(List<Tile> tiles, Ocean ocean, Land land, Shape shape);

    /**
     * Generates lakes in the island
     *
     * @param land All the land {@link Tile} since lakes can only be in land
     */
    protected abstract void generateLakes(Land land, int numLakes);

    /**
     *
     * @param land The {@link Land} of the island
     * @param ocean The {@link Ocean} of the island
     * @param numRivers The number of {@link River} to generate
     */
    private void generateRivers(Land land, Ocean ocean, int numRivers) {
        RiverGenerator riverGenerator = new RiverGenerator(land, ocean);
        List<River> rivers = riverGenerator.generate(numRivers);
        rivers.forEach(land::addRiver);
    }

    /**
     * Sets the elevation of land tiles. Ocean tiles are left at 0.
     */
    private void generateElevation(Land land) {
        land.getTiles().forEach(t -> {
            double elevation = this.altimeterProfile.generateElevation(this.shape, t);
            t.setElevation(elevation);
        });
    }

    private void generateAquifers(Land land, int numAquifers) {
        Random random = new Random();

        List<Tile> tiles = land.getTiles();

        for(int i=0; i < numAquifers; i++) {
            int randIdx = random.nextInt(0, tiles.size());
            Tile aquifer_tile = tiles.get(randIdx);
            aquifer_tile.putAquifer();

            tiles = tiles.stream().filter(t -> !t.hasAquifer()).toList();
        }
    }
}