package ca.mcmaster.cas.se2aa4.a2.mesh.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Properties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segments;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.IProperties;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

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

    /**
     *
     * @param structProperties The group of {@link Structs.Property} instances to convert to {@link Property}
     * @return A list of all the equivalent {@link Property} instances
     */
    public static Properties toProperties(Iterable<? extends Structs.Property> structProperties) {
        Properties properties = new Properties();
        structProperties.forEach(p -> properties.add(new Property(p)));

        return properties;
    }
}
