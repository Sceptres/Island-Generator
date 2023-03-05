package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;

public class GraphicRenderer {

    private final boolean isDebug;

    public GraphicRenderer(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void render(Mesh mesh, Graphics2D canvas) {


        canvas.setColor(Color.BLACK);

        Segments debugSegments = new Segments();

        // Render polygons
        mesh.getPolygons().forEach(polygon -> {
            if(this.isDebug) {
                Color color = Util.generateRandomColor(false);

                polygon.getNeighbors().forEach(p -> {
                    // Calculate center point between centroids of polygon and its neighbors
                    double midX = (p.getX() + polygon.getX()) / 2;
                    double midY = (p.getY() + polygon.getY()) / 2;
                    Vertex midVertex = new Vertex(midX, midY);

                    // Segment that will be drawn to show neighborhood
                    Segment segment = new Segment(polygon.getCentroid(), midVertex);
                    segment.setColor(color);
                    debugSegments.add(segment);
                });
                polygon.setColor(new Color(0, 0, 0, 0));
            }

            polygon.render(canvas);
        });

        // Render segments
        mesh.getSegments().forEach(segment -> {
            if(this.isDebug) {
                segment.setColor(Color.BLACK);
                segment.render(canvas);
            }
        });

        // Render vertices
        mesh.getNonCentroidVertices().forEach(vertex -> {
            if(this.isDebug) {
                vertex.setColor(Color.BLACK);
                vertex.render(canvas);
            }
        });

        if(this.isDebug) {
            debugSegments.forEach(s -> s.render(canvas));
            mesh.getCentroidVertices().forEach(vertex -> vertex.render(canvas));
        }
    }
}