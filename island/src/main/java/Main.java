import ca.mcmaster.cas.se2aa4.a2.island.cli.IslandInputHandler;
import ca.mcmaster.cas.se2aa4.a2.island.generator.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshReader;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshWriter;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        InputHandler handler = IslandInputHandler.getInputHandler(args);

        String input = IslandInputHandler.getInputMesh(handler);
        String output = IslandInputHandler.getOutputFile(handler);

        MeshReader meshReader = new MeshReader(input);
        IslandMesh mesh = meshReader.getMesh();

        IslandGenerator generator = IslandInputHandler.getIslandMode(handler, mesh);
        generator.generate();

        MeshWriter writer = new MeshWriter();
        writer.write(mesh, output);
    }
}