package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.neighborhood.NeighborhoodRelation;
import ca.mcmaster.cas.se2aa4.a2.island.tile.neighborhood.TileNeighborhood;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.util.List;

public abstract class AbstractIslandGenerator implements IslandGenerator {

    @Override
    public final void generate(Mesh mesh) {
        List<Tile> tiles = mesh.getPolygons().stream().map(Tile::new).toList();

        // Calculate the tiles neighborhood relationship
        NeighborhoodRelation neighborhood = new TileNeighborhood();
        neighborhood.calculateNeighbors(tiles);

        this.generateIsland(mesh, tiles);
    }

    /**
     *
     * @param tiles The {@link List} of tiles given by the mesh
     */
    protected abstract void generateIsland(Mesh mesh, List<Tile> tiles);
}
