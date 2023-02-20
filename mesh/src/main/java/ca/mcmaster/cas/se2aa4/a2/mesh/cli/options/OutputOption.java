package ca.mcmaster.cas.se2aa4.a2.mesh.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option that handles output files
 */
public class OutputOption extends Option {

    public static final String OPTION_STR = "output";
    private static final String DESCRIPTION = "The file to output the to.";

    public OutputOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 3), OPTION_STR, true, DESCRIPTION);
        super.setArgName("output file>");
        super.setRequired(true);
        super.setArgs(1);
    }
}
