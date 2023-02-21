package ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option that takes in the input mesh file
 */
public class InputOption extends Option {

    public static final String OPTION_STR = "input";
    private static final String DESCRIPTION = "Takes in the mesh file to read from.";

    public InputOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 2), OPTION_STR, true, DESCRIPTION);
        super.setRequired(true);
        super.setArgName("input file>");
        super.setArgs(1);
    }
}
