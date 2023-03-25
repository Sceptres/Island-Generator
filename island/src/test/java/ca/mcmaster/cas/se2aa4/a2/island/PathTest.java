package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.path.Path;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathTest {
    private Segment segment;
    private Path path;

    @Test
    @BeforeEach
    public void beforeTest() {
        Vertex v1 = new Vertex(0, 0);
        Vertex v2 = new Vertex(100, 0);

        this.segment = new Segment(v1, v2);
        this.path = new Path(this.segment);

        assertEquals(this.path.getV1(), v1);
        assertEquals(this.path.getV2(), v2);
    }

    @Test
    public void testPathType() {
        assertEquals(this.path.getType(), PathType.NONE);
        assertEquals(this.segment.getColor(), PathType.NONE.getColorGenerator().generateColor());

        this.path.setType(PathType.RIVER);
        assertEquals(this.path.getType(), PathType.RIVER);
        assertEquals(this.segment.getColor(), PathType.RIVER.getColorGenerator().generateColor());
    }

    @Test
    public void testPathWidth() {
        assertEquals(this.path.getWidth(), 1f);

        this.path.setWidth(1.5f);
        assertEquals(this.path.getWidth(), 1.5f);
    }

    @Test
    public void testPathElevation() {
        assertEquals(this.path.getElevation(), 1);

        this.path.setElevation(0.2);
        assertEquals(this.path.getElevation(), 0.2);


        this.path.setElevation(0.5);
        assertEquals(this.path.getElevation(), 0.2);
    }

}
