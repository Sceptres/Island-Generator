package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ThicknessProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.*;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class Segment implements Indexable,Thickenable, IProperties, Renderable, Colorable, Copier<Segment>, Converter<Structs.Segment> {

    private final Vertex v1;
    private final Vertex v2;
    private boolean wasRendered;
    private final Properties properties;
    private int index;

    /**
     *
     * @param v1 The first vertex of the segment. It is the vertex on the left side of the segment
     * @param v2 The second vertex of the segment. It is the vertex on the right side of the segment.
     */
    public Segment(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.properties = new Properties();
        this.setColor(Util.generateRandomColor(false));
        this.setThickness(0.5f);
        this.index = -1;
    }

    /**
     *
     * @param segment The {@link Structs.Segment} instance to wrap
     * @param v1 The left/up vertex of the segment
     * @param v2 The right/down vertex of the segment
     */
    public Segment(Structs.Segment segment, Structs.Vertex v1, Structs.Vertex v2) {
        this(segment, new Vertex(v1), new Vertex(v2));
    }

    /**
     *
     * @param segment The {@link Structs.Segment} instance to wrap
     * @param v1 The left/up vertex of the segment
     * @param v2 The right/down vertex of the segment
     */
    public Segment(Structs.Segment segment, Vertex v1, Vertex v2) {
        this(v1, v2);

        List<Property> properties = Util.toProperties(segment.getPropertiesList());
        this.addAllProperties(properties);
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     *
     * @return The index of the first {@link Structs.Vertex} of the segment
     */
    public Vertex getV1() {
        return this.v1;
    }

    /**
     *
     * @return The index of the second {@link Structs.Vertex} of the segment
     */
    public Vertex getV2() {
        return this.v2;
    }

    @Override
    public List<Property> getProperties() {
        return new ArrayList<>(this.properties);
    }

    @Override
    public Property getProperty(String key) {
        Optional<Property> property = this.properties.stream().filter(p -> p.getKey().equals(key)).findFirst();
        return property.orElse(null);
    }

    @Override
    public void setIndex(int index) {
        if(this.index == -1 && index >= 0)
            this.index = index;
    }

    @Override
    public void addProperty(Property p) {
        this.properties.add(p);
    }

    @Override
    public void addAllProperties(Iterable<? extends Property> properties) {
        properties.forEach(this::addProperty);
    }

    @Override
    public Structs.Segment getConverted() {
        return Structs.Segment.newBuilder().setV1Idx(this.v1.getIndex()).setV2Idx(this.v2.getIndex())
                .addAllProperties(this.properties.getConverted()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(v1, segment.v1) && Objects.equals(v2, segment.v2) ||
                Objects.equals(v1, segment.v2) && Objects.equals(v2, segment.v1);
    }

    @Override
    public void setColor(Color color) {
        Property property = new ColorProperty(color);
        this.addProperty(property);
    }

    @Override
    public Color getColor() {
        return Util.extractColor(this);
    }

    @Override
    public void draw(Graphics2D canvas) {
        if(!this.wasRendered) {
            canvas.setColor(this.getColor());
            canvas.setStroke(new BasicStroke(this.getThickness()));
            canvas.draw(new Line2D.Double(v1.getX(), v1.getY(), v2.getX(), v2.getY()));

            this.wasRendered = true;
        }
    }

    /**
     *
     * @param segment The segment to check with
     * @return True if this segment and the given segment share a vertex meaning they are connected. False otherwise.
     */
    public boolean shareVertex(Segment segment) {
        return this.v1.equals(segment.v1) || this.v1.equals(segment.v2) ||
                this.v2.equals(segment.v1) || this.v2.equals(segment.v2);
    }

    @Override
    public void copy(Segment o) {
        if(this.v1.equals(o.v2) && this.v2.equals(o.v1)) { // Opposite order of segment?
            this.v1.copy(o.v2);
            this.v2.copy(o.v1);
        } else { // Correct order or ignore segment order
            this.v1.copy(o.v1);
            this.v2.copy(o.v2);
        }
        this.wasRendered = o.wasRendered;
        this.properties.copy(o.properties);
        this.index = o.getIndex();
    }

    @Override
    public void setThickness(float x) {
        Property property = new ThicknessProperty(x);
        this.addProperty(property);
    }

    @Override
    public float getThickness() {
        return Util.extractThickness(this);
    }
}
