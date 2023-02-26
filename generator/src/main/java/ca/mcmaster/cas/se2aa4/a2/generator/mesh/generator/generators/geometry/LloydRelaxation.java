package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.List;

public class LloydRelaxation {

    private final int relaxationLevel;
    private final VoronoiDiagram voronoi;

    public LloydRelaxation(VoronoiDiagram voronoi, int relaxationLevel){
        this.voronoi = voronoi;
        this.relaxationLevel = relaxationLevel;
    }

    /**
     *
     * @param polygons The {@link Polygon} to apply lloyd's relaxation on
     * @return The list of {@link Polygon} after application of lloyd relaxation
     */
    public List<Polygon> apply(List<Polygon> polygons){
        List<Polygon> relaxedPolygons = new ArrayList<>(polygons);

        for(int i = 0; i < this.relaxationLevel; i++) {
            List<Coordinate> centroids = relaxedPolygons.stream().map(p -> p.getCentroid().getCoordinate()).toList();
            relaxedPolygons = this.voronoi.generateDiagram(centroids, 100);
        }

        return relaxedPolygons;
    }
}
