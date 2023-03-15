package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class ShapeOption extends Option {

    public static final String OPTION_STR = "shape";
    public static final String DEFAULT_VALUE = "circle";
    private static final String DESCRIPTION = "Sets the shape of the island. Available options are circle, oval, star";

    public ShapeOption() {
        super("shp", OPTION_STR, true, DESCRIPTION);
        super.setArgName("shape-widthxheight");
        super.setArgs(1);
        super.setRequired(false);
    }
}
