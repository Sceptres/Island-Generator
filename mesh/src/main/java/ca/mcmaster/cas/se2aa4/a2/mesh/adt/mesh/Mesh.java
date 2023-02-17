package ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygons;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertices;

import java.util.ArrayList;
import java.util.List;

public class Mesh implements Converter<Structs.Mesh> {
    private final Vertices vertices;
    private final Segments segments;
    private final Polygons polygons;

    public Mesh() {
        this.vertices = new Vertices();
        this.segments = new Segments();
        this.polygons = new Polygons();
    }

    public Mesh(Polygons polygons, Segments segments, Vertices vertices) {
        this.polygons = polygons;
        this.segments = segments;
        this.vertices = vertices;
    }

    public Mesh(Structs.Mesh mesh) {
        this.vertices = Util.toVertices(mesh.getVerticesList());
        this.segments = Util.toSegmentsVertex(mesh.getSegmentsList(), this.vertices);
        this.polygons = Util.toPolygons(mesh.getPolygonsList(), this.segments, this.vertices);
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
                .build();
    }
}
