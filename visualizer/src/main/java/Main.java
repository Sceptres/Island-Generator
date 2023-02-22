import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.VisualizerInputHandler;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.DebugOption;
import ca.mcmaster.cas.se2aa4.a2.visualizer.cli.options.InputOption;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        InputHandler handler = VisualizerInputHandler.getInputHandler(args);

        // Extracting command line parameters
        String input = getInputMesh(handler);
        String output = getOutputFile(handler);
        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);

        Mesh mesh = new Mesh(aMesh);

        // Creating the Canvas to draw the mesh
        int[] dimensions = mesh.getDimension();
        int width = dimensions[0];
        int height = dimensions[1];
        Graphics2D canvas = SVGCanvas.build(width, height);

        // Is the visualizer in debug mode?
        boolean isDebug = handler.hasOption(VisualizerInputHandler.getVisualizerOption(DebugOption.OPTION_STR));

        GraphicRenderer renderer = new GraphicRenderer(isDebug);
        // Painting the mesh on the canvas
        renderer.render(mesh, canvas);
        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the data from
     * @return The passed in data for the input mesh file path
     */
    private static String getInputMesh(InputHandler handler) {
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
    private static String getOutputFile(InputHandler handler) {
        String value = handler.getOptionValue(VisualizerInputHandler.getVisualizerOption(OutputOption.OPTION_STR));

        if(!value.endsWith(".svg")){
            handler.printHelp("The given output file is not an svg!");
        }

        return value;
    }
}
