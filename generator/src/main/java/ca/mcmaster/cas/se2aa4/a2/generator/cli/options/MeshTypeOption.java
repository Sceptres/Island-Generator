package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;


/**
 * Option that sets the type of mesh to generate. Either grid mesh or irregular mesh
 */
public class MeshTypeOption extends Option {

    public static final String OPTION_STR = "mesh";
    public static final String DEFAULT_VALUE = "grid";
    private static final String DESCRIPTION = "The type of mesh to generate. Either `grid` or `irregular`. Grid is default";

    public MeshTypeOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setRequired(false);
    }
}
