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

    public Mesh generate() {
        //ArrayLists for vertices, segments, and polygons
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

        //int to represent the vertices that will not be used as anchors
        int nonAnchor = 25;

        //Polygon count variable
        int polygonCount = 0;

        //Creating segments and polygons
        for(int i = 0; i < vertices.size(); i++){  //Using vertices as anchors to create polygons

            //Special code for the first polygon since all segments need to be created
            if(i == 0){
                //Getting vertices for segment 0
                Vertex v0 = vertices.get(0);
                Vertex v1 = vertices.get(1);
                //Creating segment 0
                Segment seg0 = getSegmentWithColor(v0,v1,0,1);
                segments.add(seg0);

                //Getting vertex to create segment 1
                Vertex v27 = vertices.get(27);
                //Creating segment 1
                Segment seg1 = getSegmentWithColor(v1,v27,1,27);
                segments.add(seg1);

                //Segment 2
                Vertex v26 = vertices.get(26);
                Segment seg2 = getSegmentWithColor(v27,v26,27,26);
                segments.add(seg2);

                //Segment 3
                Segment seg3 = getSegmentWithColor(v26,v0,26,0);
                segments.add(seg3);

                //Creating a centroid vertex by averaging x and y values
                Vertex centroid = getCentroid((v0.getX() + v1.getX())/2, (v1.getY()+ v27.getY())/2 );
                vertices.add(centroid);

                //Getting the centroid index
                int centroidIndx = vertices.indexOf(centroid);

                //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                Polygon polygon = Polygon.newBuilder().addSegmentIdxs(0).addSegmentIdxs(1)
                                .addSegmentIdxs(2).addSegmentIdxs(3).addNeighborIdxs(1)
                        .addNeighborIdxs(25).addNeighborIdxs(26).setCentroidIdx(centroidIndx).build();
                //Adding the polygon to the polygon ArrayList
                polygons.add(polygon);

                //Polygon count is not iterated since it starts at 0 and this if creates polygon 0

            }
            //Polygons created on the top row except for the first one
            else if(i > 0 && i < 25){
                //Getting vertices for segment 0 and creating it
                Vertex v0 = vertices.get(i);
                Vertex v1 = vertices.get(i+1);
                Segment seg0 = getSegmentWithColor(v0,v1,i,i+1);
                segments.add(seg0);
                //Getting the index of the segment
                int segmentIndx = segments.size()-1;

                //Getting vertices for segment 1 and creating it
                Vertex v2 = vertices.get(i+27);
                Segment seg1 = getSegmentWithColor(v1,v2,i+1,i+27);
                segments.add(seg1);

                //Getting vertices for segment 2 and creating it
                Vertex v3 = vertices.get(i+26);
                Segment seg2 = getSegmentWithColor(v2,v3,i+27,i+26);
                segments.add(seg2);

                //Segment 3 will have been already created by the polygon left to the current one, so it's not created here

                //Creating centroid
                Vertex centroid = getCentroid((v0.getX() + v1.getX())/2, (v1.getY()+ v2.getY())/2 );
                vertices.add(centroid);

                //Getting centroid index
                int centroidIndx = vertices.indexOf(centroid);

                //Special if statement for numbering reasons as a result of adding the first polygon next to polygon 0
                //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                //Adding the polygon to the polygon ArrayList
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

                //Iterating the polygon count
                polygonCount++;

            }
            //else if to detect if the current iteration of the loop corresponds to a non-anchor vertex index, or a vertex
            // that is not used to create a polygon
            else if(i == nonAnchor || i >= 650){
                //If that's the case, the next non-anchor index value is set
                nonAnchor += 26;
            }
            //Polygons created in the leftmost column except for polygon 0
            else if(i%26 == 0){
                //Segemnt 0 will already have been created by the polygon above the current one, so it's not created here

                //Getting vertices for segment 1 and creating it
                Vertex v0 = vertices.get(i+1);
                Vertex v1 = vertices.get(i+27);
                Segment seg0 = getSegmentWithColor(v0,v1,i+1,i+27);
                segments.add(seg0);
                //Getting the index of the segment
                int segmentIndx = segments.size()-1;

                //Getting vertices for segment 2 and creating it
                Vertex v2 = vertices.get(i+26);
                Segment seg1 = getSegmentWithColor(v1,v2,i+27,i+26);
                segments.add(seg1);

                //Getting vertices for segment 3 and creating it
                Vertex v3 = vertices.get(i);
                Segment seg2 = getSegmentWithColor(v2,v3,i+26,i);
                segments.add(seg2);

                //Creating centroid
                Vertex centroid = getCentroid((v0.getX() + v3.getX())/2, (v0.getY()+ v1.getY())/2 );
                vertices.add(centroid);

                //Getting centroid index
                int centroidIndx = vertices.indexOf(centroid);

                //Getting the index of the segment above the current polygon to add it as segment 0
                int upperSegIndx = polygons.get(polygonCount-24).getSegmentIdxsList().get(2);

                //Special case if this is the bottom left polygon, since there will be no polygons underneath it as neighbors
                if(polygonCount == 599){
                    //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(segmentIndx+2)
                            .addNeighborIdxs(polygonCount-24).addNeighborIdxs(polygonCount-23)
                            .addNeighborIdxs(polygonCount+2).setCentroidIdx(centroidIndx).build();
                    //Adding the polygon to the polygon ArrayList
                    polygons.add(polygon);
                }
                else{
                    //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(segmentIndx+2)
                            .addNeighborIdxs(polygonCount-24).addNeighborIdxs(polygonCount-23)
                            .addNeighborIdxs(polygonCount+2).addNeighborIdxs(polygonCount+26)
                            .addNeighborIdxs(polygonCount+27).setCentroidIdx(centroidIndx).build();
                    //Adding the polygon to the polygon ArrayList
                    polygons.add(polygon);
                }

                //Iterating the polygon count
                polygonCount++;



            }
            //Final statement is for creating all the polygons not at the top row or the leftmost column
            else{
                //Segemnt 0 will already have been created by the polygon above the current one, so it's not created here

                //Getting vertices for segment 1 and creating it
                Vertex v0 = vertices.get(i+1);
                Vertex v1 = vertices.get(i+27);
                Segment seg0 = getSegmentWithColor(v0,v1,i+1,i+27);
                segments.add(seg0);
                int segmentIndx = segments.size()-1;

                //Getting vertices for segment 2 and creating it
                Vertex v2 = vertices.get(i+26);
                Segment seg1 = getSegmentWithColor(v1,v2,i+27,i+26);
                segments.add(seg1);

                //Segemnt 3 will already have been created by the polygon left to the current one, so it's not created here

                //Creating centroid
                Vertex centroid = getCentroid((v0.getX() + v2.getX())/2, (v0.getY()+ v1.getY())/2 );
                vertices.add(centroid);

                //Getting centroid index
                int centroidIndx = vertices.indexOf(centroid);

                //Getting index for segment 0
                int upperSegIndx = polygons.get(polygonCount-24).getSegmentIdxsList().get(2);

                //Getting index for segment 3
                int leftSegIndx = polygons.get(polygonCount).getSegmentIdxsList().get(1);

                //Special case if this is a polygon at the bottom since there will be no polygons below it as neighbors
                if(polygonCount >= 600){
                    //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(leftSegIndx)
                            .addNeighborIdxs(polygonCount-25).addNeighborIdxs(polygonCount-24)
                            .addNeighborIdxs(polygonCount-23).addNeighborIdxs(polygonCount)
                            .addNeighborIdxs(polygonCount+2).setCentroidIdx(centroidIndx).build();
                    //Adding the polygon to the polygon ArrayList
                    polygons.add(polygon);
                }
                else{
                    //Creating the polygon by: adding segments in order, adding neighbors in order, and setting centroid indx
                    Polygon polygon = Polygon.newBuilder().addSegmentIdxs(upperSegIndx).addSegmentIdxs(segmentIndx)
                            .addSegmentIdxs(segmentIndx+1).addSegmentIdxs(leftSegIndx)
                            .addNeighborIdxs(polygonCount-25).addNeighborIdxs(polygonCount-24)
                            .addNeighborIdxs(polygonCount-23).addNeighborIdxs(polygonCount)
                            .addNeighborIdxs(polygonCount+2).addNeighborIdxs(polygonCount+25)
                            .addNeighborIdxs(polygonCount+26).addNeighborIdxs(polygonCount+27)
                            .setCentroidIdx(centroidIndx).build();
                    //Adding the polygon to the polygon ArrayList
                    polygons.add(polygon);
                }

                //Iterating the polygon count
                polygonCount++;
            }

        }

        //Returning the mesh with the vertices, segments, and polygons added
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

    /*public static void main(String[] args) {
        DotGen dot = new DotGen();

        Mesh aMesh = dot.generate();

        List<Polygon> polygons = aMesh.getPolygonsList();

        for(Polygon polygon: polygons){

            System.out.println("Polygon: "+polygons.indexOf(polygon));
            System.out.println("Segments: "+ polygon.getSegmentIdxs(0)+", "+polygon.getSegmentIdxs(1)+", "+polygon.getSegmentIdxs(2)+", "+polygon.getSegmentIdxs(3));
            System.out.println("Neighbors: ");
            for(int neighbor: polygon.getNeighborIdxsList()){
                System.out.print(neighbor+" ");
            }
            System.out.println();
            System.out.println();
        }
    }*/

}
