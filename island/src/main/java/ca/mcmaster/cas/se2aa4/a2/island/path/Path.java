package ca.mcmaster.cas.se2aa4.a2.island.path;

import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

public final class Path {
    private PathType type;
    private final Segment segment;

    public Path(Segment segment) {
        this.segment = segment;
        this.setType(PathType.NONE);
    }

    /**
     *
     * @return The first {@link Vertex} of the path
     */
    public Vertex getV1() {
        return this.segment.getV1();
    }

    /**
     *
     * @return The second {@link Vertex} of the path
     */
    public Vertex getV2() {
        return this.segment.getV2();
    }

    /**
     *
     * @return The width of this path
     */
    public float getWidth() {
        return this.segment.getThickness();
    }

    /**
     *
     * @param width The new width of this path
     */
    public void setWidth(float width) {
        this.segment.setThickness(width);
    }

    /**
     *
     * @param increment The amount to increase the width of this path by
     */
    public void addWidth(float increment) {
        float oldWidth = this.getWidth();
        this.segment.setThickness(oldWidth + increment);
    }

    /**
     *
     * @return The {@link PathType} of this path
     */
    public PathType getType() {
        return this.type;
    }

    /**
     *
     * @param type The {@link PathType} to set this path to
     */
    public void setType(PathType type) {
        this.type = type;
        this.segment.setColor(this.type.getColorGenerator().generateColor());
    }
}
