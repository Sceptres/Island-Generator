package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.*;

public class PolygonNeighborFinder {

    private final List<Polygon> polygons;

    public PolygonNeighborFinder(List<Polygon> polygons) {
        this.polygons = new ArrayList<>(polygons);
    }

    /**
     *
     * @return An {@link Map} with a mapping of each polygon and its neighbors
     */
    public Map<Polygon, List<Polygon>> findNeighbors(){
        Map<Polygon, List<Polygon>> neighborRelation = new HashMap<>();

        // Calculate triangulation
        List<org.locationtech.jts.geom.Polygon> triangulation = this.getDelaunayTriangulation();

        for(Polygon polygon : this.polygons) {
            List<Polygon> neighbors = this.getPolygonNeighbors(triangulation, polygon);
            neighborRelation.put(polygon, neighbors);
        }

        return neighborRelation;
    }

    private List<org.locationtech.jts.geom.Polygon> getDelaunayTriangulation() {
        List<Coordinate> centroidCoordinates = this.polygons.stream().map(p -> new Coordinate(p.getX(), p.getY())).toList();

        // Triangulation builder
        DelaunayTriangulationBuilder delaunayTriangulationBuilder = new DelaunayTriangulationBuilder();
        delaunayTriangulationBuilder.setSites(centroidCoordinates);

        // Get the triangulation result
        GeometryFactory geometryFactory = new GeometryFactory();
        Geometry triangulation = delaunayTriangulationBuilder.getTriangles(geometryFactory);
        GeometryCollection g = (GeometryCollection) triangulation;

        // Collect triangles
        List<org.locationtech.jts.geom.Polygon> jtsTriangles = new ArrayList<>();
        for(int i = 0; i < g.getNumGeometries(); i++) {
            org.locationtech.jts.geom.Polygon polygon = (org.locationtech.jts.geom.Polygon) g.getGeometryN(i);
            jtsTriangles.add(polygon);
        }

        return jtsTriangles;
    }

    /**
     *
     * @param coord The {@link Coordinate} to compare with the {@link Vertex}
     * @param vertex The {@link Vertex} to compare with the {@link Coordinate}
     * @return Whether the {@link Coordinate} and {@link Vertex} are equal
     */
    private boolean coordEqualsVertex(Coordinate coord, Vertex vertex) {
        return coord.getX() == vertex.getX() && coord.getY() == vertex.getY();
    }

    /**
     *
     * @param triangulation The result of the delaunay triangulation
     * @param polygon The {@link Polygon} to find the neighbors of
     * @return The neighbors of the given {@link Polygon}
     */
    private List<Polygon> getPolygonNeighbors(
            List<org.locationtech.jts.geom.Polygon> triangulation,
            Polygon polygon
    ) {
        Vertex centroid = polygon.getCentroid();

        List<Coordinate> neighborCentroids =  triangulation.stream()
                .filter(p ->  Arrays.stream(p.getCoordinates()).anyMatch(c -> this.coordEqualsVertex(c, centroid)))
                .flatMap(p -> Arrays.stream(p.getCoordinates()))
                .filter(c -> !this.coordEqualsVertex(c, centroid))
                .distinct()
                .toList();

        return this.polygons.stream().filter(p -> {
            Vertex pCentroid = p.getCentroid();
            return !pCentroid.equals(centroid) && neighborCentroids.stream().anyMatch(c -> this.coordEqualsVertex(c, pCentroid));
        }).toList();
    }
}
