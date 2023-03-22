package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;

import java.awt.*;
import java.util.Objects;

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
                    // Segment that will be drawn to show neighborhood
                    Segment segment = new Segment(polygon.getCentroid(), p.getCentroid());
                    segment.setColor(Color.GRAY);
                    debugSegments.add(segment);
                });
            }

            polygon.render(canvas);
        });

        // Render segments
        mesh.getSegments().forEach(segment -> {
            if(this.isDebug) {
                segment.setColor(Color.BLACK);
                segment.render(canvas);
            }
            Color color = new Color(14,240,37);
            if(Objects.equals(segment.getColor(), color)){
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
            mesh.getCentroidVertices().forEach(vertex -> {
                vertex.setColor(Color.GRAY);
                vertex.render(canvas);
            });
        }
    }
}