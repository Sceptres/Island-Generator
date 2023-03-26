package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.biome.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Land;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.geography.River;
import ca.mcmaster.cas.se2aa4.a2.island.geography.generator.generators.RiverGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;

import java.util.List;
import java.util.Random;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    private final Land land;
    private final Ocean ocean;
    private final Random rand;
    private final long seed;
    private final int numLakes;
    private final int numRivers;
    private final int numAquifers;
    private final Shape shape;
    private final Biome biome;
    private final AltimeterProfile altimeterProfile;
    protected final IslandMesh mesh;

    protected AbstractIslandGenerator(
            IslandMesh mesh,
            Shape shape,
            AltimeterProfile altimeterProfile,
            Biome biome,
            long seed,
            int numLakes,
            int numAquifers,
            int numRivers
    ) {
        this.land = new Land();
        this.ocean = new Ocean();
        this.seed = seed;
        this.rand = new Random(seed);
        this.mesh = mesh;
        this.shape = shape;
        this.biome = biome;
        this.altimeterProfile = altimeterProfile;
        this.numLakes = numLakes;
        this.numRivers = numRivers;
        this.numAquifers = numAquifers;
    }

    @Override
    public long getSeed() {
        return this.seed;
    }

    @Override
    public final void generate() {
        List<Tile> tiles = this.mesh.getTiles();

        this.generateIsland(tiles, this.ocean, this.land, this.shape);
        this.generateElevation(this.rand, this.land);
        this.generateAquifers(this.rand, this.land, this.numAquifers);
        this.generateLakes(this.rand, this.land, this.numLakes);
        this.generateRivers(this.rand, this.land, this.ocean, this.numRivers);
        this.generateHumidity(this.land);
        this.biomeHandling(this.land, this.biome);
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
    protected abstract void generateLakes(Random rand, Land land, int numLakes);

    /**
     *
     * @param land The {@link Land} of the island
     * @param ocean The {@link Ocean} of the island
     * @param numRivers The number of {@link River} to generate
     */
    private void generateRivers(Random rand, Land land, Ocean ocean, int numRivers) {
        RiverGenerator riverGenerator = new RiverGenerator(land, ocean);
        List<River> rivers = riverGenerator.generate(rand, numRivers);
        rivers.forEach(land::addRiver);
    }

    /**
     * Sets the elevation of land tiles. Ocean tiles are left at 0.
     */
    private void generateElevation(Random rand, Land land) {
        land.getTiles().forEach(t -> {
            double elevation = this.altimeterProfile.generateElevation(rand, this.shape, t);
            t.setElevation(elevation);
        });
    }

    /**
     *
     * @param land The {@link Land} to generate aquifers for
     * @param numAquifers The number of aquifers to generate
     */
    private void generateAquifers(Random rand, Land land, int numAquifers) {
        List<Tile> tiles = land.getTiles();

        for(int i=0; i < numAquifers; i++) {
            int randIdx = rand.nextInt(0, tiles.size());
            Tile aquifer_tile = tiles.get(randIdx);
            aquifer_tile.putAquifer();

            tiles = tiles.stream().filter(t -> !t.hasAquifer()).toList();
        }
    }

    /**
     *
     * @param land The {@link Land} to generate humidity for
     */
    private void generateHumidity(Land land){
        // Add humidity from the lakes
        land.getLakes().forEach(lake -> {
            lake.getNeighbors().forEach(lake::giveHumidity);
        });

        // Add humidity from the rivers
        land.getRivers().forEach(r -> {
            r.getNeighbors().forEach(r::giveHumidity);
        });

        // Soil absorption (tiles spread humidity amongst themselves)
        land.getTiles().stream().filter(t -> t.getType().getGroup() != TileGroup.WATER).forEach(tile -> {
            tile.getNeighbors().forEach(tile::giveHumidity);
        });

        // Add (its really remove) humidity from ocean
        ocean.getNeighbors().forEach(ocean::giveHumidity);
    }

    /**
     *
     * @param land The {@link Land} that the {@link Biome} will act upon
     * @param biome The {@link Biome} of the island
     */
    private void biomeHandling(Land land, Biome biome) {
        land.getTiles().forEach(biome::takeTile);
    }
}
