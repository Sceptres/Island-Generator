package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.NeighborhoodRelation;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.TileNeighborhood;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.util.List;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    protected final Mesh mesh;
    protected final Shape shape;

    protected AbstractIslandGenerator(Mesh mesh, Shape shape) {
        this.mesh = mesh;
        this.shape = shape;
    }

    @Override
    public final void generate(Mesh mesh) {
        List<Tile> tiles = mesh.getPolygons().stream().map(Tile::new).toList();

        // Calculate the tiles neighborhood relationship
        NeighborhoodRelation neighborhood = new TileNeighborhood();
        neighborhood.calculateNeighbors(tiles);

        this.generateIsland(tiles);
    }

    /**
     *
     * @param tiles The {@link List} of tiles given by the mesh
     */
    protected abstract void generateIsland(List<Tile> tiles);
}
