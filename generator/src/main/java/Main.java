import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.GeneratorInputHandler;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions.SquaresFittingException;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.options.MeshDimensionsOption;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.options.MeshTypeOption;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.options.SquareSizeOption;
import ca.mcmaster.cas.se2aa4.a2.generator.generator.MeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.generator.generators.GridMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        InputHandler handler = GeneratorInputHandler.getInputHandler(args);

        DotGen generator = new DotGen(getMeshGenerator(handler));
        Mesh myMesh = generator.generate();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, handler.getOptionValue(GeneratorInputHandler.getGeneratorOption(OutputOption.OPTION_STR)));
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

        if(meshType.equals("grid")) {
            double squareSize = getGridMeshSquareSize(handler);

            try {
                return new GridMeshGenerator(meshDimensions[0], meshDimensions[1], squareSize);
            } catch (SquaresFittingException e) {
                System.out.printf(
                        "Not all squares of size %.2f can fit in %dx%d\n",
                        squareSize,
                        meshDimensions[0],
                        meshDimensions[1]
                );
                handler.printHelp();
                System.exit(1);
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
            System.out.println("Input does not match expected format!");
            handler.printHelp();
            System.exit(1);
        }

        // Get given dimensions
        String[] dimensionStr = dimensionInput.split("x");

        // Convert input to numbers
        double width = Integer.parseInt(dimensionStr[0]);
        double height = Integer.parseInt(dimensionStr[1]);

        if((width % 1) != 0 || (height % 1) != 0) { // Is the given dimensions floating point numbers?
            System.out.println("Cannot have floating point dimensions!");
            handler.printHelp();
            System.exit(1);
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
            System.out.printf("%s is not a number!\n", squareSizeInput);
            handler.printHelp();
            System.exit(1);
        }

        return Double.parseDouble(squareSizeInput);
    }

}
