package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class LakesOption extends Option {
    public static final String OPTION_STR = "lakes";
    private static final String DESCRIPTION = "Sets the number of lakes, lakes can be conjoined by the generator (for realism).";
    public static final String DEFAULT_VALUE = "0";

    public LakesOption() {
        super(OPTION_STR.substring(0, 1), OPTION_STR, false, DESCRIPTION);
        super.setRequired(false);
        super.setArgs(1);
        super.setArgName("mode");
    }




}