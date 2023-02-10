package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Indexable;

import java.text.DecimalFormat;
import java.util.Objects;

public class Vertex implements Indexable {
    private Structs.Vertex vertex;
    private int index;

    /**
     * regular vertex builder
     */
    public Vertex(){
        this.vertex = Structs.Vertex.newBuilder().build();
    }

    /**
     *
     * @param x this is x position
     * @param y this is y position
     */
    public Vertex(double x,double y){
        this.vertex = Structs.Vertex.newBuilder().setX(precision(x)).setY(precision(y)).build();
    }

    /**
     *
     * @param vertex pass in a vertex of structs.Vertex type
     */
    public Vertex(Structs.Vertex vertex){
        this.vertex = vertex;
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
        this.vertex = Structs.Vertex.newBuilder(this.vertex).setX(precision(x)).build();
    }

    /**
     *
     * @param y y coordinate of vertex
     */
    public void setY(double y){
       this.vertex = Structs.Vertex.newBuilder(this.vertex).setY(precision(y)).build();
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
        return this.vertex.getX();
    }

    /**
     *
     * @return the y coordinate of the vertex
     */
    public double getY(){
        return this.vertex.getY();
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
        Vertex vertex1 = (Vertex) o;
        return Objects.equals(vertex, vertex1.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex);
    }
}
