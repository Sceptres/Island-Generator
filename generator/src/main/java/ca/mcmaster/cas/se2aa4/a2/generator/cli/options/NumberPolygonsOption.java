package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * An option that sets the number of polygons to generate in an irregular mesh. Will be ignored by the grid mesh.
 */
public class NumberPolygonsOption extends Option {

    public static final String OPTION_STR = "num-polygons";
    public static final String DEFAULT_VALUE = "100";
    private static final String DESCRIPTION =
            "Sets the number of polygons to generate in the irregular mesh. Will be ignored if the mesh is a grid.";


    public NumberPolygonsOption() throws IllegalArgumentException {
        super(OPTION_STR, OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setRequired(false);
    }
}
