package ca.mcmaster.cas.se2aa4.a2.island.tile;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.geography.Aquiferable;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.profiles.HumidityProfile;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.SoilAbsorptionProfile;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.profiles.WetSoilAbsorption;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.Configurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Neighborable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Tile implements Neighborable<Tile>, Positionable<Double>, IElevation, IHumidity, Aquiferable {

    private TileType type;
    private boolean aquifer;
    private Configurator configurator;
    private final SoilAbsorptionProfile soilAbsorptionProfile;
    private final HumidityProfile humidity;
    private final ElevationProfile elevation;
    private final Polygon polygon;
    private final List<Path> paths;
    private final List<Tile> neighbors;

    /**
     *
     * @param polygon The {@link Polygon} that this tile represents
     * @param paths The list {@link Path} belonging to this tile
     * @param soilAbsorptionProfile The {@link SoilAbsorptionProfile} of this tile
     */
    public Tile(Polygon polygon, List<Path> paths, SoilAbsorptionProfile soilAbsorptionProfile) {
        this.polygon = polygon;
        this.neighbors = new Tiles();
        this.paths = new ArrayList<>(paths);
        this.setType(TileType.LAND_TILE);
        this.soilAbsorptionProfile = soilAbsorptionProfile;
        this.humidity = new HumidityProfile();
        this.elevation = new ElevationProfile();
        this.aquifer = false;
    }

    public Tile(Polygon polygon, List<Path> paths) {
        this(polygon, paths, new WetSoilAbsorption());
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

    /**
     *
     * @return The {@link List} of {@link Path} that belong to this tile
     */
    public List<Path> getPaths() {
        return new ArrayList<>(this.paths);
    }

    /**
     *
     * @return The list of {@link Vertex} that belong to this tile
     */
    public List<Vertex> getVertices() {
        return this.polygon.getVertices();
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
        this.paths.forEach(p -> p.setElevation(elevation));
    }

    @Override
    public boolean hasAquifer() {
        return this.aquifer;
    }

    @Override
    public void putAquifer() {
        if(!this.aquifer) {
            this.aquifer = true;
            this.setHumidity(this.getHumidity() + 500);
        }
    }

    @Override
    public void removeAquifer() {
        if(this.aquifer) {
            this.aquifer = false;
            this.setHumidity(this.getHumidity() - 500);
        }
    }

    @Override
    public float getHumidity() {
        return this.humidity.getHumidity();
    }

    @Override
    public void setHumidity(float humidity) {
        this.configurator.getHumidityHandler().handleHumidity(this.humidity, this.soilAbsorptionProfile.getHumidityReceiver(), humidity);
    }

    @Override
    public void giveHumidity(IHumidity h) {
        if(!this.equals(h)) {
            float humidity = this.soilAbsorptionProfile.getHumidityTransmitter().giveHumidity(this);
            float oldHumidity = h.getHumidity();
            h.setHumidity(oldHumidity + humidity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile tile)) return false;
        return type == tile.type && Objects.equals(polygon, tile.polygon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, polygon);
    }
}