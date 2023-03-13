package ca.mcmaster.cas.se2aa4.a2.island.generator;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;

public interface IslandGenerator {

    /**
     *
     * @param mesh The given mesh to generate island from
     */
    void generate(Mesh mesh);
}
