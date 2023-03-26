package ca.mcmaster.cas.se2aa4.a2.island.mesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.SoilAbsorptionProfile;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.NeighborhoodRelation;
import ca.mcmaster.cas.se2aa4.a2.island.neighborhood.TileNeighborhood;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.ArrayList;
import java.util.List;

public class IslandMesh implements Converter<Mesh> {
    private final Mesh mesh;
    private final List<Tile> tiles;
    private final List<Path> paths;

    /**
     *
     * @param mesh The {@link Mesh} to get the polygons from
     * @param paths All the paths in the mesh to tie to the tiles
     * @return All the {@link Tile} in the mesh
     */
    private static List<Tile> createTiles(Mesh mesh, List<Path> paths, SoilAbsorptionProfile soilAbsorptionProfile) {
        List<Polygon> polygons = mesh.getPolygons();
        List<Tile> tiles = new ArrayList<>();

        for(Polygon polygon : polygons) {
            List<Path> tilePaths = polygon.getConverted().getSegmentIdxsList().stream().map(paths::get).toList();
            Tile tile = new Tile(polygon, tilePaths, soilAbsorptionProfile);
            tiles.add(tile);
        }

        return tiles;
    }

    /**
     *
     * @param mesh The {@link Mesh} to get the segments from
     * @return The {@link List} of {@link Path}
     */
    private static List<Path> createPaths(Mesh mesh) {
        return mesh.getSegments().stream().map(Path::new).toList();
    }

    /**
     *
     * @param mesh The {@link Mesh} to read from
     */
    public IslandMesh(Mesh mesh, SoilAbsorptionProfile soilAbsorptionProfile) {
        this.mesh = mesh;
        this.paths = IslandMesh.createPaths(mesh);
        this.tiles = IslandMesh.createTiles(mesh, this.paths, soilAbsorptionProfile);

        NeighborhoodRelation relation = new TileNeighborhood();
        relation.calculateNeighbors(this.tiles);
    }

    /**
     *
     * @return The {@link Path} found in this mesh
     */
    public List<Path> getPaths() {
        return new ArrayList<>(this.paths);
    }

    /**
     *
     * @return The {@link Tile} found in this mesh
     */
    public List<Tile> getTiles() {
        return new ArrayList<>(this.tiles);
    }

    /**
     *
     * @return The dimensions of this mesh
     */
    public int[] getDimension() {
        return this.mesh.getDimension();
    }

    @Override
    public Mesh getConverted() {
        return this.mesh;
    }
}
