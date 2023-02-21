package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.generator.MeshGenerator;
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