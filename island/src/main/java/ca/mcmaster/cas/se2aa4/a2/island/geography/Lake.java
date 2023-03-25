package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.island.humidity.IHumidity;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.profiles.HumidityProfile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileGroup;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;

import java.util.List;
import java.util.Random;

public class Lake extends TiledGeography implements IHumidity {

    private final HumidityProfile humidityProfile;
    /**
     *
     * @param random The random instance to use in finding next tile
     * @return The next {@link Tile} of the lake
     */
    public static Tile getNextTile(Lake lake, Random random) {
        List<Tile> tiles = lake.getTiles();
        Tile tile = tiles.get(tiles.size()-1);
        List<Tile> neighbors = tile.getNeighbors();
        return neighbors.get(random.nextInt(neighbors.size()));
    }

    public Lake(Tile start, HumidityProfile humidityProfile) {
        super(TileType.LAND_WATER_TILE);
        super.addTile(start);
        this.humidityProfile = humidityProfile;
    }

    @Override
    public List<Tile> getNeighbors() {
        return super.tiles.stream()
                .flatMap(t -> t.getNeighbors().stream())
                .filter(t -> t.getType().getGroup() == TileGroup.LAND)
                .toList();
    }

    @Override
    public float getHumidity() {
        return this.humidityProfile.getHumidity();
    }

    @Override
    public void setHumidity(float humidity) {
        this.tiles.forEach(t -> t.setHumidity(humidity));
    }

    @Override
    public void giveHumidity(IHumidity humidity) {
        humidity.setHumidity(this.tiles.size() * 25);
    }
}
