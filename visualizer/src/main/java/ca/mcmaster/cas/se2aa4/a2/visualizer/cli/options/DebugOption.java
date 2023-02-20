package ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option that enables debugging mode when rendering the mesh
 */
public class DebugOption extends Option {

    public static final String OPTION_STR = "debug";
    private static final String DESCRIPTION = "Enable debug mode when rendering the mesh.";

    public DebugOption() throws IllegalArgumentException {
        super("X", OPTION_STR, false, DESCRIPTION);
        super.setRequired(false);
    }
}
