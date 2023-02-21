package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Colorable;

import java.awt.*;

public class SetColorGenerator implements ColorGenerator<Colorable> {

    private final Color color;

    /**
     *
     * @param rgba an array of red,green,blue, and alpha.
     */
    public SetColorGenerator(int[] rgba){
        this.color = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }


    /**
     *
     * @param colorable takes in any object that implements colorable
     */
    @Override
    public void generateColor(Colorable colorable) {
        colorable.setColor(this.color);
    }
}
