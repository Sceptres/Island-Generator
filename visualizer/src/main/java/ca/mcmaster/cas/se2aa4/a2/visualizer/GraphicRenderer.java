package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Random;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        boolean debug = false;

        Mesh mesh = new Mesh(aMesh);

        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(1f);
        canvas.setStroke(stroke);

        Segments debugSegments = new Segments();

        mesh.getPolygons().forEach(polygon -> {
            if(debug) {
                Random random = new Random();
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                Color color = new Color(r, g, b);

                polygon.getNeighbors().forEach(p -> {
                    double midX = (p.getCentroid().getX() + polygon.getCentroid().getX()) / 2;
                    double midY = (p.getCentroid().getY() + polygon.getCentroid().getY()) / 2;
                    Vertex midVertex = new Vertex(midX, midY);
                    Segment segment = new Segment(polygon.getCentroid(), midVertex);
                    segment.setColor(color);
                    debugSegments.add(segment);
                });
            }
            polygon.setColor(new Color(0, 0, 0, 0));
            polygon.render(canvas);
        });

        mesh.getSegments().forEach(segment -> {
            if(debug)
                segment.setColor(Color.BLACK);
            segment.render(canvas);
        });

        mesh.getNonCentroidVertices().forEach(vertex -> {
            if(debug)
                vertex.setColor(Color.BLACK);
            vertex.render(canvas);
        });

        if(debug) {
            debugSegments.forEach(s -> s.render(canvas));
            mesh.getCentroidVertices().forEach(vertex -> vertex.render(canvas));
        }
    }
}
