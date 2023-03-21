package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class AquiferOption extends Option {
    public static final String OPTION_STR = "aquifers";
    private static final String DESCRIPTION = "Sets the number of aquifers in the island.";
    public static final String DEFAULT_VALUE = "0";

    public AquiferOption() {
        super(OPTION_STR.substring(0, 2), OPTION_STR, true, DESCRIPTION);
        super.setRequired(false);
        super.setArgs(1);
        super.setArgName("# of aquifers");
    }
}