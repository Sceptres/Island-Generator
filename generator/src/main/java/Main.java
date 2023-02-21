import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.GeneratorInputHandler;
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
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;

import java.io.IOException;
import java.util.Objects;

public class Main {

    private static float[] getThickness(InputHandler handler){
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
    private static MeshGenerator getMeshGenerator(InputHandler handler) {
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
                return new GridMeshGenerator(generators, thickness, meshDimensions[0], meshDimensions[1], squareSize);
            } catch (SquaresFittingException e) {
                String message = String.format(
                        "Not all squares of size %.2f can fit in %dx%d\n",
                        squareSize,
                        meshDimensions[0],
                        meshDimensions[1]
                );
                handler.printHelp(message);
            }
        } else if(meshType.equals("irregular")) {
            System.out.println("This option is coming soon!");
            System.exit(1);
        }

        System.out.println("Mesh type should only be grid or irregular!");
        System.exit(1);

        return null;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract mesh dimensions from
     * @return An array with the dimensions of the mesh. [width, height]
     */
    private static int[] getMeshDimensions(InputHandler handler) {
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
    private static double getGridMeshSquareSize(InputHandler handler) {
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
    private static ColorGenerator[] getColorGenerator(InputHandler handler) {
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
    private static ColorGenerator getGeneralColorGenerator(InputHandler handler, String input) {
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
    private static ColorGenerator getVertexColorGenerator(InputHandler handler, String input) {
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
    private static ColorGenerator getSegmentColorGenerator(InputHandler handler, String input) {
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
    private static ColorGenerator getPolygonColorGenerator(InputHandler handler, String input) {
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

    public static void main(String[] args) throws IOException {
        InputHandler handler = GeneratorInputHandler.getInputHandler(args);

        DotGen generator = new DotGen(getMeshGenerator(handler));
        Mesh myMesh = generator.generate();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, handler.getOptionValue(GeneratorInputHandler.getGeneratorOption(OutputOption.OPTION_STR)));
    }
}