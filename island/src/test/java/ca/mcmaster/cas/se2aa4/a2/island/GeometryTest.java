package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testable
public class GeometryTest {

    private Vertex center;

    @BeforeEach
    public void beforeTest() {
        this.center = new Vertex(100, 100);
    }

    @Test
    public void circleTest() {
        Shape circle = new Circle(100, this.center);

        Vertex v1 = new Vertex(50, 50);
        Vertex v2 = new Vertex(0, 0);

        assertTrue(circle.contains(v1));
        assertFalse(circle.contains(v2));
    }

    @Test
    public void ovalTest() {
        Shape circle = new Oval(50, 100, this.center);

        Vertex v1 = new Vertex(120, 120);
        Vertex v2 = new Vertex(0, 0);

        assertTrue(circle.contains(v1));
        assertFalse(circle.contains(v2));
    }
}
