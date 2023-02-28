import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.cli.GeneratorInputHandler;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.options.OutputOption;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        InputHandler handler = GeneratorInputHandler.getInputHandler(args);

        // Generate the mesh
        DotGen generator = new DotGen(GeneratorInputHandler.getMeshGenerator(handler));
        Mesh myMesh = generator.generate();

        // File name
        String file = GeneratorInputHandler.getOutputFile(handler);

        // Write to mesh file
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh.getConverted(), file);
    }
}