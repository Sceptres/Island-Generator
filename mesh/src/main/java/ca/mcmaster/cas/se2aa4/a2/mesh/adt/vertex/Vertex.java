package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Vertex implements Indexable, IProperties, Colorable, Converter<Structs.Vertex> {
    private double x;
    private double y;
    private final Properties properties;
    private int index;

    /**
     *
     * @param x this is x position
     * @param y this is y position
     */
    public Vertex(double x,double y){
        this.x = x;
        this.y = y;
        this.properties = new Properties();
    }

    /**
     *
     * @param vertex pass in a vertex of structs.Vertex type
     */
    public Vertex(Structs.Vertex vertex){
        this(vertex.getX(), vertex.getY());

        List<Property> properties = vertex.getPropertiesList().stream().map(Property::new).toList();
        this.addAllProperties(properties);
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     * @param x x coordinate of vertex
     */
    public void setX(double x){
        this.x = this.precision(x);
    }

    /**
     *
     * @param y y coordinate of vertex
     */
    public void setY(double y){
       this.y = this.precision(y);
    }

    @Override
    public void setColor(Color color) {
        Property property = new ColorProperty(color);
        this.addProperty(property);
    }

    @Override
    public void addProperty(Property property) {
        this.properties.add(property);
    }

    @Override
    public void addAllProperties(Iterable<? extends Property> properties) {
        properties.forEach(this::addProperty);
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    /**
     *
     * @return the x coordinate of the vertex
     */
    public double getX(){
        return this.x;
    }

    /**
     *
     * @return the y coordinate of the vertex
     */
    public double getY(){
        return this.y;
    }

    @Override
    public Property getProperty(String key) {
        Optional<Property> property = this.properties.stream().filter(p -> p.getKey().equals(key)).findFirst();
        return property.orElse(null);
    }

    @Override
    public List<Property> getProperties() {
        List<Property> properties = new Properties();
        Collections.copy(properties, this.properties);
        return properties;
    }

    @Override
    public Color getColor() {
        return Util.extractColor(this.getProperty(ColorProperty.KEY));
    }

    @Override
    public Structs.Vertex getConverted() {
        List<Structs.Property> properties = this.properties.stream().map(Property::getConverted).toList();
        return Structs.Vertex.newBuilder().setX(this.x).setY(this.y).addAllProperties(properties).build();
    }

    /**
     *
     * @param x double that you want to round to 2 decimal places
     * @return double value in 2 decimal
     */
    private double precision(double x){
        DecimalFormat df=new DecimalFormat("0.00");
        String formate = df.format(x);
        double finalValue = Double.parseDouble(formate);
        return finalValue;
    }

    /**
     *
     * @param o object
     * @return true if its the same object false if its not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.x, x) == 0 && Double.compare(vertex.y, y) == 0 && Objects.equals(properties, vertex.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}
