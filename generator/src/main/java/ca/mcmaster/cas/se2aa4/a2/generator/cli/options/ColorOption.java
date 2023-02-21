package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

public class ColorOption extends Option {

    public static final String OPTION_STR = "color";
    public static final String DEFAULT_VALUE = "random random random";
    private static final String DESCRIPTION = "Sets the color generation for all the elements of the mesh. " +
            "Format: vertices segments polygons. " +
            "General options: random, r,g,b or r,g,b,a. " +
            "Segment options: vertices (average color of vertices). " +
            "Polygon options: transparent, vertices (average color of vertices) or segments (average color of segments). " +
            "Default is random for all 3.";

    public ColorOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(3);
        super.setArgName("vertex coloring> <segment coloring> <polygon coloring");
        super.setRequired(false);
    }
}
