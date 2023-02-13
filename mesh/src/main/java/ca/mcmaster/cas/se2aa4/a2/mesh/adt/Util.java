package ca.mcmaster.cas.se2aa4.a2.mesh.adt;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;

import java.awt.*;

public class Util {
    /**
     *
     * @param property The color property to extract color from
     * @return The color
     */
    public static Color extractColor(Property property) {
        if(!property.getKey().equals("rgb_color")) // Not the color property?
            throw new IllegalArgumentException("This property is not a color property!");

        // Get color
        String[] rgbStr = property.getValue().split(",");
        int r = Integer.parseInt(rgbStr[0]);
        int g = Integer.parseInt(rgbStr[1]);
        int b = Integer.parseInt(rgbStr[2]);

        return new Color(r, g, b);
    }
}
