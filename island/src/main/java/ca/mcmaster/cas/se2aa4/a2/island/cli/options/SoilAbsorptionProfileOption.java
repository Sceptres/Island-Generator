package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class SoilAbsorptionProfileOption extends Option {

    public static final String OPTION_STR = "soil";
    public static final String DEFAULT_VALUE = "wet";
    private static final String DESCRIPTION = "Sets soil absorption. Available options are wet and dry.";

    public SoilAbsorptionProfileOption() {
        super(OPTION_STR.substring(0,1), OPTION_STR, true, DESCRIPTION);
        super.setArgName("absorption");
        super.setArgs(1);
        super.setRequired(false);
    }
}
