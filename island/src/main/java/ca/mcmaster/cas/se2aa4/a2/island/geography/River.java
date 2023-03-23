package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NoElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.*;

public class River extends TiledGeography {

    private Vertex end;
    private final float flow;
    private final Vertex start;
    private final List<Tile> tiles;
    private final LinkedList<Path> riverPath;
    private final ElevationHandler handler;

    public River(Vertex start, float flow){
        super(TileType.LAND_WATER_TILE);
        this.start = start;
        this.end = start;
        this.flow = flow;
        this.tiles = new LinkedList<>();
        this.riverPath = new LinkedList<>();
        this.handler = new NoElevationHandler();
    }

    /**
     *
     * @return The {@link Vertex} where river starts
     */
    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return this.end;
    }

    public void setEnd(Vertex v) {
        this.end = v;
    }

    /**
     *
     * @return The {@link Path}s of the river
     */
    public List<Path> getRiverPath() {
        return new ArrayList<>(this.riverPath);
    }

    @Override
    public List<Tile> getTiles() {
        return new ArrayList<>(this.tiles);
    }

    @Override
    public void addTile(Tile tile) {
        this.tiles.add(tile);
    }

    @Override
    public void addAllTiles(List<Tile> tiles) {
        tiles.forEach(this::addTile);
    }

    @Override
    public List<Tile> getNeighbors() {
        return this.getTiles();
    }

    @Override
    public void setElevation(double elevation) {
        this.handler.takeElevation(super.elevation, elevation);
    }

    /**
     *
     * @return The base flow of the river
     */
    public float getFlow() {
        return this.flow;
    }

    /**
     *
     * @return Gets all the vertices in this river
     */
    public List<Vertex> getVertices() {
        return this.riverPath.stream()
                .flatMap(p -> Arrays.stream(new Vertex[]{p.getV1(), p.getV2()}))
                .distinct()
                .toList();
    }

    /**
     *
     * @param path The {@link Path} to add to the river
     * @param tiles The 2 {@link Tile}s that this path belong to
     */
    public void addPath(Path path, List<Tile> tiles) {
        path.setWidth(this.flow);
        path.setType(PathType.RIVER);
        this.riverPath.add(path);
        this.tiles.addAll(tiles);
    }

    public void update(Vertex v, float flow) {
        Optional<Path> pathOptional = this.riverPath.stream().filter(p -> p.hasVertex(v)).findFirst();
        if(pathOptional.isPresent()) {
            Path path = pathOptional.get();
            int pathIdx = this.riverPath.indexOf(path);

            for(int i=pathIdx; i < this.riverPath.size(); i++) {
                Path currentPath = this.riverPath.get(i);
                currentPath.setWidth(flow);
            }
        }
    }

    public boolean intersect(Vertex vertex) {
        return this.getVertices().contains(vertex);
    }

    public boolean intersect(River river) {
        return this.intersect(river.end);
    }

}
