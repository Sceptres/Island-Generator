package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * Set the square size of a grid mesh. Will be ignored by irregular meshes.
 */
public class SquareSizeOption extends Option {

    public static final String OPTION_STR = "squareSize";
    public static final String DEFAULT_VALUE = "20";
    private static final String DESCRIPTION = "Sets the size of the squares in the grid mesh. Ignored by irregular mesh.";

    public SquareSizeOption() throws IllegalArgumentException {
        super("ss", OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setArgName("square size");
        super.setRequired(false);
    }
}
