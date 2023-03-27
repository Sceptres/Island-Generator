package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

public class HookOption extends Option {

    public static final String OPTION_STR = "hook";
    public static final String DEFAULT_VALUE = "none";
    private static final String DESCRIPTION = "Enables a hook to act on the mesh. Options are moisture and elevation. By default no hooks run.";

    public HookOption() {
        super("H", OPTION_STR, true, DESCRIPTION);
        super.setArgName("hook name");
        super.setRequired(false);
        super.setArgs(1);
    }

}