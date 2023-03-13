package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.List;

public class LagoonIslandGenerator extends AbstractIslandGenerator {
    @Override
    protected void generateIsland(Mesh mesh, List<Tile> tiles) {
        // Find mesh center vertex
        int[] meshDimension = mesh.getDimension();
        Vertex meshCenter = new Vertex(meshDimension[0]/2f, meshDimension[1]/2f);

        // Create circles
        double diagonalLength = Math.hypot(meshDimension[0], meshDimension[1]);
        Shape circle = new Oval(diagonalLength/16f, diagonalLength/8f, meshCenter);
        Shape circle1 = new Oval(diagonalLength/8f, diagonalLength/4f, meshCenter);

        // Make water tiles
        tiles.stream().filter(circle::contains).forEach(t -> t.setType(TileType.LAGOON_TILE));
        tiles.stream().filter(t -> !circle1.contains(t)).forEach(t -> t.setType(TileType.OCEAN_TILE));

        // Generate land tiles
        List<Tile> landTiles = tiles.stream().filter(t -> circle1.contains(t) && !circle.contains(t)).toList();
        landTiles.stream().filter(t ->
                t.getNeighbors().stream().anyMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER)
        ).forEach(t -> t.setType(TileType.BEACH_TILE));
    }
}