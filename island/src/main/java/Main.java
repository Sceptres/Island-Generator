import ca.mcmaster.cas.se2aa4.a2.island.cli.IslandInputHandler;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshReader;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshWriter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        InputHandler handler = IslandInputHandler.getInputHandler(args);

        String input = IslandInputHandler.getInputMesh(handler);
        String output = IslandInputHandler.getOutputFile(handler);

        String mode = IslandInputHandler.getIslandMode(handler);

        MeshReader meshReader = new MeshReader(input);
        Mesh mesh = meshReader.getMesh();

        mesh.getPolygons().forEach(Tile::new);

        MeshWriter writer = new MeshWriter();
        writer.write(mesh, output);
    }
}
