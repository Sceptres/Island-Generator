package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.ArrayList;
import java.util.List;

public class Land extends TiledGeography {
    private final List<Lake> lakes;
    private final List<River> rivers;

    public Land() {
        super(TileType.LAND_TILE);
        this.lakes = new ArrayList<>();
        this.rivers = new ArrayList<>();
    }

    @Override
    public void addTile(Tile tile) {
        this.addTile(tile, tile.getType());
    }

    /**
     *
     * @param tile The {@link Tile} to add to the land
     * @param type The type of the tile to add
     */
    public void addTile(Tile tile, TileType type) {
        tile.setType(type);
        super.tiles.add(tile);
    }

    /**
     *
     * @param lake The {@link Lake} to add to the land
     */
    public void addLake(Lake lake) {
        this.lakes.add(lake);
    }

    /**
     *
     * @return The list of {@link Lake} found in this land
     */
    public List<Lake> getLakes() {
        return new ArrayList<>(this.lakes);
    }

    /**
     *
     * @param river The {@link River} to add to the land
     */
    public void addRiver(River river) {
        this.rivers.add(river);
    }

    /**
     *
     * @return The list of {@link River} found in this island
     */
    public List<River> getRivers() {
        return new ArrayList<>(this.rivers);
    }

    /**
     *
     * @return All the unused paths on the land
     */
    public List<Path> getPaths() {
        return this.tiles.stream()
                .filter(t -> t.getType().getGroup() == TileGroup.LAND)
                .flatMap(t -> t.getPaths().stream())
                .filter(p -> p.getType() == PathType.NONE)
                .distinct()
                .toList();
    }

    @Override
    public List<Tile> getNeighbors() {
        return super.tiles.stream()
                .flatMap(t -> t.getNeighbors().stream())
                .filter(t -> t.getType() == TileType.OCEAN_TILE)
                .toList();
    }
}
