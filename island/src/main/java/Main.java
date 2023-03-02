import ca.mcmaster.cas.se2aa4.a2.island.io.MeshReader;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshWriter;
import ca.mcmaster.cas.se2aa4.a2.island.tile.Tile;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MeshReader meshReader = new MeshReader(args[0]);
        Mesh mesh = meshReader.getMesh();

        mesh.getPolygons().forEach(Tile::new);

        MeshWriter writer = new MeshWriter();
        writer.write(mesh, args[0]);
    }
}
