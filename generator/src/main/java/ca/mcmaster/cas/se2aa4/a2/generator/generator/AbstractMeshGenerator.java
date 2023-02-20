package ca.mcmaster.cas.se2aa4.a2.generator.generator;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public abstract class AbstractMeshGenerator implements MeshGenerator {

    private final int width;
    private final int height;

    /**
     *
     * @param width The width of the mesh to generate
     * @param height The height of the mesh to generate
     */
    protected AbstractMeshGenerator(int width, int height) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException("Cannot have a dimension less than or equal to 0!");

        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public abstract void generate(Mesh mesh);
}
