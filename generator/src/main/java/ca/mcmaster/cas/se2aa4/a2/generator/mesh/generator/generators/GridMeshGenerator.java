package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.NotSquareMeshException;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.SquaresFittingException;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.AbstractMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.List;

public class GridMeshGenerator extends AbstractMeshGenerator {

    private final double squareSize;

    public GridMeshGenerator(ColorGenerator[] generators, int width, int height, double squareSize) {
        super(generators, width, height);

        if(width != height) // Not square dimensions?
            throw new NotSquareMeshException();
        else if((width/squareSize) % 1 != 0) // Not all squares can fit within the mesh?
            throw new SquaresFittingException(super.getWidth(), super.getHeight());

        this.squareSize = squareSize;
    }

    @Override
    public void generate(Mesh mesh) {
        // Create all the vertices and segments
        for (int y = 0; y < super.getHeight(); y += this.squareSize) {
            for (int x = 0; x < super.getWidth(); x += this.squareSize) {
                // Add vertex
                Vertex v0 = getVertexWithColor(x, y);
                Vertex v1 = getVertexWithColor(x+this.squareSize, y);
                Vertex v2 = getVertexWithColor(x+this.squareSize, y+this.squareSize);
                Vertex v3 = getVertexWithColor(x, y+this.squareSize);
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
                super.getPolygonColorGenerator().generateColor(polygon);
                mesh.addVertex(polygon.getCentroid());
                mesh.addPolygon(polygon);
            }
        }
    }

    /**
     *
     * @param x The x position of the {@link Vertex}
     * @param y The y position of the {@link Vertex}
     * @return The {@link Vertex} instance
     */
    private Vertex getVertexWithColor(double x, double y) {
        Vertex vertex = new Vertex(x, y);
        super.getVertexColorGenerator().generateColor(vertex);

        return vertex;
    }

    /**
     *
     * @param v1 The first {@link Vertex} of the {@link Segment}
     * @param v2 The second {@link Vertex} of the {@link Segment}
     * @return The {@link Segment} connecting both vertices
     */
    private Segment getSegmentWithColor(Vertex v1, Vertex v2) {
        Segment segment = new Segment(v1, v2);
        super.getSegmentColorGenerator().generateColor(segment);

        return segment;
    }
}