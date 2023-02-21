package ca.mcmaster.cas.se2aa4.a2.mesh.cli;

import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.HelpOption;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private static final HelpOption HELP_OPTION = new HelpOption();

    private CommandLine cmd;
    private final HelpFormatter formatter;
    private final Options options;

    /**
     *
     * @param args The arguments passed in through the cmd
     * @param optionsMap The {@link HashMap} of all the possible options that can be passed in
     */
    public InputHandler(String[] args, Map<String, ? extends Option> optionsMap) {
        // Set options
        this.options = new Options();
        optionsMap.forEach((k, v) -> this.options.addOption(v));
        this.options.addOption(HELP_OPTION);
        this.formatter = new HelpFormatter();

        try {
            // Parse data in array
            CommandLineParser parser = new DefaultParser();
            this.cmd = parser.parse(this.options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            this.printHelp();
            System.exit(1);
        }

        if(this.hasOption(HELP_OPTION)) { // Did the user request for help message?
            this.printHelp();
            System.exit(1);
        }
    }

    /**
     *
     * @param option The key of the {@link Option} to get
     * @return The {@link Option} instance from {@link InputHandler#options}
     */
    public Option getOption(String option) {
        return (Option) this.options.getOption(option).clone();
    }

    /**
     * Print the help string to the user
     */
    public void printHelp() {
        this.formatter.printHelp(200, "Mesh Generation", "", options, "");
    }

    /**
     *
     * @param option The {@link Option} to get value of from the command line
     * @return The value given to this {@link Option}
     * @throws IllegalArgumentException If this {@link Option} is not a part of the parsed {@link Options}
     */
    public String getOptionValue(Option option) throws IllegalArgumentException {
        if(!this.options.hasOption(option.getOpt()))
            throw new IllegalArgumentException("Invalid option!");

        return this.cmd.getOptionValue(option);
    }

    /**
     *
     * @param option The {@link Option} to get value of from the command line
     * @param defaultValue The default value if no value exists
     * @return The value of the {@link Option} if there is one. The given default value otherwise.
     */
    public String getOptionValue(Option option, String defaultValue) {
        if(!this.options.hasOption(option.getOpt()))
            throw new IllegalArgumentException("Invalid option!");

        return this.cmd.getOptionValue(option, defaultValue);
    }

    /**
     *
     * @param option The {@link Option} to get values of from the command line
     * @return A {@link String}[] containing the values given to this {@link Option}
     * @throws IllegalArgumentException If this {@link Option} is not a part of the parsed {@link Options}
     */
    public String[] getOptionValues(Option option) throws IllegalArgumentException {
        if(!this.options.hasOption(option.getOpt()))
            throw new IllegalArgumentException("Invalid option!");

        return this.cmd.getOptionValues(option);
    }

    /**
     *
     * @param option The {@link Option} to get values of
     * @param defaultValues The default values to pass in if no arguments were given
     * @return The values of the given option. If there are none the default values given
     */
    public String[] getOptionValues(Option option, String[] defaultValues) {
        String[] values = this.getOptionValues(option);

        return values.length != 0 ? values : defaultValues;
    }

    /**
     *
     * @param option The {@link Option} to get from the command line
     * @return Whether this {@link Option} has been passed in or not
     */
    public boolean hasOption(Option option) {
        return this.cmd.hasOption(option);
    }
}

