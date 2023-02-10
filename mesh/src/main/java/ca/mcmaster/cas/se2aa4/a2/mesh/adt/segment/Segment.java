package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Indexable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.*;

public class Segment implements Indexable {

    private final Vertex v1;
    private final Vertex v2;
    private final Properties properties;
    private int index;

    /**
     *
     * @param v1Idx Index of the first {@link Structs.Vertex} of the {@link Segment}
     * @param v2Idx Index of the second {@link Structs.Vertex} of the {@link Segment}
     */
    public Segment(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.properties = new Properties();
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

    /**
     *
     * @return The {@link List} of properties associated with this segment
     */
    public List<Property> getProperties() {
        List<Property> properties = new Properties();
        Collections.copy(properties, this.properties);
        return properties;
    }

    /**
     *
     * @param key The key of the {@link Structs.Property} to get
     * @return The {@link Structs.Property} with the given key
     */
    public Property getProperty(String key) {
        Optional<Property> property = this.getProperties().stream().filter(p -> p.getKey().equals(key)).findFirst();
        return property.orElse(null);
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     * @param p The {@link Structs.Property} to add to this segment
     */
    public void addProperty(Property p) {
        this.properties.add(p);
    }

    /**
     *
     * @param properties All the {@link Structs.Property} to add
     */
    public void addAllProperties(Iterable<? extends Property> properties) {
        properties.forEach(this.properties::add);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(v1, segment.v1) && Objects.equals(v2, segment.v2) && Objects.equals(properties, segment.properties);
    }
}
