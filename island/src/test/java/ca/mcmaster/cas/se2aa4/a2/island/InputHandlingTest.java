package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.island.cli.IslandInputHandler;
import ca.mcmaster.cas.se2aa4.a2.island.cli.options.ModeOption;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.HillyAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.VolcanoAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.generator.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.SoilAbsorptionProfile;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@Testable
public class InputHandlingTest {

    private static final PrintStream originalOut = System.out;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static final String[] input = new String[] {
            "-in", "../generator/sample.mesh",
            "-out", "island.mesh",
            "", ""
    };

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void altitudeTest() {
        input[4] = "--altitude";
        input[5] = "volcano";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            AltimeterProfile profile;

            handler.set(IslandInputHandler.getInputHandler(input));
            profile = IslandInputHandler.getAltimeterInput(handler.get());
            assertInstanceOf(VolcanoAltimeter.class, profile);

            input[5] = "hills";

            handler.set(IslandInputHandler.getInputHandler(input));
            profile = IslandInputHandler.getAltimeterInput(handler.get());
            assertInstanceOf(HillyAltimeter.class, profile);
        });

        assertThrows(Exception.class, () -> {
            AltimeterProfile profile;

            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getAltimeterInput(handler.get());
            assertEquals("Invalid altimeter option!", outContent.toString());
        });
    }

    @Test
    public void aquiferTest() {
        input[4] = "--aquifers";
        input[5] = "3";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            int numAquifers;

            handler.set(IslandInputHandler.getInputHandler(input));
            numAquifers = IslandInputHandler.getNumAquifers(handler.get());
            assertEquals(numAquifers, 3);

            input[5] = "0";

            handler.set(IslandInputHandler.getInputHandler(input));
            numAquifers = IslandInputHandler.getNumAquifers(handler.get());
            assertEquals(numAquifers, 0);
        });

        assertThrows(Exception.class, () -> {
            input[5] = "-1";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumAquifers(handler.get());
            assertEquals("Cannot have negative number of aquifers", outContent.toString());

            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumAquifers(handler.get());
            assertEquals("Invalid number wrong input!", outContent.toString());
        });
    }

    /*@Test
    public void inputOptionTest(){
        input[4] = "";
        input[5] = "";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            String mesh;

            handler.set(IslandInputHandler.getInputHandler(input));
            mesh = IslandInputHandler.getInputMesh(handler.get());
            assertEquals(mesh, "../generator/sample.mesh");

            input[1] = "a.mesh";

            handler.set(IslandInputHandler.getInputHandler(input));
            mesh = IslandInputHandler.getInputMesh(handler.get());
            assertEquals(mesh, "a.mesh");
        });

        assertThrows(Exception.class,() -> {
            input[1] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getInputMesh(handler.get());
            assertEquals("wrong input is not a mesh file! Please insert correct file format.\n", outContent.toString());
        });
    }*/

    @Test
    public void lakesOption() {
        input[4] = "--lakes";
        input[5] = "5";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            int numLakes;

            handler.set(IslandInputHandler.getInputHandler(input));
            numLakes = IslandInputHandler.getNumLakes(handler.get());
            assertEquals(numLakes, 5);

            input[5] = "0";

            handler.set(IslandInputHandler.getInputHandler(input));
            numLakes = IslandInputHandler.getNumLakes(handler.get());
            assertEquals(numLakes, 0);
        });

        assertThrows(Exception.class, () -> {
            input[5] = "-1";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumLakes(handler.get());
            assertEquals("Cannot have a negative number of lakes!", outContent.toString());

            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumLakes(handler.get());
            assertEquals("Invalid number wrong input!", outContent.toString());
        });
    }

    @Test
    public void outputOptionTest(){
        input[4] = "";
        input[5] = "";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            String mesh;

            handler.set(IslandInputHandler.getInputHandler(input));
            mesh = IslandInputHandler.getOutputFile(handler.get());
            assertEquals(mesh, "island.mesh");

            input[3] = "a.mesh";

            handler.set(IslandInputHandler.getInputHandler(input));
            mesh = IslandInputHandler.getOutputFile(handler.get());
            assertEquals(mesh, "a.mesh");
        });

        assertThrows(Exception.class,() -> {
            input[3] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getOutputFile(handler.get());
            assertEquals("Can only write to .mesh files.", outContent.toString());
        });
    }

    @Test
    public void riversOptionTest(){
        input[4] = "--rivers";
        input[5] = "5";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            int numRivers;

            handler.set(IslandInputHandler.getInputHandler(input));
            numRivers = IslandInputHandler.getNumRivers(handler.get());
            assertEquals(numRivers, 5);

            input[5] = "0";

            handler.set(IslandInputHandler.getInputHandler(input));
            numRivers = IslandInputHandler.getNumRivers(handler.get());
            assertEquals(numRivers, 0);
        });

        assertThrows(Exception.class, () -> {
            input[5] = "-1";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumRivers(handler.get());
            assertEquals("Cannot have a negative number of rivers!", outContent.toString());

            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getNumRivers(handler.get());
            assertEquals("Invalid number of rivers wrong input!", outContent.toString());
        });
    }

    @Test
    public void shapeOptionTest(){
        input[4] = "--shape";
        input[5] = "circle";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            Shape shape;
            Vertex center = new Vertex(0,0);


            handler.set(IslandInputHandler.getInputHandler(input));
            shape = IslandInputHandler.getShapeInput(handler.get(), center ,100);
            assertInstanceOf(Circle.class, shape);

        });

        assertThrows(Exception.class,() -> {
            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getShapeInput(handler.get(), new Vertex(0,0) ,100);
            assertEquals("Invalid shape!", outContent.toString());
        });
    }

    @Test
    public void soilAbsorptionProfileOptionTest(){
        input[4] = "--soil";
        input[5] = "wet";

        AtomicReference<InputHandler> handler = new AtomicReference<>();

        assertDoesNotThrow(() -> {
            SoilAbsorptionProfile soilAbsorptionProfile;

            handler.set(IslandInputHandler.getInputHandler(input));
            soilAbsorptionProfile = IslandInputHandler.getSoilAbsorptionProfile(handler.get());
            assertInstanceOf(SoilAbsorptionProfile.class, soilAbsorptionProfile);

        });

        assertThrows(Exception.class,() -> {
            input[5] = "wrong input";
            handler.set(IslandInputHandler.getInputHandler(input));
            IslandInputHandler.getSoilAbsorptionProfile(handler.get());
            assertEquals("Invalid soil absorption profile!", outContent.toString());
        });
    }
}

