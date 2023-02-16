package ca.mcmaster.cas.se2aa4.a2.mesh.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygons;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ThicknessProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.IProperties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertices;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Util {
    /**
     *
     * @param t The element to get color property from
     * @return The color
     */
    public static <T extends IProperties> double extractThickness(T t) {
        Property property = t.getProperty(ThicknessProperty.KEY);
        if(Objects.isNull(property))
            return 2;

        // Get thickness
        double x = Double.parseDouble(property.getValue());
        return x;
    }

    public static <T extends IProperties> Color extractColor(T t) {
        Property property = t.getProperty(ColorProperty.KEY);
        if(Objects.isNull(property))
            return Color.BLACK;

        if(!property.getKey().equals(ColorProperty.KEY)) // Not the color property?
            throw new IllegalArgumentException("This property is not a color property!");

        // Get color
        String[] rgbStr = property.getValue().split(",");
        int r = Integer.parseInt(rgbStr[0]);
        int g = Integer.parseInt(rgbStr[1]);
        int b = Integer.parseInt(rgbStr[2]);
        int alpha = Integer.parseInt(rgbStr[3]);
        return new Color(r, g, b, alpha);
    }

    /**
     *
     * @param structProperties The group of {@link Structs.Property} instances to convert to {@link Property}
     * @return A list of all the equivalent {@link Property} instances
     */
    public static Properties toProperties(List<? extends Structs.Property> structProperties) {
        Properties properties = new Properties();
        structProperties.forEach(p -> properties.add(new Property(p)));

        return properties;
    }

    /**
     *
     * @param segments The list of {@link Structs.Segment} to convert to {@link Segment}
     * @param vertices The list of {@link Structs.Vertex} that match segment vertices
     * @return A {@link Segments} list with all the {@link Segment} instances
     */
    public static Segments toSegments(List<? extends Structs.Segment> segments, List<? extends Structs.Vertex> vertices) {
        Vertices verticesList = Util.toVertices(vertices);
        return Util.toSegmentsVertex(segments, verticesList);
    }

    /**
     *
     * @param segments The list of {@link Structs.Segment} to convert to {@link Segment}
     * @param vertices The list of {@link Vertex} that match segment vertices
     * @return A {@link Segments} list with all the {@link Segment} instances
     */
    public static Segments toSegmentsVertex(List<? extends Structs.Segment> segments, List<? extends Vertex> vertices) {
        Segments segmentsList = new Segments();

        for (Structs.Segment structsSegment : segments) {
            // Get vertices for this segment
            Vertex v1 = vertices.get(structsSegment.getV1Idx());
            Vertex v2 = vertices.get(structsSegment.getV2Idx());

            // Add segment
            segmentsList.add(new Segment(structsSegment, v1, v2));
        }

        return segmentsList;
    }

    /**
     *
     * @param vertices The list of {@link Structs.Vertex} instances to convert to {@link Vertex}
     * @return The {@link Vertices} list with all the {@link Vertex} instances
     */
    public static Vertices toVertices(List<? extends Structs.Vertex> vertices) {
        Vertices verticesList = new Vertices();
        vertices.forEach(v -> verticesList.add(new Vertex(v)));

        return verticesList;
    }

    /**
     *
     * @param polygons The list {@link Structs.Polygon} to wrap
     * @param segments The list of all {@link Segment}
     * @param vertices The list of all {@link Vertex}
     * @return A list {@link Polygons} with equivalent polygons
     */
    public static Polygons toPolygons(
            List<? extends Structs.Polygon> polygons,
            List<? extends Segment> segments,
            List<? extends Vertex> vertices) {
        Polygons polygonsList = new Polygons();

        // Create instances
        polygons.forEach(p -> {
            List<? extends Segment> polygonSegments = p.getSegmentIdxsList().stream().map(segments::get).toList();
            Polygon polygon = new Polygon(p, polygonSegments, vertices.get(p.getCentroidIdx()));

            polygonsList.add(polygon);
        });

        // Set neighbors
        for(int i=0; i < polygons.size(); i++) {
            // Get neighbors
            Structs.Polygon p = polygons.get(i);
            List<Polygon> neighbors = p.getNeighborIdxsList().stream().map(polygonsList::get).toList();

            // Add neighbors
            Polygon polygon = polygonsList.get(i);
            polygon.addNeighbors(neighbors);
        }

        return polygonsList;
    }

    /**
     *
     * @param polygons The list of {@link Structs.Polygon} to wrap into {@link Polygon}
     * @param segments The list of {@link Structs.Segment} with all the segments
     * @param vertices The list of {@link Structs.Vertex} with all the vertices
     * @return A list {@link Polygons} of equivalent polygons
     */
    public static Polygons toPolygonsFromStruct(
            List<? extends Structs.Polygon> polygons,
            List<? extends Structs.Segment> segments,
            List<? extends Structs.Vertex> vertices
    ) {
        List<Vertex> vertexList = Util.toVertices(vertices);
        List<Segment> segmentList = Util.toSegmentsVertex(segments, vertexList);
        return Util.toPolygons(polygons, segmentList, vertexList);
    }

    /**
     *
     * @param x double that you want to round to 2 decimal places
     * @return double value in 2 decimal
     */
    public static double precision(double x){
        DecimalFormat df=new DecimalFormat("0.00");
        String formate = df.format(x);
        double finalValue = Double.parseDouble(formate);
        return finalValue;
    }
}
