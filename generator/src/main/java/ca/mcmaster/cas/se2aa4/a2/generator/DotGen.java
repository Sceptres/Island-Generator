package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.RandomColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;

public class DotGen {

    private final MeshGenerator generator;

    public DotGen(MeshGenerator generator) {
        this.generator = generator;
    }

    public Structs.Mesh generate() {
        Mesh mesh = new Mesh();
        
        int width = this.generator.getWidth();
        int height = this.generator.getHeight();
        
        int[] dimensions = new int[2];
        
        dimensions[0] = width;
        dimensions[1] = height;

        mesh.setDimension(dimensions);
        // Generate mesh
        this.generator.generate(mesh);

        // Match neighboring polygons
        for(Polygon p1 : mesh.getPolygons()) {
            for(Polygon p2 : mesh.getPolygons()) {
                p1.addNeighbor(p2);
            }
        }

        return mesh.getConverted();
    }
}