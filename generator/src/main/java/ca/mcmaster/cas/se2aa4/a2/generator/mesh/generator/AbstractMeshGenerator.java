package ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public abstract class AbstractMeshGenerator implements MeshGenerator {

    private final float verticesThickness;
    private final float segmentsThickness;
    private final int width;
    private final int height;
    private final ColorGenerator vertexColorGenerator;
    private final ColorGenerator segmentColorGenerator;
    private final ColorGenerator polygonColorGenerator;

    /**
     *
     * @param generators The array of color generators [vertex, segments, polygons]
     * @param thickness The thickness of the vertices and segments [vertex thickness, segment thickness]
     * @param dimensions The dimensions of the mesh. [width, height]
     */
    protected AbstractMeshGenerator(ColorGenerator[] generators, float[] thickness, int[] dimensions) {
        if(dimensions[0] <= 0 || dimensions[1] <= 0)
            throw new IllegalArgumentException("Cannot have a dimension less than or equal to 0!");
        this.verticesThickness = (float) Util.precision(thickness[0]);
        this.segmentsThickness = (float) Util.precision(thickness[1]);
        this.width = dimensions[0];
        this.height = dimensions[1];
        this.vertexColorGenerator = generators[0];
        this.segmentColorGenerator = generators[1];
        this.polygonColorGenerator = generators[2];
    }

    /**
     *
     * @return The thickness, diameter, of the vertices
     */
    protected float getVerticesThickness(){
        return verticesThickness;
    }

    /**
     *
     * @return The thickness of the segments
     */
    protected float getSegmentsThickness(){
        return segmentsThickness;
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
