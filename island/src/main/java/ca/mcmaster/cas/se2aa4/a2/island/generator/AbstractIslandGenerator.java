package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.NeighborhoodRelation;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.TileNeighborhood;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.util.List;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    private final int numLakes;
    private final Shape shape;
    protected final Mesh mesh;

    protected AbstractIslandGenerator(Mesh mesh, Shape shape, int numLakes) {
        this.mesh = mesh;
        this.shape = shape;
        this.numLakes = numLakes;
    }

    @Override
    public final void generate() {
        List<Tile> tiles = this.mesh.getPolygons().stream().map(Tile::new).toList();

        // Calculate the tiles neighborhood relationship
        NeighborhoodRelation neighborhood = new TileNeighborhood();
        neighborhood.calculateNeighbors(tiles);

        this.generateIsland(tiles, this.shape);

        List<Tile> landTiles = tiles.stream().filter(t -> t.getType().getGroup() == TileGroup.LAND).toList();
        this.generateLakes(landTiles, this.numLakes);

    }

    /**
     * Generates the land tiles and ocean tiles
     * @param tiles The {@link List} of tiles given by the mesh
     */
    protected abstract void generateIsland(List<Tile> tiles, Shape shape);

    /**
     * Generates lakes in the island
     * @param mainLandTiles All the land {@link Tile} since lakes can only be in land
     */
    protected abstract void generateLakes(List<Tile> mainLandTiles, int numLakes);
}
