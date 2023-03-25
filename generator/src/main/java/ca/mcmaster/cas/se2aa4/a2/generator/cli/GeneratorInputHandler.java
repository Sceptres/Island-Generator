package ca.mcmaster.cas.se2aa4.a2.generator.cli;

import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.InvalidPolygonNumberException;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.NotSquareMeshException;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.SquaresFittingException;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.options.*;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.RandomColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.SetColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor.PolygonSegmentColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor.PolygonTransparentColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.polygoncolor.PolygonVertexColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.segmentcolor.SegmentColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.GridMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.IrregularMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.exceptions.IllegalInputException;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;
import org.apache.commons.cli.Option;

import java.util.Map;
import java.util.Objects;

public class GeneratorInputHandler {
    private final static Map<String, Option> GENERATOR_OPTIONS = Map.of(
            OutputOption.OPTION_STR,  new OutputOption(),
            MeshTypeOption.OPTION_STR, new MeshTypeOption(),
            MeshDimensionsOption.OPTION_STR, new MeshDimensionsOption(),
            NumberPolygonsOption.OPTION_STR, new NumberPolygonsOption(),
            RelaxationLevelOption.OPTION_STR, new RelaxationLevelOption(),
            SquareSizeOption.OPTION_STR, new SquareSizeOption(),
            ColorOption.OPTION_STR, new ColorOption(),
            ThicknessOption.OPTION_STR, new ThicknessOption()
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

    /**
     *
     * @param handler The {@link InputHandler} to extract the output file from
     * @return The name of the file to export to
     */
    public static String getOutputFile(InputHandler handler) throws IllegalInputException {
        String file = handler.getOptionValue(GeneratorInputHandler.getGeneratorOption(OutputOption.OPTION_STR));

        if(!file.endsWith(".mesh"))
            handler.printHelp("Can only write to .mesh files.");

        return file;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract thickness input from
     * @return The given thicknesses of the vertices and segments
     */
    public static float[] getThickness(InputHandler handler) throws IllegalInputException {
        String[] thickness = handler.getOptionValues(
                GeneratorInputHandler.getGeneratorOption(ThicknessOption.OPTION_STR),
                ThicknessOption.DEFAULT_VALUE
        );
        if(!thickness[0].matches("[0-9]+.?[0-9]*")) { // Given input is not a number?
            String message = String.format("%s is not a number!\n", thickness[0]);
            handler.printHelp(message);
        }

        else if(!thickness[1].matches("[0-9]+.?[0-9]*")) { // Given input is not a number?
            String message = String.format("%s is not a number!\n", thickness[1]);
            handler.printHelp(message);
            System.exit(1);
        }


        return new float[]{Float.parseFloat(thickness[0]), Float.parseFloat(thickness[1])};

    }


    /**
     *
     * @param handler The {@link InputHandler} to extract generator type from
     * @return The {@link MeshGenerator} matching given input
     */
    public static MeshGenerator getMeshGenerator(InputHandler handler) throws IllegalInputException {
        String meshType = handler.getOptionValue(
                GeneratorInputHandler.getGeneratorOption(MeshTypeOption.OPTION_STR),
                MeshTypeOption.DEFAULT_VALUE
        );

        // Get mesh dimensions
        int[] meshDimensions = getMeshDimensions(handler);
        ColorGenerator[] generators = getColorGenerator(handler);

        // get thickness
        float[] thickness = getThickness(handler);

        if(meshType.equals("grid")) {
            double squareSize = getGridMeshSquareSize(handler);

            try {
                return new GridMeshGenerator(generators, thickness, meshDimensions, squareSize);
            } catch(NotSquareMeshException e) { // Mesh does not have square dimensions?
                handler.printHelp("Dimensions must be equal for grid mesh!");
            } catch(SquaresFittingException e) { // Not all squares fit within the grid?
                String message = String.format(
                        "Not all squares of size %.2f can fit in %dx%d\n",
                        squareSize,
                        meshDimensions[0],
                        meshDimensions[1]
                );
                handler.printHelp(message);
            }
        } else if(meshType.equals("irregular")) {
            try {
                int relaxationLevel = getRelaxationLevel(handler);
                int numPolygons = getNumPolygons(handler);
                return new IrregularMeshGenerator(generators, thickness, meshDimensions, relaxationLevel, numPolygons);
            } catch (InvalidPolygonNumberException e) { // Invalid number of polygons passed in?
                handler.printHelp(e.getMessage());
            }
        }

        handler.printHelp("Mesh type should only be grid or irregular!");

        return null;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract mesh dimensions from
     * @return An array with the dimensions of the mesh. [width, height]
     */
    public static int[] getMeshDimensions(InputHandler handler) throws IllegalInputException {
        String dimensionInput = handler.getOptionValue(
                GeneratorInputHandler.getGeneratorOption(MeshDimensionsOption.OPTION_STR),
                MeshDimensionsOption.DEFAULT_VALUE
        );

        // Check that input matches expected widthxheight format
        if(!dimensionInput.matches("[0-9]+.?[0-9]*(x)[0-9]+.?[0-9]*")) {
            handler.printHelp("Input does not match expected format!");
        }

        // Get given dimensions
        String[] dimensionStr = dimensionInput.split("x");

        // Convert input to numbers
        double width = Integer.parseInt(dimensionStr[0]);
        double height = Integer.parseInt(dimensionStr[1]);

        if((width % 1) != 0 || (height % 1) != 0) { // Is the given dimensions floating point numbers?
            handler.printHelp("Cannot have floating point dimensions!");
        }

        return new int[]{(int) width, (int) height};
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the square size from
     * @return The passed in square size
     */
    public static double getGridMeshSquareSize(InputHandler handler) throws IllegalInputException {
        String squareSizeInput = handler.getOptionValue(
                GeneratorInputHandler.getGeneratorOption(SquareSizeOption.OPTION_STR),
                SquareSizeOption.DEFAULT_VALUE
        );

        if(!squareSizeInput.matches("[0-9]+")) { // Given input is not a number?
            String message = String.format("%s is not a number!\n", squareSizeInput);
            handler.printHelp(message);
        }

        return Double.parseDouble(squareSizeInput);
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract color data from
     * @return An array with the vertex, segments, and polygon color generation methods in order
     */
    public static ColorGenerator[] getColorGenerator(InputHandler handler) throws IllegalInputException {
        String[] values = handler.getOptionValues(
                GeneratorInputHandler.getGeneratorOption(ColorOption.OPTION_STR),
                ColorOption.DEFAULT_VALUE
        );

        // Get element color generators
        ColorGenerator vertexColorGenerator = getVertexColorGenerator(handler, values[0]);
        ColorGenerator segmentColorGenerator = getSegmentColorGenerator(handler, values[1]);
        ColorGenerator polygonColorGenerator = getPolygonColorGenerator(handler, values[2]);

        return new ColorGenerator[]{vertexColorGenerator, segmentColorGenerator, polygonColorGenerator};
    }

    /**
     *
     * @param input The passed in CMD input by the user
     * @return The color generator matching the given input. Only covers common cases: random | r,g,b | r,g,b,a
     */
    private static ColorGenerator getGeneralColorGenerator(InputHandler handler, String input) throws IllegalInputException {
        try {
            if (input.equals("random"))
                return new RandomColorGenerator();
            else if (input.matches("[0-9]+,[0-9]+,[0-9]+")) {
                // Convert RGB to RGBA
                String rgbaStr = input+",255";
                String[] rgbStr = rgbaStr.split(",");
                return new SetColorGenerator(Util.getColorFromStr(rgbStr));
            } else if (input.matches("[0-9]+,[0-9]+,[0-9]+,[0-9]+")) {
                String[] rgbaStr = input.split(",");
                return new SetColorGenerator(Util.getColorFromStr(rgbaStr));
            }
        } catch (IllegalArgumentException e) {
            handler.printHelp("Invalid RGB/RGBA input.");
        }

        return null;
    }

    /**
     *
     * @param handler The {@link InputHandler} to print out any errors with input
     * @param input The input for the vertex color generation
     * @return The {@link ColorGenerator} for the vertices
     */
    private static ColorGenerator getVertexColorGenerator(InputHandler handler, String input) throws IllegalInputException {
        ColorGenerator generator = getGeneralColorGenerator(handler, input);

        if(Objects.isNull(generator)) { // Nothing chosen from general options?
            String message = String.format("Invalid color generation method %s for vertex!\n", input);
            handler.printHelp(message);
        }

        return generator;
    }

    /**
     *
     * @param handler The {@link InputHandler} to print out any errors with input
     * @param input The input for the segment color generation
     * @return The {@link ColorGenerator} for the segments
     */
    private static ColorGenerator getSegmentColorGenerator(InputHandler handler, String input) throws IllegalInputException {
        ColorGenerator generator = getGeneralColorGenerator(handler, input);

        if(Objects.isNull(generator)) {  // Nothing chosen from general options?
            if(input.equals("vertices")) { // User request segments color to be average color of vertices?
                generator = new SegmentColorGenerator();
            } else {
                String message = String.format("Invalid color generation method %s for segment!\n", input);
                handler.printHelp(message);
            }
        }

        return generator;
    }

    /**
     *
     * @param handler The {@link InputHandler} to print out any errors with input
     * @param input The input for the polygon color generation
     * @return The {@link ColorGenerator} for the polygons
     */
    private static ColorGenerator getPolygonColorGenerator(InputHandler handler, String input) throws IllegalInputException {
        ColorGenerator generator = getGeneralColorGenerator(handler, input);

        if(Objects.isNull(generator)) {  // Nothing chosen from general options?
            switch (input) {
                case "vertices" -> // User request polygon color to be average color of vertices?
                        generator = new PolygonVertexColorGenerator();
                case "segments" ->  // User request polygon color to be average color of segments?
                        generator = new PolygonSegmentColorGenerator();
                case "transparent" -> // User request polygon color to be transparent?
                        generator = new PolygonTransparentColorGenerator();
                default -> {
                    String message = String.format("Invalid color generation method %s for polygon!\n", input);
                    handler.printHelp(message);
                }
            }
        }

        return generator;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the relaxation level from
     * @return The given relaxation level
     */
    public static int getRelaxationLevel(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                GeneratorInputHandler.getGeneratorOption(RelaxationLevelOption.OPTION_STR),
                RelaxationLevelOption.DEFAULT_VALUE
        );

        if(value.matches("[0-9]+")) { // The given input is a number?
            return Integer.parseInt(value);
        } else
            handler.printHelp("Given relaxation level is not an integer!");

        return -1;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the number of polygons from
     * @return The number of polygons passed in by the user
     */
    public static int getNumPolygons(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                GeneratorInputHandler.getGeneratorOption(NumberPolygonsOption.OPTION_STR),
                NumberPolygonsOption.DEFAULT_VALUE
        );

        if(value.matches("[0-9]+")) { // Is the given input a number?
            return Integer.parseInt(value);
        } else
            handler.printHelp("The given number of polygons is not a number!");

        return -1;
    }

}