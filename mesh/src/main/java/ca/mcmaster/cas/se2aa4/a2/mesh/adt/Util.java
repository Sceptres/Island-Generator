package ca.mcmaster.cas.se2aa4.a2.mesh.adt;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.IProperties;

import java.awt.*;
import java.util.Objects;

public class Util {
    /**
     *
     * @param t The element to get color property from
     * @return The color
     */
    public static <T extends IProperties> Color extractColor(T t) {
        Property property = t.getProperty(ColorProperty.KEY);

        if(Objects.isNull(property))
            return Color.BLACK;

        if(!property.getKey().equals(ColorProperty.KEY)) // Not the color property?
            throw new IllegalArgumentException("This property is not a color property!");

        // Get color
        String[] rgbStr = property.getValue().split(",");
        int r = Integer.parseInt(rgbStr[0]);
        int g = Integer.parseInt(rgbStr[1]);
        int b = Integer.parseInt(rgbStr[2]);

        return new Color(r, g, b);
    }
}
