package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int num_dots_ro = (width / square_size) + 1; // number of dots per row

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();

        // Counters to keep track of the indices of the vertices
        int xIndex = 0;
        int yIndexCount = 0;

        // Create all the vertices and segments
        for (int y = 0; y <= height; y += square_size) {
            for (int x = 0; x <= width; x += square_size) {
                Vertex v = getVertexWithColor(x, y);

                // Add vertex
                vertices.add(v);

                // Add segments in row
                if(xIndex%num_dots_row >= 1) { // Has there been at least 1 vertex added to this row?
                    segments.add(getSegmentWithColor(vertices.get(xIndex-1), v, xIndex-1, xIndex));
                }

                // Add segments in column
                if(xIndex >= num_dots_row) { // Is there at least 1 row added?
                    int yIndex = ((yIndexCount-1)*num_dots_row) + (xIndex%num_dots_row);
                    segments.add(getSegmentWithColor(v, vertices.get(yIndex), xIndex, yIndex));
                }

                xIndex++;
            }
            yIndexCount++;
        }

        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).build();
    }

    /**
     *
     * @param properties The properties of the element to extract the color of
     * @return The {@link Color} of the element
     */
    private Color extractColor(List<Property> properties) {
        String val = null;

        // Get color property value
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                System.out.println(p.getValue());
                val = p.getValue();
            }
        }

        if (val == null) // No color?
            return Color.BLACK;

        // Get color from property
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }

    /**
     *
     * @param x The x position of the {@link Vertex}
     * @param y The y position of the {@link Vertex}
     * @return The {@link Vertex} instance
     */
    private Vertex getVertexWithColor(int x, int y) {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;

        // Create color property
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

        return Vertex.newBuilder().setX(x).setY(y).addProperties(color).build();
    }

    /**
     *
     * @param v1 The {@link Vertex} on the left side of the segment
     * @param v2 The {@link Vertex} on the right side of the segment
     * @param v1Idx The index of v1
     * @param v2Idx The index of v2
     * @return The {@link Segment} connecting v1 and v2 with color of the average of the colors of the vertices
     */
    private Segment getSegmentWithColor(Vertex v1, Vertex v2, int v1Idx, int v2Idx) {
        // Get vertex 1 and vertex 2 colors
        Color v1Color = extractColor(v1.getPropertiesList());
        Color v2Color = extractColor(v2.getPropertiesList());

        // Generate segment color RGB values
        int red = (v1Color.getRed() + v2Color.getRed()) / 2;
        int green = (v1Color.getGreen() + v2Color.getGreen()) / 2;
        int blue = (v1Color.getBlue() + v2Color.getBlue()) / 2;

        // Segment color property
        String colorCode = red + "," + green + "," + blue;
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

        return Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).addProperties(color).build();
    }

}
