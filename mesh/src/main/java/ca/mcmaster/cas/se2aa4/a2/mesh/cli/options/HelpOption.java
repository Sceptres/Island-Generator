package ca.mcmaster.cas.se2aa4.a2.mesh.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option that displays help message to the user
 */
public class HelpOption extends Option {
    public static final String OPTION_STR = "help";
    private static final String DESCRIPTION = "Displays program usage.";

    public HelpOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, false, DESCRIPTION);
        super.setRequired(false);
    }
}
