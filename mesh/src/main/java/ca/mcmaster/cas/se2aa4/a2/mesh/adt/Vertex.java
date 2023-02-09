package ca.mcmaster.cas.se2aa4.a2.mesh.adt;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import java.text.DecimalFormat;

public class Vertex {
    private Structs.Vertex vertex;

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



}
