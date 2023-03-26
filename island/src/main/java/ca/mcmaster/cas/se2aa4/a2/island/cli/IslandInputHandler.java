package ca.mcmaster.cas.se2aa4.a2.island.cli;

import ca.mcmaster.cas.se2aa4.a2.island.cli.options.*;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.AltimeterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.HillyAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.LagoonAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.elevation.altimetry.profiles.VolcanoAltimeter;
import ca.mcmaster.cas.se2aa4.a2.island.generator.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.generator.generators.LagoonIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.generator.generators.RandomIslandGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.geometry.shapes.Star;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.SoilAbsorptionProfile;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.profiles.DrySoilAbsorption;
import ca.mcmaster.cas.se2aa4.a2.island.humidity.soil.profiles.WetSoilAbsorption;
import ca.mcmaster.cas.se2aa4.a2.island.mesh.IslandMesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.InputHandler;
import ca.mcmaster.cas.se2aa4.a2.mesh.cli.exceptions.IllegalInputException;
import org.apache.commons.cli.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class IslandInputHandler {
    private final static Map<String, Option> ISLAND_OPTIONS = Map.of(
            ModeOption.OPTION_STR,  new ModeOption(),
            InputOption.OPTION_STR, new InputOption(),
            OutputOption.OPTION_STR, new OutputOption(),
            ShapeOption.OPTION_STR, new ShapeOption(),
            LakesOption.OPTION_STR, new LakesOption(),
            AltimeterProfileOption.OPTION_STR, new AltimeterProfileOption(),
            AquiferOption.OPTION_STR, new AquiferOption(),
            RiversOption.OPTION_STR, new RiversOption(),
            SoilAbsorptionProfileOption.OPTION_STR, new SoilAbsorptionProfileOption()

    );

    /**
     *
     * @param opt The {@link Option} to get from the set options
     * @return The {@link Option} instance that matches the given option string
     */
    public static Option getIslandOption(String opt) {
        if(!ISLAND_OPTIONS.containsKey(opt))
            throw new IllegalArgumentException("This option does not exist!");

        return ISLAND_OPTIONS.get(opt);
    }

    /**
     *
     * @param args The arguments passed in through the CMD
     * @return The input handler that has parsed these arguments
     * @throws IllegalInputException if the input is inappropriate
     */
    public static InputHandler getInputHandler(String[] args) throws IllegalInputException {
        return new InputHandler(args, ISLAND_OPTIONS);
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the output file from the user's input
     * @return The name of the file to export to
     * @throws IllegalInputException if the input is inappropriate
     */
    public static String getOutputFile(InputHandler handler) throws IllegalInputException {
        String file = handler.getOptionValue(IslandInputHandler.getIslandOption(OutputOption.OPTION_STR));

        if(!file.endsWith(".mesh"))
            handler.printHelp("Can only write to .mesh files.");

        return file;
    }

    /**
     *
     * @param handler The {@link InputHandler} to retrieve generation mode from the user's input
     * @param mesh The {@link Mesh} to generate island from
     * @return The {@link IslandGenerator} to use
     * @throws IllegalInputException if the input is inappropriate
     */
    public static IslandGenerator getIslandMode(InputHandler handler, IslandMesh mesh) throws IllegalInputException {
        String mode = handler.getOptionValue(
                IslandInputHandler.getIslandOption(ModeOption.OPTION_STR),
                ModeOption.DEFAULT_VALUE
        );

        IslandGenerator generator = null;

        int[] meshDimension = mesh.getDimension();
        Vertex meshCenter = new Vertex(meshDimension[0]/2f, meshDimension[1]/2f);
        double diagonalLength = Math.hypot(meshDimension[0]/2f, meshDimension[1]/2f);

        int numAquifers = IslandInputHandler.getNumAquifers(handler);
        int numRivers = IslandInputHandler.getNumRivers(handler);
        Shape shape = IslandInputHandler.getShapeInput(handler, meshCenter, diagonalLength);

        if(mode.equals("lagoon"))
            generator = new LagoonIslandGenerator(mesh, shape, numAquifers, numRivers);
        else if(mode.equals("random")) {
            int numLakes = IslandInputHandler.getNumLakes(handler);
            AltimeterProfile altimeterProfile = IslandInputHandler.getAltimeterInput(handler);
            generator = new RandomIslandGenerator(mesh, shape, altimeterProfile, numLakes, numAquifers, numRivers);
        } else
            handler.printHelp("Invalid mode: " + mode);

        return generator;

    }

    /**
     *
     * @param handler The {@link InputHandler} to get the data from the user's input
     * @return The passed in data for the input mesh file path
     * @throws IllegalInputException if the input is inappropriate
     */
    public static String getInputMesh(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(IslandInputHandler.getIslandOption(InputOption.OPTION_STR));

        if(!Files.exists(Path.of(value))) { // Does this file not exist?
            String message = String.format("Cannot find %s. Please try again!\n", value);
            handler.printHelp(message);
        } else if(!value.endsWith(".mesh")) { // Is the given file not a mesh?
            String message = String.format("%s is not a mesh file! Please insert correct file format.\n", value);
            handler.printHelp(message);
        }

        return value;
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the number of lakes input from the user's input
     * @return The number of lakes set by the user
     * @throws IllegalInputException if the input is inappropriate
     */
    public static int getNumLakes(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(LakesOption.OPTION_STR),
                LakesOption.DEFAULT_VALUE
        );

        int numLakes = -1;

        try {
            numLakes = Integer.parseInt(value);

            if(numLakes < 0)
                throw new IllegalArgumentException();
        } catch(NumberFormatException e) {
            String message = String.format("Invalid number %s!", value);
            handler.printHelp(message);
        } catch (IllegalArgumentException e) {
            handler.printHelp("Cannot have a negative number of lakes!");
        }

        return numLakes;
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the number of aquifers input from the user's input
     * @return The number of aquifers set by the user
     * @throws IllegalInputException if the input is inappropriate
     */
    public static int getNumAquifers(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(AquiferOption.OPTION_STR),
                AquiferOption.DEFAULT_VALUE
        );

        int numAquifers = -1;

        try {
            numAquifers = Integer.parseInt(value);

            if(numAquifers < 0)
                throw new IllegalArgumentException();
        } catch(NumberFormatException e) {
            String message = String.format("Invalid number %s!", value);
            handler.printHelp(message);
        } catch(IllegalArgumentException e) {
            String message = "Cannot have negative number of aquifers";
            handler.printHelp(message);
        }

        return numAquifers;
    }

    /**
     *
     * @param handler The {@link InputHandler} to get the number of rivers from the user's input
     * @return The number of rivers inserted by the user. 0 otherwise
     * @throws IllegalInputException if the input is inappropriate
     */
    public static int getNumRivers(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(RiversOption.OPTION_STR),
                RiversOption.DEFAULT_VALUE
        );

        int numRivers = -1;

        try {
            numRivers = Integer.parseInt(value);

            if(numRivers < 0)
                throw new IllegalArgumentException();
        } catch(NumberFormatException e) {
            String message = String.format("Invalid number of rivers %s!", value);
            handler.printHelp(message);
        } catch(IllegalArgumentException e) {
            handler.printHelp("Cannot have a negative number of rivers!");
        }

        return numRivers;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the data from the user's input
     * @param center The center {@link Vertex} of the mesh
     * @param diagonalLength The length from the center to a corner in the mesh
     * @return The {@link Shape} that matches cmd input
     * @throws IllegalInputException if the input is inappropriate
     */
    public static Shape getShapeInput(InputHandler handler, Vertex center, double diagonalLength) throws IllegalInputException {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(ShapeOption.OPTION_STR),
                ShapeOption.DEFAULT_VALUE
        );

        Shape shape = null;

        switch (value) {
            case "circle"   -> shape = new Circle(diagonalLength/2f, center);
            case "oval"     -> shape = new Oval(diagonalLength/3f, diagonalLength/1.5f, center);
            case "star"     -> shape = new Star(diagonalLength/1.5f, diagonalLength/3f, 8, center);
            default         -> handler.printHelp("Invalid shape!");
        }

        return shape;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the {@link AltimeterProfile} from the user's input
     * @return The {@link AltimeterProfile} that matches user input
     * @throws IllegalInputException if the input is inappropriate
     */
    public static AltimeterProfile getAltimeterInput(InputHandler handler) throws IllegalInputException {
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(AltimeterProfileOption.OPTION_STR),
                AltimeterProfileOption.DEFAULT_VALUE
        );

        AltimeterProfile profile = null;

        switch(value) {
            case "hills"        -> profile = new HillyAltimeter();
            case "volcano"      -> profile = new VolcanoAltimeter();
            case "lagoon"       -> profile = new LagoonAltimeter();
            default -> handler.printHelp("Invalid altimeter option!");
        }

        return profile;
    }

    /**
     *
     * @param handler The {@link InputHandler} to extract the {@link SoilAbsorptionProfile} from the user's input
     * @return The {@link SoilAbsorptionProfile} that matches the user input
     * @throws IllegalInputException if the input is inappropriate
     */
    public static SoilAbsorptionProfile getSoilAbsorptionProfile(InputHandler handler) throws IllegalInputException{
        String value = handler.getOptionValue(
                IslandInputHandler.getIslandOption(SoilAbsorptionProfileOption.OPTION_STR),
                SoilAbsorptionProfileOption.DEFAULT_VALUE
        );

        SoilAbsorptionProfile soilAbsorptionProfile = null;

        switch (value){
            case "wet" -> soilAbsorptionProfile = new WetSoilAbsorption();
            case "dry" -> soilAbsorptionProfile = new DrySoilAbsorption();
            default ->  handler.printHelp("Invalid soil absorption profile!");
        }

        return soilAbsorptionProfile;
    }
}