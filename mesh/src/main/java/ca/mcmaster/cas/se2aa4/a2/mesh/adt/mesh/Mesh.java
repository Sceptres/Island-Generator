package ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygons;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.DimensionProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.IProperties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertices;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Mesh implements IProperties, Converter<Structs.Mesh> {
    private final Vertices vertices;
    private final Segments segments;
    private final Polygons polygons;
    private final Properties properties;

    public Mesh() {
        this.vertices = new Vertices();
        this.segments = new Segments();
        this.polygons = new Polygons();
        this.properties = new Properties();
    }

    public Mesh(Polygons polygons, Segments segments, Vertices vertices) {
        this.polygons = polygons;
        this.segments = segments;
        this.vertices = vertices;
        this.properties = new Properties();
    }

    public Mesh(Structs.Mesh mesh) {
        this.vertices = Util.toVertices(mesh.getVerticesList());
        this.segments = Util.toSegmentsVertex(mesh.getSegmentsList(), this.vertices);
        this.polygons = Util.toPolygons(mesh.getPolygonsList(), this.segments, this.vertices);
        this.properties = Util.toProperties(mesh.getPropertiesList());
    }

    /**
     *
     * @return A {@link List} with all the {@link Vertex} of the mesh
     */
    public List<Vertex> getVertices() {
        return new ArrayList<>(this.vertices);
    }

    /**
     *
     * @return A {@link List} of all {@link Vertex} that are not centroids
     */
    public List<Vertex> getNonCentroidVertices() {
        return this.vertices.stream().filter(v -> !v.isCentroid()).toList();
    }

    /**
     *
     * @return A {@link List} of all {@link Vertex} that are centroids
     */
    public List<Vertex> getCentroidVertices() {
        return this.vertices.stream().filter(Vertex::isCentroid).toList();
    }

    /**
     *
     * @return A {@link List} with all the {@link Segment} of the mesh
     */
    public List<Segment> getSegments() {
        return new ArrayList<>(this.segments);
    }

    /**
     *
     * @return A {@link List} with all the {@link Polygon} of the mesh
     */
    public List<Polygon> getPolygons() {
        return new ArrayList<>(this.polygons);
    }

    /**
     *
     * @param vertex The {@link Vertex} to add to the mesh
     */
    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    /**
     *
     * @param vertices The {@link List} of {@link Vertex} to add to the mesh
     */
    public void addAllVertices(List<? extends Vertex> vertices) {
        vertices.forEach(this::addVertex);
    }

    /**
     *
     * @param segment The {@link Segment} to add to the mesh
     */
    public void addSegment(Segment segment) {
        this.segments.add(segment);
    }

    /**
     *
     * @param segments The {@link List} of {@link Segment} to add to the mesh
     */
    public void addAllSegments(List<? extends Segment> segments) {
        segments.forEach(this::addSegment);
    }

    /**
     *
     * @param polygon The {@link Polygon} to add to the mesh
     */
    public void addPolygon(Polygon polygon) {
        this.polygons.add(polygon);
    }

    /**
     *
     * @param polygons The {@link List} of {@link Polygon} to add to the mesh
     */
    public void addAllPolygons(List<? extends Polygon> polygons) {
        polygons.forEach(this::addPolygon);
    }

    @Override
    public Structs.Mesh getConverted() {
        return Structs.Mesh.newBuilder().addAllVertices(this.vertices.getConverted())
                .addAllSegments(this.segments.getConverted())
                .addAllPolygons(this.polygons.getConverted())
                .addAllProperties(this.properties.getConverted())
                .build();
    }

    @Override
    public void addProperty(Property property) {
        this.properties.add(property);
    }

    @Override
    public void addAllProperties(Iterable<? extends Property> properties) {
        properties.forEach(this::addProperty);
    }

    @Override
    public Property getProperty(String key) {
        Optional<Property> property = this.properties.stream().filter(p -> p.getKey().equals(key)).findFirst();
        return property.orElse(null);
    }

    @Override
    public List<Property> getProperties() {
        return new ArrayList<>(this.properties);
    }

    public void setDimension(int[] dimensions) {
        Property property = new DimensionProperty(dimensions);
        this.addProperty(property);
    }

    public int[] getDimension() {

        String[] dimensionStr = this.getProperty(DimensionProperty.KEY).getValue().split("x");
        return Arrays.stream(dimensionStr).mapToInt(Integer::parseInt).toArray();

    }
}
