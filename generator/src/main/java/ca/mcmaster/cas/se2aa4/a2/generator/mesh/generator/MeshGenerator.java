package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public interface MeshGenerator {
    /**
     *
     * @param mesh The mesh to add generated data to.
     */
    void generate(Mesh mesh);

    /**
     *
     * @return The width of the mesh to generate
     */
    int getWidth();

    /**
     *
     * @return The height of the mesh to generate
     */
    int getHeight();
}
