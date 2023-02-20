package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option to set the dimensions of the mesh
 */
public class MeshDimensionsOption extends Option {

    public static final String OPTION_STR = "dimension";
    public static final String DEFAULT_VALUE = "500x500";
    private static final String DESCRIPTION = "Sets the dimensions of the mesh. Format widthxheight. Default is 500x500.";

    public MeshDimensionsOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setArgName("widthxheight>");
        super.setRequired(false);
    }
}
