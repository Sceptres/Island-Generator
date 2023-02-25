package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygons;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertices;
import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JTSMesh implements Converter<Mesh> {

    private final List<org.locationtech.jts.geom.Polygon> jtsPolygons;
    private final Vertices vertices;
    private final Segments segments;
    private final Polygons polygons;

    public JTSMesh() {
        this.jtsPolygons = new ArrayList<>();
        this.vertices = new Vertices();
        this.segments = new Segments();
        this.polygons = new Polygons();
    }

    /**
     *
     * @param polygons The {@link org.locationtech.jts.geom.Polygon} to put into the mesh
     */
    public void putPolygons(List<org.locationtech.jts.geom.Polygon> polygons) {
        this.jtsPolygons.clear();
        this.jtsPolygons.addAll(polygons);
    }

    /**
     *
     * @return A {@link List} with all the elements of {@link JTSMesh#jtsPolygons}
     */
    public List<org.locationtech.jts.geom.Polygon> getPolygons() {
        return new ArrayList<>(this.jtsPolygons);
    }

    @Override
    public Mesh getConverted() {
        this.generatePolygons(this.jtsPolygons);
        return new Mesh(this.polygons, this.segments, this.vertices);
    }

    /**
     *
     * @param coordinates The coordinates to convert to {@link Vertex}
     * @return The list of {@link Vertex} extracted from {@link Coordinate}
     */
    private List<Vertex> convertCoordinatesToVertices(Coordinate[] coordinates) {
        return Arrays.stream(coordinates).map(c -> {
            Vertex v = new Vertex(c.getX(), c.getY());
            this.vertices.add(v);
            return v;
        }).toList();
    }

    /**
     *
     * @param vertices The group of {@link Vertex} to form {@link Segment} from
     * @return The list of {@link Segment} connecting adjacent vertices
     */
    private List<Segment> generateSegments(List<Vertex> vertices) {
        List<Segment> segments = new ArrayList<>();

        int numVertices = vertices.size();

        for(int i=0; i < numVertices; i++) {
            // Get segment vertices
            Vertex v1 = vertices.get(i%numVertices);
            Vertex v2 = vertices.get((i+1)%numVertices);

            if(v1.equals(v2)) // Ignore segments that are of length 0
                continue;

            // Add segment
            Segment segment = new Segment(v1, v2);
            this.segments.add(segment);
            segments.add(segment);
        }

        return segments;
    }

    /**
     *
     * @param jtsPolygons The {@list List} of generated polygons
     */
    private void generatePolygons(List<org.locationtech.jts.geom.Polygon> jtsPolygons) {
        for(org.locationtech.jts.geom.Polygon jtsPolygon : jtsPolygons) {
            org.locationtech.jts.geom.Polygon convexedPolygon = (org.locationtech.jts.geom.Polygon) jtsPolygon.convexHull();

            List<Vertex> vertices = this.convertCoordinatesToVertices(convexedPolygon.getCoordinates());
            List<Segment> segments = this.generateSegments(vertices);

            // Configure polygon
            Polygon polygon = new Polygon(segments);

            // Add polygon
            this.polygons.add(polygon);
        }
    }
}
