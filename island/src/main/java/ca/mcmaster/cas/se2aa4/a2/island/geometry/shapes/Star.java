package ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes;

import ca.mcmaster.cas.se2aa4.a2.island.geometry.AbstractShape;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.awt.geom.Path2D;

public class Star extends AbstractShape {
    /**
     * Source code: https://stackoverflow.com/questions/16327588/how-to-make-star-shape-in-java
     * @param innerRadius The inner radius of the star
     * @param outerRadius The outer radius of the star
     * @param numPoints The number of points on the star
     * @param v The {@link Vertex} that is at the center of the star
     * @return The star {@link Shape}
     */
    private static Shape createStar(double innerRadius, double outerRadius, int numPoints, Vertex v) {
        Path2D path = new Path2D.Double();
        double deltaAngleRad = Math.PI / numPoints;
        for (int i = 0; i < numPoints * 2; i++) {
            double angleRad = i * deltaAngleRad;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relX = ca;
            double relY = sa;

            if ((i & 1) == 0) {
                relX *= outerRadius;
                relY *= outerRadius;
            } else {
                relX *= innerRadius;
                relY *= innerRadius;
            }

            if (i == 0) {
                path.moveTo(v.getX() + relX, v.getY() + relY);
            } else {
                path.lineTo(v.getX() + relX, v.getY() + relY);
            }
        }
        path.closePath();
        return path;
    }

    public Star(double innerRadius, double outerRadius, int numPoints, Vertex center) {
        super(createStar(innerRadius, outerRadius, numPoints, center));
    }
}
