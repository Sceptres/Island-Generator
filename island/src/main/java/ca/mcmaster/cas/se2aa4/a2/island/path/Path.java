package ca.mcmaster.cas.se2aa4.a2.island.path;

import ca.mcmaster.cas.se2aa4.a2.island.elevation.IElevation;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.ElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.handler.handlers.PathElevationHandler;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.profiles.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.path.type.PathType;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.Objects;

public final class Path implements IElevation {
    private PathType type;
    private final Segment segment;
    private final ElevationProfile elevationProfile;
    private final ElevationHandler elevationHandler;

    public Path(Segment segment) {
        this.segment = segment;
        this.setType(PathType.NONE);
        this.setWidth(1f);
        this.elevationProfile = new ElevationProfile();
        this.elevationHandler = new PathElevationHandler();

        this.elevationProfile.setElevation(1);
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
        if(width > 3f)
            this.segment.setThickness(3f);
        else
            this.segment.setThickness(Math.max(width, 1f));
    }

    /**
     *
     * @param increment The amount to increase the width of this path by
     */
    public void addWidth(float increment) {
        float oldWidth = this.getWidth();
        float newWidth = oldWidth + increment;
        this.setWidth(newWidth);
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

    @Override
    public double getElevation() {
        return this.elevationProfile.getElevation();
    }

    @Override
    public void setElevation(double elevation) {
        this.elevationHandler.takeElevation(this.elevationProfile, elevation);
    }

    /**
     *
     * @param path The {@link Path} to check connection with
     * @return True if the 2 paths are connected. False otherwise.
     */
    public boolean isConnected(Path path) {
        return this.segment.shareVertex(path.segment);
    }

    /**
     *
     * @param path The {@link Path} to check connection with
     * @param vertex An end of the path to check connection to
     * @return True if both paths are connected through the given vertex. False otherwise.
     */
    public boolean isConnected(Path path, Vertex vertex) {
        return this.segment.shareVertex(path.segment) && this.segment.getSharedVertex(path.segment).equals(vertex);
    }

    /**
     *
     * @param vertex The {@link Vertex} to check if this path has
     * @return True if this path contains the vertex. False otherwise
     */
    public boolean hasVertex(Vertex vertex) {
        return this.getV1().equals(vertex) || this.getV2().equals(vertex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path path)) return false;
        return type == path.type && Objects.equals(segment, path.segment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, segment);
    }
}
