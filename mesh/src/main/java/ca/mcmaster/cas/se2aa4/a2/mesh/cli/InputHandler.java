package ca.mcmaster.cas.se2aa4.a2.mesh.cli;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private CommandLine cmd;
    private final HelpFormatter formatter;
    private final Options options;

    /**
     *
     * @param args The arguments passed in through the cmd
     * @param optionsMap The {@link HashMap} of all the possible options that can be passed in
     */
    public InputHandler(String[] args, HashMap<String, ? extends Option> optionsMap) {
        // Set options
        this.options = new Options();
        optionsMap.forEach((k, v) -> this.options.addOption(v));
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
     * @param option The {@link Option} to get from the command line
     * @return Whether this {@link Option} has been passed in or not
     * @throws IllegalArgumentException If this {@link Option} is not a part of the parsed {@link Options}
     */
    public boolean hasOption(Option option) throws IllegalArgumentException {
        return this.cmd.hasOption(option);
    }
}

