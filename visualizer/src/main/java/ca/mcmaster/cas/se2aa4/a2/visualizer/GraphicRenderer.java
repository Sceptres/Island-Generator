package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        List<Vertex> vertices = aMesh.getVerticesList();
        List<Segment> segments = aMesh.getSegmentsList();

        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(3f);
        canvas.setStroke(stroke);

        // Add vertices
        for (Vertex v: vertices) {
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }

        // Add segments
        /*for(Segment segment : segments) {
            // Get vertices assigned to this segment
            Vertex v1 = vertices.get(segment.getV1Idx());
            Vertex v2 = vertices.get(segment.getV2Idx());

            // Draw segment with appropriate color
            Color old = canvas.getColor();
            canvas.setColor(extractColor(segment.getPropertiesList()));
            canvas.draw(new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY()));
            canvas.setColor(old);
        }*/

        Vertex v1 = vertices.get(aMesh.getSegments(76).getV1Idx());
        Vertex v2 = vertices.get(aMesh.getSegments(76).getV2Idx());

        Color old = canvas.getColor();
        canvas.setColor(extractColor(aMesh.getSegments(76).getPropertiesList()));
        canvas.draw(new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY()));
        canvas.setColor(old);
    }


    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

}
