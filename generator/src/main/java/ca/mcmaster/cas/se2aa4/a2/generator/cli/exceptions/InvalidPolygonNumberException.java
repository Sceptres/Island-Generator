package ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions;

public class InvalidPolygonNumberException extends IllegalArgumentException {
    public InvalidPolygonNumberException() {
        super("The number of polygons to be generated in an irregular mesh must be greater than 2!");
    }
}
