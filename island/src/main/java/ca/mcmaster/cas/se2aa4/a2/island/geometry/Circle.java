package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

/**
 * This class is to make a perimeter for the lagoon and the beach
 */
public class Circle {
    private double radius;
    private final Vertex vertex;


    /**
     *
     * @param radius radius of the circle
     * @param vertex the center of the circle
     */
    public Circle(double radius, Vertex vertex){
        this.radius = radius;
        this.vertex = vertex;
    }

    /**
     *
     * @param radius pass in desired radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     *
     * @return getter for radius
     */
    public double getRadius(){
        return radius;
    }

    /**
     * if t is on the perimeter or inside the perimeter return true
     * @param t pass in t to check if it is inside the circle or not
     * @param <T> Any positionable element
     * @return if t is on the perimeter or inside the perimeter return true else return false
     */
    public <T extends Positionable<Double>> boolean isInside(T t){
       //double distance = Math.hypot(x1-x2, y1-y2);
        double distance = Math.sqrt(Math.pow(t.getX() - vertex.getX(), 2) + Math.pow((t.getY()- vertex.getY()), 2));
        return distance <= this.radius;
    }

    /**
     *
     * @return getter for vertex
     */
    public Vertex getVertex() {
        return vertex;
    }


}
