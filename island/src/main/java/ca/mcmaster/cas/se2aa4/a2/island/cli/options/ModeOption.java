package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

public class ModeOption extends org.apache.commons.cli.Option{

    public static final String OPTION_STR = "mode";

    private static final String DESCRIPTION = "Sets island generation mode.";

    public static final String DEFAULT_VALUE = "lagoon";

    public ModeOption() throws IllegalArgumentException {
        super(OPTION_STR.substring(0, 1), OPTION_STR, false, DESCRIPTION);
        super.setRequired(false);
        super.setArgs(1);
        super.setArgName("mode");
    }
}
