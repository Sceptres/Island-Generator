package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import java.awt.*;

public class ColorProperty extends Property {
    public static final String KEY = "rgb_color";
    private static final String VALUE_FORMAT = "%d,%d,%d";

    /**
     * @param color The color stored by this property
     */
    public ColorProperty(Color color) {
        super(KEY, String.format(VALUE_FORMAT, color.getRed(), color.getGreen(), color.getBlue()));
    }
}
