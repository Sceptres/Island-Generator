package ca.mcmaster.cas.se2aa4.a2.island.cli.options;

import org.apache.commons.cli.Option;

import java.util.Random;

public class SeedOption extends Option {

    public static final String OPTION_STR = "seed";
    private static final String DESCRIPTION = "What is your seed?";
    public static final String DEFAULT_VALUE = String.valueOf(generateSeed());


    private static long generateSeed(){
        Random random = new Random();
        return random.nextLong(Long.MIN_VALUE,Long.MAX_VALUE);
    }

    public SeedOption() {
        super("sed", OPTION_STR, true, DESCRIPTION);
        super.setRequired(false);
        super.setArgs(1);
        super.setArgName("seed");
    }
}