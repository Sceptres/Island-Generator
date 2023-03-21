package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class AltimeterProfileOption extends Option {
    public static final String OPTION_STR = "altitude";
    private static final String DESCRIPTION = "Sets the altimeter profile for the island. Available options are hills, volcano, or lagoon";
    public static final String DEFAULT_VALUE = "hills";

    public AltimeterProfileOption() {
        super(OPTION_STR.substring(0, 1), OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setArgName("altimetric profile");
        super.setRequired(false);
    }
}
