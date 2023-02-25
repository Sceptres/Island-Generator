package ca.mcmaster.cas.se2aa4.a2.visualizer.cli;

import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.DebugOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.InputOption;
import org.apache.commons.cli.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class VisualizerInputHandler {
    private final static Map<String, Option> VISUALIZER_OPTIONS = Map.of(
            InputOption.OPTION_STR, new InputOption(),
            OutputOption.OPTION_STR,  new OutputOption(),
            DebugOption.OPTION_STR, new DebugOption()
    );

    /**
     *
     * @param opt The {@link Option} to get from the set options
     * @return The {@link Option} instance that matches the given option string
     */
    public static Option getVisualizerOption(String opt) {
        if(!VISUALIZER_OPTIONS.containsKey(opt))
            throw new IllegalArgumentException("This option does not exist!");

        return VISUALIZER_OPTIONS.get(opt);
    }

    /**
     *
     * @param args The arguments passed in through the CMD
     * @return The input handler that has parsed these arguments
     */
    public static InputHandler getInputHandler(String[] args) {
        return new InputHandler(args, VISUALIZER_OPTIONS);
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the data from
     * @return The passed in data for the input mesh file path
     */
    public static String getInputMesh(InputHandler handler) {
        String value = handler.getOptionValue(VisualizerInputHandler.getVisualizerOption(InputOption.OPTION_STR));

        if(!Files.exists(Path.of(value))) { // Does this file not exist?
            String message = String.format("Cannot find %s. Please try again!\n", value);
            handler.printHelp(message);
        } else if(!value.endsWith(".mesh")) { // Is the given file not a mesh?
            String message = String.format("%s is not a mesh file! Please insert correct file format.\n", value);
            handler.printHelp(message);
        }

        return value;
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the data from
     * @return The path of the file to output the mesh to
     */
    public static String getOutputFile(InputHandler handler) {
        String value = handler.getOptionValue(VisualizerInputHandler.getVisualizerOption(OutputOption.OPTION_STR));

        if(!value.endsWith(".svg")){
            handler.printHelp("The given output file is not an svg!");
        }

        return value;
    }
}
