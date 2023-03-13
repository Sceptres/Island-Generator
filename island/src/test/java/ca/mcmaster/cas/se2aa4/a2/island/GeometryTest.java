package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Circle;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testable
public class GeometryTest {

    private Circle circle;

    @BeforeEach
    public void beforeTest() {
        Vertex center = new Vertex(100, 100);
        this.circle = new Circle(100, center);
    }

    @Test
    public void containsTest() {
        Vertex v1 = new Vertex(50, 50);
        Vertex v2 = new Vertex(0, 100);
        Vertex v3 = new Vertex(0, 0);

        assertTrue(this.circle.isInside(v1));
        assertTrue(this.circle.isInside(v2));
        assertFalse(this.circle.isInside(v3));
    }

    @Test
    public void changePositionTest() {
        Vertex v = this.circle.getVertex();
        v.setX(200);
        v.setY(200);

        Vertex v1 = new Vertex(190, 190);
        Vertex v2 = new Vertex(100, 200);
        Vertex v3 = new Vertex(0, 0);

        assertTrue(this.circle.isInside(v1));
        assertTrue(this.circle.isInside(v2));
        assertFalse(this.circle.isInside(v3));
    }

    @Test
    public void changeRadiusTest() {
        this.circle.setRadius(50);

        Vertex v1 = new Vertex(75, 125);
        Vertex v2 = new Vertex(50, 100);
        Vertex v3 = new Vertex(0, 0);

        assertTrue(this.circle.isInside(v1));
        assertTrue(this.circle.isInside(v2));
        assertFalse(this.circle.isInside(v3));
    }
}
