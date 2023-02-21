package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon.Polygon;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;

import java.awt.*;
import java.util.List;


/**
 * This class takes the average color of the segments
 */
public class PolygonSegmentColorGenerator implements ColorGenerator<Polygon> {

    /**
     *
     * @param polygon a polygon with segments
     */
    @Override
    public void generateColor(Polygon polygon) {
        List<Segment> list = polygon.getSegments();     //DID I IMPORT THE RIGHT ONE
        int red,green,blue,alpha;
        red=0;
        green= 0;
        blue= 0;
        alpha = 0;
        for(Segment segment : list){
            Color color = segment.getColor();
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
