package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.InvalidPolygonNumberException;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.AbstractMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry.JTSMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry.LloydRelaxation;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry.PolygonNeighborFinder;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.geometry.VoronoiDiagram;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class IrregularMeshGenerator extends AbstractMeshGenerator {

    private final int numPolygons;
    private final JTSMesh mesh;
    private final VoronoiDiagram voronoi;
    private final LloydRelaxation relaxation;

    /**
     * @param generators The array of color generators [vertex, segments, polygons]
     * @param thickness  The thickness of the vertices and segments [vertex thickness, segment thickness]
     * @param dimensions The dimensions of the mesh. [width, height]
     */
    public IrregularMeshGenerator(
            ColorGenerator[] generators,
            float[] thickness,
            int[] dimensions,
            int relaxationLevel,
            int numPolygons
    ) {
        super(generators, thickness, dimensions);

        if(numPolygons < 2)
            throw new InvalidPolygonNumberException();
        this.mesh = new JTSMesh();
        this.voronoi = new VoronoiDiagram(super.getWidth(), super.getHeight());
        this.relaxation = new LloydRelaxation(this.voronoi, relaxationLevel);
        this.numPolygons = numPolygons;
    }

    @Override
    public void generate(Mesh mesh) {
        List<Coordinate> randomPoints = this.generateRandomPoints();

        this.mesh.putPolygons(this.voronoi.generateDiagram(randomPoints, 100));
        this.mesh.putPolygons(this.relaxation.apply(this.mesh.getPolygons()));

        this.addToMesh(mesh);

        // Calculate the polygons
        PolygonNeighborFinder neighborFinder = new PolygonNeighborFinder(mesh.getPolygons());
        Map<Polygon, List<Polygon>> neighborRelation = neighborFinder.findNeighbors();

        // Add neighbors for each polygon
        neighborRelation.forEach(Polygon::addNeighbors);
    }

    /**
     *
     * @return The {@link List} of randomly generated {@link Coordinate}
     */
    private List<Coordinate> generateRandomPoints(){
        //Random object
        Random rnd = new Random(System.currentTimeMillis()+System.nanoTime());

        //Width and height of the mesh
        int width = super.getWidth();
        int height = super.getHeight();

        List<Coordinate> coordinates = new ArrayList<>();

        //Loop to generate specified amount of points, each corresponding to a polygon
        while(coordinates.size() != this.numPolygons) {
            //Setting the x and y coordinates of the vertex randomly
            double x = rnd.nextDouble(0, width);
            double y = rnd.nextDouble(0, height);

            //Creating the new vertex object
            Coordinate coordinate = new Coordinate(x, y);

            if(coordinates.contains(coordinate))
                continue;

            //Trying to add the vertex and storing a boolean of whether it was added or not
            coordinates.add(coordinate);
        }

        //Return the list of vertices
        return coordinates;
    }

    /**
     *
     * @param mesh The {@link Mesh} to add the elements to
     */
    private void addToMesh(Mesh mesh) {
        Mesh generatedMesh = this.mesh.getConverted();

        // Configure and add vertices
        generatedMesh.getVertices().forEach(v -> {
            super.getVertexColorGenerator().generateColor(v);
            v.setThickness(super.getVerticesThickness());
            mesh.addVertex(v);
        });

        // Configure and add segments
        generatedMesh.getSegments().forEach(s -> {
            super.getSegmentColorGenerator().generateColor(s);
            s.setThickness(super.getSegmentsThickness());
            mesh.addSegment(s);
        });

        // Configure and add polygons
        generatedMesh.getPolygons().forEach(p -> {
            super.getPolygonColorGenerator().generateColor(p);
            mesh.addPolygon(p);
            mesh.addVertex(p.getCentroid());
        });
    }
}