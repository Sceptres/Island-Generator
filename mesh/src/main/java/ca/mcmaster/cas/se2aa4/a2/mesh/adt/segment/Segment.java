package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.IProperties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Indexable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Segment implements Indexable, IProperties {

    private final Vertex v1;
    private final Vertex v2;
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
    }

    /**
     *
     * @param segment The {@link Structs.Segment} instance to wrap
     * @param v1 The left vertex of the segment
     * @param v2 The right vertex of the segment
     */
    public Segment(Structs.Segment segment, Structs.Vertex v1, Structs.Vertex v2) {
        this.v1 = new Vertex(v1);
        this.v2 = new Vertex(v2);
        this.properties = new Properties();

        List<Property> properties = segment.getPropertiesList().stream().map(Property::new).toList();
        this.addAllProperties(properties);

        this.index = -1;
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
        List<Property> properties = new Properties();
        Collections.copy(properties, this.properties);
        return properties;
    }

    @Override
    public Property getProperty(String key) {
        Optional<Property> property = this.getProperties().stream().filter(p -> p.getKey().equals(key)).findFirst();
        return property.orElse(null);
    }

    @Override
    public void setIndex(int index) {
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

    /**
     *
     * @return Get the {@link Structs.Segment} instance that stores all segment data
     */
    public Structs.Segment getSegment() {
        List<Structs.Property> properties = this.properties.stream().map(Property::getProperty).toList();
        return Structs.Segment.newBuilder().setV1Idx(this.v1.getIndex()).setV2Idx(this.v2.getIndex())
                .addAllProperties(properties).build();
    }

    /**
     * Calculates the color of the segment and adds it to properties
     */
    public void calculateColor() {
        Color v1Color = this.v1.getColor();
        Color v2Color = this.v2.getColor();

        // Calculate segment color
        int r = (v1Color.getRed() + v2Color.getRed()) / 2;
        int g = (v1Color.getGreen() + v2Color.getGreen()) / 2;
        int b = (v1Color.getBlue() + v2Color.getBlue()) / 2;

        // Create color property
        Color color = new Color(r, g, b);
        Property property = new ColorProperty(color);

        // Add property
        this.addProperty(property);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(v1, segment.v1) && Objects.equals(v2, segment.v2) && Objects.equals(properties, segment.properties);
    }
}
