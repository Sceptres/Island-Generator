package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.List;

public class VoronoiDiagram {

    private VoronoiDiagramBuilder voronoi;
    private final Envelope envelope;

    public VoronoiDiagram(int width, int height) {
        this.envelope = new Envelope(0, width, 0, height);

        this.voronoi = new VoronoiDiagramBuilder();
        this.voronoi.setClipEnvelope(this.envelope);
    }

    /**
     *
     * @param coordinates The coordinates to apply voronoi diagram on
     * @return The generated polygons in the voronoi diagram
     */
    public List<Polygon> generateDiagram(List<Coordinate> coordinates, double precision){
        this.voronoi.setSites(coordinates);

        // Get generated geometry
        GeometryFactory geometryFactory = new GeometryFactory();
        geometryFactory.getPrecisionModel().makePrecise(precision);

        // Get the geometry of the generated diagram
        Geometry geometry = this.voronoi.getDiagram(geometryFactory);
        GeometryCollection geometryCollection = (GeometryCollection) geometry;

        // Collect generated polygons
        List<Polygon> polygons = new ArrayList<>();
        for(int i = 0; i < geometryCollection.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) geometryCollection.getGeometryN(i);
            polygons.add(polygon);
        }

        // Reset the diagram builder
        this.reset();

        return polygons;
    }

    /**
     * Reset the diagram builder to allow for regeneration
     */
    public void reset() {
        this.voronoi = new VoronoiDiagramBuilder();
        this.voronoi.setClipEnvelope(this.envelope);
    }
}
