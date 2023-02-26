package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public class DotGen {

    private final MeshGenerator generator;

    public DotGen(MeshGenerator generator) {
        this.generator = generator;
    }

    public Structs.Mesh generate() {
        Mesh mesh = new Mesh();

        // Get mesh dimensions
        int width = this.generator.getWidth();
        int height = this.generator.getHeight();

        // Create mesh dimension
        int[] dimensions = new int[2];
        dimensions[0] = width;
        dimensions[1] = height;

        // Set mesh dimensions
        mesh.setDimension(dimensions);
        // Generate mesh
        this.generator.generate(mesh);

        return mesh.getConverted();
    }
}