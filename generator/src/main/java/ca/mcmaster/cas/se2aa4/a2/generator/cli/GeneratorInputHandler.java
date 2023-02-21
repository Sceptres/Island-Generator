package ca.mcmaster.cas.se2aa4.a2.generator.cli;

import ca.mcmaster.cas.se2aa4.a2.generator.cli.options.*;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;
import org.apache.commons.cli.Option;

import java.util.Map;

public class GeneratorInputHandler {
    private final static Map<String, Option> GENERATOR_OPTIONS = Map.of(
            OutputOption.OPTION_STR,  new OutputOption(),
            MeshTypeOption.OPTION_STR, new MeshTypeOption(),
            MeshDimensionsOption.OPTION_STR, new MeshDimensionsOption(),
            NumberPolygonsOption.OPTION_STR, new NumberPolygonsOption(),
            RelaxationLevelOption.OPTION_STR, new RelaxationLevelOption(),
            SquareSizeOption.OPTION_STR, new SquareSizeOption(),
            ColorOption.OPTION_STR, new ColorOption()
    );

    /**
     *
     * @param opt The {@link Option} to get from the set options
     * @return The {@link Option} instance that matches the given option string
     */
    public static Option getGeneratorOption(String opt) {
        if(!GENERATOR_OPTIONS.containsKey(opt))
            throw new IllegalArgumentException("This option does not exist!");

        return GENERATOR_OPTIONS.get(opt);
    }

    /**
     *
     * @param args The arguments passed in through the CMD
     * @return The input handler that has parsed these arguments
     */
    public static InputHandler getInputHandler(String[] args) {
        return new InputHandler(args, GENERATOR_OPTIONS);
    }
}
