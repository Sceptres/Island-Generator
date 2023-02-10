package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import java.awt.*;

public class ColorProperty extends Property {
    /**
     * @param color The color stored by this property
     */
    public ColorProperty(Color color) {
        super("rgb_color", String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue()));
    }
}
