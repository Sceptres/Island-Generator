package ca.mcmaster.cas.se2aa4.a2.visualizer.cli;

import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.DebugOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.InputOption;
import org.apache.commons.cli.Option;

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
}
