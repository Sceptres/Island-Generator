package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.generator.coloring.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.coloring.generators.RandomColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.generator.mesh.generator.generators.GridMeshGenerator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DotGenTest {

    @Test
    public void meshIsNotNull() {
        ColorGenerator[] generators = new ColorGenerator[]{
                new RandomColorGenerator(),
                new RandomColorGenerator(),
                new RandomColorGenerator()
        };
        DotGen generator = new DotGen(new GridMeshGenerator(generators, 500, 500, 20));
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

}
