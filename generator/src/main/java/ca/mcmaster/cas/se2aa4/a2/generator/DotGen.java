package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int num_dots_row = (width / square_size) + 1; // number of dots per row

    public Structs.Mesh generate() {
        Mesh mesh = new Mesh();

        // Create all the vertices and segments
        for (int y = 0; y < height; y += square_size) {
            for (int x = 0; x < width; x += square_size) {
                // Add vertex
                Vertex v0 = getVertexWithColor(x, y);
                Vertex v1 = getVertexWithColor(x+square_size, y);
                Vertex v2 = getVertexWithColor(x+square_size, y+square_size);
                Vertex v3 = getVertexWithColor(x, y+square_size);
                mesh.addVertex(v0);
                mesh.addVertex(v1);
                mesh.addVertex(v2);
                mesh.addVertex(v3);

                // Add segments
                Segment s0 = getSegmentWithColor(v0, v1);
                Segment s1 = getSegmentWithColor(v1, v2);
                Segment s2 = getSegmentWithColor(v2, v3);
                Segment s3 = getSegmentWithColor(v3, v0);
                mesh.addSegment(s0);
                mesh.addSegment(s1);
                mesh.addSegment(s2);
                mesh.addSegment(s3);

                // Add polygon
                Polygon polygon = new Polygon(List.of(s0, s1, s2, s3));
                mesh.addVertex(polygon.getCentroid());
                mesh.addPolygon(polygon);
            }
        }

        // Match neighboring polygons
        for(Polygon p1 : mesh.getPolygons()) {
            for(Polygon p2 : mesh.getPolygons()) {
                p1.addNeighbor(p2);
            }
        }

        return mesh.getConverted();
    }

    /**
     *
     * @param x The x position of the {@link Vertex}
     * @param y The y position of the {@link Vertex}
     * @return The {@link Vertex} instance
     */
    private Vertex getVertexWithColor(double x, double y) {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        Color color = new Color(red, green, blue);

        Vertex vertex = new Vertex(x, y);
        vertex.setColor(color);

        return vertex;
    }

    /**
     *
     * @param v1
     * @param v2
     * @return
     */
    private Segment getSegmentWithColor(Vertex v1, Vertex v2) {
        Segment segment = new Segment(v1, v2);
        segment.calculateColor();
        return segment;
    }
}
