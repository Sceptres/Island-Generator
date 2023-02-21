package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Colorable;

import java.awt.*;

public class SetColorGenerator implements ColorGenerator<Colorable> {

    private final Color color;

    /**
     *
     * @param color The color to use.
     */
    public SetColorGenerator(Color color){
        this.color = color;
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
