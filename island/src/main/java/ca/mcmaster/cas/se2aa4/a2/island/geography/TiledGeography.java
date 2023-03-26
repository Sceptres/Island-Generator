package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.configuration.Configurator;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.ArrayList;
import java.util.List;

public abstract class TiledGeography implements IElevation {
    protected final TileType type;
    protected final List<Tile> tiles;
    protected final ElevationProfile elevation;
    private final Configurator configurator;

    protected TiledGeography(TileType type) {
        this.type = type;
        this.tiles = new ArrayList<>();
        this.elevation = new ElevationProfile();
        this.configurator = type.getConfigurator();
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(this.tiles);
    }

    /**
     *
     * @param tile Adds a {@link Tile} to the lake
     */
    public void addTile(Tile tile) {
        tile.setType(this.type);
        this.tiles.add(tile);
    }

    /**
     *
     * @param tiles The {@link List} of tiles to add to this geography
     */
    public void addAllTiles(List<Tile> tiles) {
        tiles.forEach(this::addTile);
    }

    @Override
    public double getElevation() {
        return this.elevation.getElevation();
    }

    @Override
    public void setElevation(double elevation) {
        this.configurator.getElevationHandler().takeElevation(this.elevation, elevation);
        this.tiles.forEach(t -> t.setElevation(this.getElevation()));
    }

    public abstract List<Tile> getNeighbors();
}
