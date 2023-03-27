import ca.mcmaster.cas.se2aa4.a2.island.cli.IslandInputHandler;
import ca.mcmaster.cas.se2aa4.a2.island.generator.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.hook.Hook;
import ca.mcmaster.cas.se2aa4.a2.island.hook.hooks.HeatMap;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.SoilAbsorptionProfile;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshReader;
import ca.mcmaster.cas.se2aa4.a2.island.io.MeshWriter;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.exceptions.IllegalInputException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            InputHandler handler = IslandInputHandler.getInputHandler(args);

            String input = IslandInputHandler.getInputMesh(handler);
            String output = IslandInputHandler.getOutputFile(handler);
            SoilAbsorptionProfile soilAbsorptionProfile = IslandInputHandler.getSoilAbsorptionProfile(handler);

            MeshReader meshReader = new MeshReader(input);
            IslandMesh mesh = new IslandMesh(meshReader.getMesh(), soilAbsorptionProfile);

            IslandGenerator generator = IslandInputHandler.getIslandMode(handler, mesh);
            generator.generate();

            Hook heatMap = IslandInputHandler.getHook(handler);
            heatMap.apply(mesh.getTiles());

            MeshWriter writer = new MeshWriter();
            writer.write(mesh.getConverted(), output);

            System.out.printf("Island Seed: %d\n", generator.getSeed());
        } catch(IllegalInputException e) {
            System.exit(1);
        }
    }
}