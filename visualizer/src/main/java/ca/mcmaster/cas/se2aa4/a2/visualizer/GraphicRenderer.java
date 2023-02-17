package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.awt.*;

public class GraphicRenderer {

    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        Mesh mesh = new Mesh(aMesh);

        canvas.setColor(Color.BLACK);

        // Render polygons
        mesh.getPolygons().forEach(polygon -> polygon.render(canvas));

        // Render segments
        mesh.getSegments().forEach(segment -> segment.render(canvas));

        // Render vertices
        mesh.getNonCentroidVertices().forEach(vertex -> vertex.render(canvas));
    }
}
