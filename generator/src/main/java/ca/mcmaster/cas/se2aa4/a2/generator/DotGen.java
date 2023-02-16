package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int num_dots_row = (width / square_size) + 1; // number of dots per row

    public Mesh generate() {
        List<Vertex> vertices = new ArrayList<>();
        List<Segment> segments = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();


        // Create all the vertices
        for (int y = 0; y <= height; y += square_size) {
            for (int x = 0; x <= width; x += square_size) {
                Vertex v = getVertexWithColor(x, y);

                // Add vertex
                vertices.add(v);

            }
        }

        int nonAnchor = 25;

        int polygonCount = 0;

        for(int i = 0; i < vertices.size(); i++){
            if(i == 0){
                Vertex v0 = vertices.get(0);
                Vertex v1 = vertices.get(1);
                Segment seg0 = getSegmentWithColor(v0,v1,0,1);
                segments.add(seg0);

                Vertex v27 = vertices.get(27);
                Segment seg1 = getSegmentWithColor(v1,v27,1,27);
                segments.add(seg1);

                Vertex v26 = vertices.get(26);
                Segment seg2 = getSegmentWithColor(v27,v26,27,26);
                segments.add(seg2);

                Segment seg3 = getSegmentWithColor(v26,v0,26,0);
                segments.add(seg3);

                Vertex centroid = getCentroid((v0.getX() + v1.getX())/2, (v1.getY()+ v27.getY())/2 );
                vertices.add(centroid);

                int centroidIndx = vertices.indexOf(centroid);

                Polygon polygon = Polygon.newBuilder().addSegmentIdxs(0).addSegmentIdxs(1)
                                .addSegmentIdxs(2).addSegmentIdxs(3).addNeighborIdxs(1)
                        .addNeighborIdxs(25).addNeighborIdxs(26).setCentroidIdx(centroidIndx).build();
                polygons.add(polygon);

            }
            else if(i > 0 && i < 25){
                Vertex v0 = vertices.get(i);
                Vertex v1 = vertices.get(i+1);
                Segment seg0 = getSegmentWithColor(v0,v1,i,i+1);
                int segmentIndx = 3+(i-1)*2;
                segments.add(seg0);
                segmentIndx = segments.size()-1;

                Vertex v2 = vertices.get(i+27);
                Segment seg1 = getSegmentWithColor(v1,v2,i+1,i+27);
                segments.add(seg1);

                Vertex v3 = vertices.get(i+26);
                Segment seg2 = getSegmentWithColor(v2,v3,i+27,i+26);
                segments.add(seg2);

                Vertex centroid = getCentroid((v0.getX() + v1.getX())/2, (v1.getY()+ v2.getY())/2 );
                vertices.add(centroid);

                int centroidIndx = vertices.indexOf(centroid);

                if(i == 1){
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(segmentIndx+2)
                            .addSegmentIdxs(i).setCentroidIdx(centroidIndx).addNeighborIdxs(i-1)
                            .addNeighborIdxs(i+1).addNeighborIdxs(i+24).addNeighborIdxs(i+25)
                            .addNeighborIdxs(i+26).build();
                    polygons.add(polygon);
                }
                else{
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(segmentIndx+2)
                            .addSegmentIdxs(segmentIndx-2).setCentroidIdx(centroidIndx).addNeighborIdxs(i-1)
                            .addNeighborIdxs(i+1).addNeighborIdxs(i+24).addNeighborIdxs(i+25)
                            .addNeighborIdxs(i+26).build();
                    polygons.add(polygon);
                }

                polygonCount++;

            }
            else if(i == nonAnchor || i >= 650){
                nonAnchor += 26;
            }
            else if(i%26 == 0){
                Vertex v0 = vertices.get(i+1);
                Vertex v1 = vertices.get(i+27);
                Segment seg0 = getSegmentWithColor(v0,v1,i+1,i+27);
                int segmentIndx = 76+(((polygonCount+1)/25)-1)*51;
                segments.add(seg0);
                segmentIndx = segments.size()-1;

                Vertex v2 = vertices.get(i+26);
                Segment seg1 = getSegmentWithColor(v1,v2,i+27,i+26);
                segments.add(seg1);

                Vertex v3 = vertices.get(i);
                Segment seg2 = getSegmentWithColor(v2,v3,i+26,i);
                segments.add(seg2);

                Vertex centroid = getCentroid((v0.getX() + v3.getX())/2, (v0.getY()+ v1.getY())/2 );
                vertices.add(centroid);

                int centroidIndx = vertices.indexOf(centroid);

                int upperSegIndx = polygons.get(polygonCount-24).getSegmentIdxsList().get(2);

                Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                        .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(segmentIndx+2)
                        .addNeighborIdxs(polygonCount-24).addNeighborIdxs(polygonCount-23)
                        .addNeighborIdxs(polygonCount+2).addNeighborIdxs(polygonCount+26)
                        .addNeighborIdxs(polygonCount+27).setCentroidIdx(centroidIndx).build();
                polygons.add(polygon);

                polygonCount++;



            }
            else{
                Vertex v0 = vertices.get(i+1);
                Vertex v1 = vertices.get(i+27);
                Segment seg0 = getSegmentWithColor(v0,v1,i+1,i+27);
                int segmentIndx;
                segmentIndx = segments.size()-1;

                if(polygonCount%25 == 0){
                    segmentIndx = polygons.get(polygonCount).getSegmentIdxsList().get(1)+3;
                }
                else {
                    segmentIndx = polygons.get(polygonCount).getSegmentIdxsList().get(1)+2;
                }

                segments.add(seg0);

                Vertex v2 = vertices.get(i+26);
                Segment seg1 = getSegmentWithColor(v1,v2,i+27,i+26);
                segments.add(seg1);

                Vertex centroid = getCentroid((v0.getX() + v2.getX())/2, (v0.getY()+ v1.getY())/2 );
                vertices.add(centroid);

                int centroidIndx = vertices.indexOf(centroid);

                int upperSegIndx = polygons.get(polygonCount-24).getSegmentIdxsList().get(2);

                int leftSegIndx = polygons.get(polygonCount).getSegmentIdxsList().get(1);

                Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                        .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(leftSegIndx)
                        .addNeighborIdxs(polygonCount-25).addNeighborIdxs(polygonCount-24)
                        .addNeighborIdxs(polygonCount-23).addNeighborIdxs(polygonCount)
                        .addNeighborIdxs(polygonCount+2).addNeighborIdxs(polygonCount+25)
                        .addNeighborIdxs(polygonCount+26).addNeighborIdxs(polygonCount+27)
                        .setCentroidIdx(centroidIndx).build();
                polygons.add(polygon);

                polygonCount++;
            }

        }

        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
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
    private Vertex getVertexWithColor(double x, double y) {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;

        // Create color property
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

        return Vertex.newBuilder().setX(x).setY(y).addProperties(color).build();
    }

    private Vertex getCentroid(double x, double y) {
        Random bag = new Random();
        int red = 255;
        int green = 0;
        int blue = 0;
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
