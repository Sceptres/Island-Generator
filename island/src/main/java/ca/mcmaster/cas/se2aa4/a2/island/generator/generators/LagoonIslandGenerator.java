package ca.mcmaster.cas.se2aa4.a2.island.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.island.generator.AbstractIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Circle;
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
        Circle circle = new Circle(diagonalLength/8f, meshCenter);
        Circle circle1 = new Circle(diagonalLength/4f, meshCenter);

        // Make water tiles
        tiles.stream().filter(circle::isInside).forEach(t -> t.setType(TileType.LAGOON_TILE));
        tiles.stream().filter(t -> !circle1.isInside(t)).forEach(t -> t.setType(TileType.OCEAN_TILE));

        // Generate land tiles
        List<Tile> landTiles = tiles.stream().filter(t -> circle1.isInside(t) && !circle.isInside(t)).toList();
        landTiles.stream().filter(t ->
                t.getNeighbors().stream().anyMatch(t1 -> t1.getType().getGroup() == TileGroup.WATER)
        ).forEach(t -> t.setType(TileType.BEACH_TILE));
    }
}