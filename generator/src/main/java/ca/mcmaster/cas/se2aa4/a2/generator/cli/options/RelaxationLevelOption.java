package ca.mcmaster.cas.se2aa4.a2.generator.cli.options;

import org.apache.commons.cli.Option;

/**
 * Option that sets the relaxation level of an irregular mesh. Will be ignored by grid meshes.
 */
public class RelaxationLevelOption extends Option {

    public static final String OPTION_STR = "relaxationLevel";
    public static final String DEFAULT_VALUE = "3";
    private static final String DESCRIPTION = "Sets the relaxation level of an irregular mesh. Will be ignored by grid mesh";

    public RelaxationLevelOption() throws IllegalArgumentException {
        super("rl", OPTION_STR, true, DESCRIPTION);
        super.setArgs(1);
        super.setArgName("relaxation level>");
        super.setRequired(false);
    }
}
