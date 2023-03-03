package ca.mcmaster.cas.se2aa4.a2.island.io;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.io.IOException;

public class MeshWriter {
    /**
     *
     * @param mesh The {@link Mesh} to write
     * @param filename The file to write the mesh to
     * @throws IOException If there was an issue write to the disk
     */
    public void write(Mesh mesh, String filename) throws IOException {
        MeshFactory factory = new MeshFactory();
        factory.write(mesh.getConverted(), filename);
    }
}
