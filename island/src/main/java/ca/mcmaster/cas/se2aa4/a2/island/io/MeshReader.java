package ca.mcmaster.cas.se2aa4.a2.island.io;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

import java.io.IOException;

public class MeshReader {
    private final IslandMesh mesh;

    /**
     *
     * @param filename The file to read the mesh from
     * @throws IOException If there was a problem reading the mesh
     */
    public MeshReader(String filename) throws IOException {
        MeshFactory factory = new MeshFactory();

        Structs.Mesh mesh = factory.read(filename);
        Mesh adtMesh = new Mesh(mesh);
        this.mesh = new IslandMesh(adtMesh);
    }

    /**
     *
     * @return {@link MeshReader#mesh} getter
     */
    public IslandMesh getMesh() {
        return this.mesh;
    }
}
