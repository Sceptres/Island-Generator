package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * Sets the coloring of a specific object
 */
public class ColorOption extends Option {

    public static final String OPTION_STR = "color";
    public static final String[] DEFAULT_VALUE = new String[]{"random", "random", "random"};
    private static final String DESCRIPTION = "Sets the color generation for all the elements of the mesh.\n" +
            "Format: vertices segments polygons.\n" +
            "Vertex options: random | r,g,b | r,g,b,a.\n" +
            "Segment options: random | r,g,b | r,g,b,a | vertices (average color of vertices).\n" +
            "Polygon options: random | r,g,b | r,g,b,a | transparent | vertices (average color of vertices) | " +
            "" +
            "segments (average color of segments).\n" +
            "Default is random for all 3.";

    public ColorOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(3);
        super.setArgName("vertex coloring> <segment coloring> <polygon coloring");
        super.setRequired(false);
    }
}
