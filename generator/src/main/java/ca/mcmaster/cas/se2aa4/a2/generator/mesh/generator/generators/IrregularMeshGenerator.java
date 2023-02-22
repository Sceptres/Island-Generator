package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.RandomColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.AbstractMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertices;
import org.locationtech.jts.shape.random.RandomPointsBuilder;
import org.locationtech.jts.shape.random.RandomPointsInGridBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.List;
import java.util.Random;

public class IrregularMeshGenerator extends AbstractMeshGenerator {


    /**
     * @param generators The array of color generators [vertex, segments, polygons]
     * @param thickness  The thickness of the vertices and segments [vertex thickness, segment thickness]
     * @param dimensions The dimensions of the mesh. [width, height]
     */
    public IrregularMeshGenerator(ColorGenerator[] generators, float[] thickness, int[] dimensions) {
        super(generators, thickness, dimensions);
    }

    @Override
    public void generate(Mesh mesh) {
        mesh.addAllVertices(generateRandomPoints());
    }

    private List<Vertex> generateRandomPoints(){

        //Random object
        Random rnd = new Random();

        //New ArrayList of vertices
        Vertices vertices = new Vertices();

        //Width and height of the mesh
        int width = super.getWidth();
        int height = super.getHeight();

        //Getting the thickness of the vertices
        float vertexThickness = super.getVerticesThickness();

        //Loop to generate specified amount of points, each corresponding to a polygon
        for(int i = 0; i < 100; i++){
            //Setting the x and y coordinates of the vertex randomly
            double x = rnd.nextDouble(width+1);
            double y = rnd.nextDouble(height+1);

            //Creating the new vertex object
            Vertex vertex = new Vertex(x, y);

            //Setting the thickness of the vertex
            vertex.setThickness(vertexThickness);

            //Setting the color of the vertex
            super.getVertexColorGenerator().generateColor(vertex);

            //Trying to add the vertex and storing a boolean of whether it was added or not
            boolean added = vertices.add(vertex);

            //If the vertex was not added
            if(!added){
                //Decrement the loop to add another vertex
                i--;
            }
        }

        //Return the list of vertices
        return vertices;
    }

}
