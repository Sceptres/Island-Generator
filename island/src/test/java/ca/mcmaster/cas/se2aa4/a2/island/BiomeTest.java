package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.biome.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.biome.biomes.TropicalBiome;
import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.tile.type.TileType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class BiomeTest {
    private static Tile tile;
    private static Biome biome;

    @BeforeAll
    public static void beforeTest() {
        Vertex v1 = new Vertex(0, 0);
        Vertex v2 = new Vertex(100, 0);
        Vertex v3 = new Vertex(100, 100);
        Vertex v4 = new Vertex(0, 100);

        Segment s1 = new Segment(v1, v2);
        Segment s2 = new Segment(v2, v3);
        Segment s3 = new Segment(v3, v4);
        Segment s4 = new Segment(v4, v1);

        List<Segment> polygonSegments = List.of(s1, s2, s3, s4);
        List<Path> paths = polygonSegments.stream().map(Path::new).toList();

        Polygon polygon = new Polygon(polygonSegments);

        tile = new Tile(polygon, paths);

        biome = new TropicalBiome();
    }

    @Test
    public void tileTest() {
        tile.setHumidity(0.5f);
        tile.setElevation(0.4);
        biome.takeTile(tile);
        assertEquals(TileType.SUBTROPICAL_DESERT, tile.getType());

        tile.setHumidity(100f);
        tile.setElevation(0.2);
        biome.takeTile(tile);
        assertEquals(TileType.TROPICAL_SEASONAL_FOREST, tile.getType());

        tile.setHumidity(350f);
        tile.setElevation(0.6);
        biome.takeTile(tile);
        assertEquals(TileType.TROPICAL_RAIN_FOREST, tile.getType());

        tile.setHumidity(25f);
        tile.setElevation(0.1);
        biome.takeTile(tile);
        assertEquals(TileType.BEACH, tile.getType());
    }
}
