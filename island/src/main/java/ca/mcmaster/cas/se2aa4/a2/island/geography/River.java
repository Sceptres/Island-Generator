package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.NoElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class River extends TiledGeography {

    private Vertex end;
    private final float flow;
    private final List<Vertex> start;
    private final Set<Tile> tiles;
    private final Set<Path> riverPath;
    private final DefaultDirectedGraph<Vertex, DefaultEdge> riverGraph;
    private final ElevationHandler handler;

    public River(Vertex start, float flow){
        super(TileType.LAND_WATER_TILE);
        this.start = new UniqueList<>();
        this.start.add(start);

        this.end = start;
        this.flow = flow;
        this.tiles = new HashSet<>();
        this.riverPath = new HashSet<>();
        this.riverGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.handler = new NoElevationHandler();
    }

    /**
     *
     * @return The {@link Vertex} where river starts
     */
    public List<Vertex> getStartVertices() {
        return new ArrayList<>(this.start);
    }

    /**
     *
     * @return The end {@link Vertex}
     */
    public Vertex getEnd() {
        return this.end;
    }

    /**
     *
     * @param v The new end {@link Vertex} of the river
     */
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
     * @return Gets all the vertices in this river
     */
    public List<Vertex> getVertices() {
        return new ArrayList<>(this.riverGraph.vertexSet());
    }

    /**
     *
     * @param path The {@link Path} to add to the river
     * @param tiles The 2 {@link Tile}s that this path belong to
     */
    public void addPath(Path path, Vertex start, List<Tile> tiles) {
        Vertex v1 = path.getV1();
        Vertex v2 = path.getV2();

        if(!v1.equals(start) && !v2.equals(start))
            throw new IllegalArgumentException("Given vertex is not a part of the path");

        path.setWidth(this.flow);
        path.setType(PathType.RIVER);

        Vertex end = v1.equals(start) ? v2 : v1;

        this.riverGraph.addVertex(start);
        this.riverGraph.addVertex(end);

        this.riverGraph.addEdge(start, end);

        this.riverPath.add(path);
        this.tiles.addAll(tiles);

        this.end = end;
    }

    /**
     *
     * @param river The {@link River} to merge with this one
     */
    public void merge(River river) {
        if(!this.intersect(river))
            throw new IllegalArgumentException("Rivers do not merge");

        this.start.addAll(river.start);
        this.riverPath.addAll(river.riverPath);

        Graphs.addGraph(this.riverGraph, river.riverGraph);

        Vertex riverEnd = river.end;

        AllDirectedPaths<Vertex, DefaultEdge> allPaths = new AllDirectedPaths<>(this.riverGraph);
        List<GraphPath<Vertex, DefaultEdge>> graphPaths = allPaths.getAllPaths(riverEnd, this.end, true, null);

        // Linear graph so there can only be one path
        GraphPath<Vertex, DefaultEdge> graphPath = graphPaths.get(0);

        boolean sameFlow = this.start.contains(riverEnd);

        graphPath.getEdgeList().forEach(e -> {
            Vertex v1 = this.riverGraph.getEdgeSource(e);
            Vertex v2 = this.riverGraph.getEdgeTarget(e);
            Path path = this.riverPath.stream().filter(pth -> pth.hasVertex(v1) && pth.hasVertex(v2)).findFirst().get();

            if(!sameFlow)
                path.addWidth(river.flow);
            else
                path.setWidth(this.flow);
        });
    }

    public boolean intersect(Vertex vertex) {
        return this.getVertices().contains(vertex);
    }

    public boolean intersect(River river) {
        return this.intersect(river.end);
    }

}
