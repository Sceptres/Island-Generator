package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Colorable;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.util.List;


/**
 * This class takes the average color of the vertices
 */
public class PolygonVertexColorGenerator implements ColorGenerator<Polygon> {

    /**
     *
     * @param polygon a polygon with vertices
     */
    @Override
    public void generateColor(Polygon polygon) {
        List<Vertex> list = polygon.getVertices();
        int red,green,blue,alpha;
        red=0;
        green= 0;
        blue= 0;
        alpha = 0;
        for(Vertex vertex : list){
            Color color = vertex.getColor();
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha += color.getAlpha();
        }

        red = red / list.size();
        blue = blue / list.size();
        green = green / list.size();
        alpha = alpha / list.size();

        polygon.setColor(new Color(red,green,blue,alpha));
    }
}
