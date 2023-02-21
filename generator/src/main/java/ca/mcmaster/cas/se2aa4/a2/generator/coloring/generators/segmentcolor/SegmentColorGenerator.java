package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.segmentcolor;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;

import java.awt.*;


public class SegmentColorGenerator implements ColorGenerator<Segment> {


    /**
     *
     * @param segment A segment with 2 vertices
     */
    @Override
    public void generateColor(Segment segment) {
        Color v1_color = segment.getV1().getColor();
        Color v2_color = segment.getV2().getColor();

        int red = (v1_color.getRed() + v2_color.getRed()) / 2;
        int blue = (v1_color.getBlue() + v2_color.getBlue())/2;
        int green = (v1_color.getGreen() + v2_color.getGreen()) /2;
        int alpha = (v1_color.getAlpha() + v2_color.getAlpha()) / 2;
        segment.setColor(new Color(red,green,blue,alpha));
    }
}
