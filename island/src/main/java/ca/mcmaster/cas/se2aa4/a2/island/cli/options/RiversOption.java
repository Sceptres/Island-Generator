package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class RiversOption extends Option {
    public static final String OPTION_STR = "rivers";
    private static final String DESCRIPTION = "Sets the number of rivers.";
    public static final String DEFAULT_VALUE = "0";

    public RiversOption() {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setRequired(false);
        super.setArgs(1);
        super.setArgName("rivers");
    }
}
