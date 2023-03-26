package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class BiomeOption extends Option {

    public static final String OPTION_STR = "biomes";
    public static final String DEFAULT_VALUE = "tropical";
    private static final String DESCRIPTION = "Sets the biome distribution. Available options are tropical.";

    public BiomeOption() {
        super(OPTION_STR.substring(0,1), OPTION_STR, true, DESCRIPTION);
        super.setArgName("biomes");
        super.setArgs(1);
        super.setRequired(false);
    }

}
