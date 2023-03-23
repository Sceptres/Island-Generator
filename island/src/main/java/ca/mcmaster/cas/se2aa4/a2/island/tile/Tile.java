package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Aquiferable;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.HumidityReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.handlers.reciever.IReceiver;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.profiles.HumidityProfile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.Configurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Neighborable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Tile implements Neighborable<Tile>, Converter<Polygon>, Positionable<Double>, IElevation, IHumidity, Aquiferable {

    private TileType type;
    private boolean aquifer;
    private Configurator configurator;
    private final IReceiver humidityReceiver;
    private final HumidityProfile humidity;
    private final ElevationProfile elevation;
    private final Polygon polygon;
    private final List<Tile> neighbors;

    /**
     *
     * @param polygon The {@link Polygon} to create a Tile from
     */
    public Tile(Polygon polygon, IReceiver humidityReceiver) {
        this.polygon = polygon;
        this.neighbors = new Tiles();
        this.setType(TileType.LAND_TILE);
        this.humidityReceiver = humidityReceiver;
        this.humidity = new HumidityProfile();
        this.elevation = new ElevationProfile();
        this.aquifer = false;
    }

    public Tile(Polygon polygon) {
        this(polygon, new HumidityReceiver(1));
    }

    /**
     *
     * @param type The new {@link TileType} of this tile
     */
    public void setType(TileType type) {
        this.type = type;
        this.configurator = type.getConfigurator();
        this.polygon.setColor(this.configurator.apply());
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
        return this.polygon.getNeighbors().contains(tile.polygon);
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
    public double getElevation() {
        return this.elevation.getElevation();
    }

    @Override
    public void setElevation(double elevation) {
        ElevationHandler handler = this.configurator.getElevationHandler();
        handler.takeElevation(this.elevation, elevation);
    }

    @Override
    public boolean hasAquifer() {
        return this.aquifer;
    }

    @Override
    public void putAquifer() {
        this.aquifer = true;
    }

    @Override
    public void removeAquifer() {
        this.aquifer = false;
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

    @Override
    public float getHumidity() {
        return this.humidity.getHumidity();
    }

    @Override
    public void setHumidity(float humidity) {
        this.humidityReceiver.receiveHumidity(this.humidity, humidity);
    }

    @Override
    public void giveHumidity(IHumidity h) {
        float humidity = this.configurator.getHumidityTransmitter().giveHumidity(this);
        h.setHumidity(humidity);
    }
}