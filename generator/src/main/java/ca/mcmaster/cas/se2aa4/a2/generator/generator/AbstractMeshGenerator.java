package ca.mcmaster.cas.se2aa4.a2.generator.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public abstract class AbstractMeshGenerator implements MeshGenerator {

    private final int width;
    private final int height;
    private final ColorGenerator vertexColorGenerator;
    private final ColorGenerator segmentColorGenerator;
    private final ColorGenerator polygonColorGenerator;

    /**
     *
     * @param width The width of the mesh to generate
     * @param height The height of the mesh to generate
     */
    protected AbstractMeshGenerator(ColorGenerator[] generators, int width, int height) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException("Cannot have a dimension less than or equal to 0!");

        this.width = width;
        this.height = height;
        this.vertexColorGenerator = generators[0];
        this.segmentColorGenerator = generators[1];
        this.polygonColorGenerator = generators[2];
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     *
     * @return The {@link ColorGenerator} used to generate the color of the vertices
     */
    protected ColorGenerator getVertexColorGenerator() {
        return this.vertexColorGenerator;
    }

    /**
     *
     * @return The {@link ColorGenerator} used to generate the color of the segments
     */
    protected ColorGenerator getSegmentColorGenerator() {
        return this.segmentColorGenerator;
    }

    /**
     *
     * @return The {@link ColorGenerator} used to generate the color of the polygons
     */
    protected ColorGenerator getPolygonColorGenerator() {
        return this.polygonColorGenerator;
    }

    @Override
    public abstract void generate(Mesh mesh);
}
