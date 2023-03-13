package ca.mcmaster.cas.se2aa4.a2.island.cli;

import ca.mcmaster.cas.se2aa4.a2.island.cli.options.InputOption;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.ModeOption;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.OutputOption;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class IslandInputHandler {
    private final static Map<String, Option> ISLAND_OPTIONS = Map.of(
            ModeOption.OPTION_STR,  new ModeOption(),
            InputOption.OPTION_STR, new InputOption(),
            OutputOption.OPTION_STR, new OutputOption()

    );

    /**
     *
     * @param opt The {@link Option} to get from the set options
     * @return The {@link Option} instance that matches the given option string
     */
    public static Option getIslandOption(String opt) {
        if(!ISLAND_OPTIONS.containsKey(opt))
            throw new IllegalArgumentException("This option does not exist!");

        return ISLAND_OPTIONS.get(opt);
    }

    /**
     *
     * @param args The arguments passed in through the CMD
     * @return The input handler that has parsed these arguments
     */
    public static InputHandler getInputHandler(String[] args) {
        return new InputHandler(args, ISLAND_OPTIONS);
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the output file from
     * @return The name of the file to export to
     */
    public static String getOutputFile(InputHandler handler) {
        String file = handler.getOptionValue(IslandInputHandler.getIslandOption(OutputOption.OPTION_STR));

        if(!file.endsWith(".mesh"))
            handler.printHelp("Can only write to .mesh files.");

        return file;
    }

    public static String getIslandMode(InputHandler handler){
        String mode = handler.getOptionValue(
                IslandInputHandler.getIslandOption(ModeOption.OPTION_STR),
                ModeOption.DEFAULT_VALUE
        );

        return mode;

    }

    /**
     *
     * @param handler The {@link InputHandler} to get the data from
     * @return The passed in data for the input mesh file path
     */
    public static String getInputMesh(InputHandler handler) {
        String value = handler.getOptionValue(IslandInputHandler.getIslandOption(InputOption.OPTION_STR));

        if(!Files.exists(Path.of(value))) { // Does this file not exist?
            String message = String.format("Cannot find %s. Please try again!\n", value);
            handler.printHelp(message);
        } else if(!value.endsWith(".mesh")) { // Is the given file not a mesh?
            String message = String.format("%s is not a mesh file! Please insert correct file format.\n", value);
            handler.printHelp(message);
        }

        return value;
    }
}
