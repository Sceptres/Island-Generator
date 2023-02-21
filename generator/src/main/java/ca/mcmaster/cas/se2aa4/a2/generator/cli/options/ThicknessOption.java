package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * Sets the thickness of a specific object
 */
public class ThicknessOption extends Option {

    public static final String OPTION_STR = "thickness";
    public static final String[] DEFAULT_VALUE = new String[]{"3", "3"};
    private static final String DESCRIPTION = "Sets the thickness of the vertices and segments.\n" +
            "Format: vertices segments.\n" +
            "Vertex options: any postive real number.\n" +
            "Segment options: any postive real number.\n" +
            "Default is 3 for vertices and segments.";

    public ThicknessOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(2);
        super.setArgName("vertex thickness> <segment thickness");
        super.setRequired(false);
    }
}
