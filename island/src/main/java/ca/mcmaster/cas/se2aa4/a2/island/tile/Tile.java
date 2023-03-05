package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Neighborable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile implements Neighborable<Tile>, Converter<Polygon>, Positionable<Double> {

    protected TileType type;
    protected final Polygon polygon;
    private final List<Tile> neighbors;

    /**
     *
     * @param polygon The {@link Polygon} to create a Tile from
     */
    public Tile(Polygon polygon) {
        this.polygon = polygon;
        this.neighbors = new Tiles();
        this.type = TileType.GENERIC_TILE;
    }

    /**
     *
     * @return The {@link TileType} of this tile
     */
    public TileType getType() {
        return this.type;
    }

    @Override
    public Double getX() {
        return this.polygon.getCentroid().getX();
    }

    @Override
    public Double getY() {
        return this.polygon.getCentroid().getY();
    }

    @Override
    public Double[] getPosition() {
        return new Double[]{this.getX(), this.getY()};
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;
        return type == tile.type && Objects.equals(polygon, tile.polygon) && Objects.equals(neighbors, tile.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, polygon, neighbors);
    }
}