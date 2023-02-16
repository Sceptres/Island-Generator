package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.*;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class Vertex implements Indexable,Thickenable, IProperties, Renderable, Colorable, Copier<Vertex>, Converter<Structs.Vertex> {
    private double x;
    private double y;
    private boolean wasRendered;
    private final Properties properties;
    private int index;

    /**
     *
     * @param x this is x position
     * @param y this is y position
     */
    public Vertex(double x,double y){
        this.x = Util.precision(x);
        this.y = Util.precision(y);
        this.properties = new Properties();
        this.index = -1;
    }

    /**
     *
     * @param vertex pass in a vertex of structs.Vertex type
     */
    public Vertex(Structs.Vertex vertex){
        this(vertex.getX(), vertex.getY());
        this.addAllProperties(Util.toProperties(vertex.getPropertiesList()));
    }

    @Override
    public void setIndex(int index) {
        if(this.index == -1 && index >= 0)
            this.index = index;
    }

    /**
     *
     * @param x x coordinate of vertex
     */
    public void setX(double x){
        this.x = Util.precision(x);
    }

    /**
     *
     * @param y y coordinate of vertex
     */
    public void setY(double y){
       this.y = Util.precision(y);
    }

    /**
     *
     * @return True if this vertex is a centroid of a polygon. False otherwise
     */
    public boolean isCentroid() {
        Property property = this.getProperty(CentroidProperty.KEY);

        if(Objects.isNull(property))
            return false;

        return Boolean.parseBoolean(property.getValue());
    }

    /**
     *
     * @param isCentroid Is this vertex a centroid vertex?
     */
    public void setAsCentroid(boolean isCentroid) {
        if(isCentroid) {
            this.addProperty(new CentroidProperty(true));
        } else {
            this.addProperty(new CentroidProperty(false));
        }
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
        return new ArrayList<>(this.properties);
    }

    @Override
    public Color getColor() {
        return Util.extractColor(this);
    }

    @Override
    public void draw(Graphics2D canvas) {
        if(!this.wasRendered) {
            double centreX = this.getX() - (3 / 2.0d);
            double centreY = this.getY() - (3 / 2.0d);

            // Set color to draw with
            canvas.setColor(this.getColor());

            // Draw vertex
            Ellipse2D point = new Ellipse2D.Double(centreX, centreY, 3, 3);
            canvas.fill(point);

            this.wasRendered = true;
        }
    }

    @Override
    public Structs.Vertex getConverted() {
        return Structs.Vertex.newBuilder().setX(this.x).setY(this.y).addAllProperties(this.properties.getConverted()).build();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.x, x) == 0 && Double.compare(vertex.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, properties, index);
    }

    @Override
    public void copy(Vertex vertex) {
        this.setX(vertex.getX());
        this.setY(vertex.getY());
        this.wasRendered = vertex.wasRendered;
        this.properties.copy(vertex.properties);
        this.index = vertex.getIndex();
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
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
