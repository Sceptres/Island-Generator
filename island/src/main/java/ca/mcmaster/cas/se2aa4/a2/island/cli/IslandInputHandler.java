package ca.mcmaster.cas.se2aa4.a2.island.cli;

import ca.mcmaster.cas.se2aa4.a2.island.cli.options.InputOption;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.ModeOption;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.OutputOption;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.ShapeOption;
import ca.mcmaster.cas.se2aa4.a2.island.generator.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.generator.generators.LagoonIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Star;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class IslandInputHandler {
    private final static Map<String, Option> ISLAND_OPTIONS = Map.of(
            ModeOption.OPTION_STR,  new ModeOption(),
            InputOption.OPTION_STR, new InputOption(),
            OutputOption.OPTION_STR, new OutputOption(),
            ShapeOption.OPTION_STR, new ShapeOption()

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

    public static IslandGenerator getIslandMode(InputHandler handler, Mesh mesh){
        String mode = handler.getOptionValue(
                IslandInputHandler.getIslandOption(ModeOption.OPTION_STR),
                ModeOption.DEFAULT_VALUE
        );

        IslandGenerator generator = null;

        int[] meshDimension = mesh.getDimension();
        Vertex meshCenter = new Vertex(meshDimension[0]/2f, meshDimension[1]/2f);
        double diagonalLength = Math.hypot(meshDimension[0]/2f, meshDimension[1]/2f);

        Shape shape = IslandInputHandler.getShapeInput(handler, meshCenter, diagonalLength);

        if(mode.equals("lagoon"))
            generator = new LagoonIslandGenerator(mesh, shape);
        else
            handler.printHelp("Invalid mode: " + mode);

        return generator;

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

    /**
     *
     * @param handler The {@link InputHandler} to extract the data from
     * @param center The center {@link Vertex} of the mesh
     * @param diagonalLength The length from the center to a corner in the mesh
     * @return The {@link Shape} that matches cmd input
     */
    private static Shape getShapeInput(InputHandler handler, Vertex center, double diagonalLength) {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(ShapeOption.OPTION_STR),
                ShapeOption.DEFAULT_VALUE
        );

        Shape shape = null;

        switch (value) {
            case "circle"   -> shape = new Circle(diagonalLength/2f, center);
            case "oval"     -> shape = new Oval(diagonalLength/3f, diagonalLength/1.5f, center);
            case "star"     -> shape = new Star(diagonalLength/1.5f, diagonalLength/3f, 8, center);
            default         -> handler.printHelp("Invalid shape!");
        }

        return shape;
    }
}