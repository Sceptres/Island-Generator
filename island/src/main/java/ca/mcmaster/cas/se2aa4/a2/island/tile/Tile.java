package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Neighborable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Tile implements Neighborable<Tile>, Converter<Polygon> {

    private final Polygon polygon;
    private final List<Tile> neighbors;

    /**
     *
     * @param polygon The {@link Polygon} to create a Tile from
     */
    public Tile(Polygon polygon) {
        this.polygon = polygon;
        this.neighbors = new Tiles();
    }

    /**
     *
     * @return The X position of the centroid of the tile
     */
    public double getX() {
        return this.polygon.getCentroid().getX();
    }

    /**
     *
     * @return The Y position of the centroid of the tile
     */
    public double getY() {
        return this.polygon.getCentroid().getY();
    }

    /**
     *
     * @return A 2 element double array with the X and Y position of the centroid
     */
    public double[] getPosition() {
        Vertex centroid = this.polygon.getCentroid();
        return new double[]{centroid.getX(), centroid.getY()};
    }

    @Override
    public Polygon getConverted() {
        return this.polygon;
    }

    @Override
    public boolean isNeighbor(Tile tile) {
        return this.polygon.isNeighbor(tile.polygon);
    }

    @Override
    public List<Tile> getNeighbors() {
        return new ArrayList<>(this.neighbors);
    }

    @Override
    public void addNeighbor(Tile tile) {
        this.neighbors.add(tile);
    }

    @Override
    public void addNeighbors(List<Tile> tiles) {
        tiles.forEach(this::addNeighbor);
    }
}
