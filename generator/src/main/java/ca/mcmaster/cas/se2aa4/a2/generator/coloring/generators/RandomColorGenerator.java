package ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Colorable;


/**
 * This generates a random color
 */
public class RandomColorGenerator implements ColorGenerator<Colorable>{

    /**
     *
     * @param colorable takes in any object that implements colorable
     */
    @Override
    public void generateColor(Colorable colorable) {
        colorable.setColor(Util.generateRandomColor(false));
    }
}
