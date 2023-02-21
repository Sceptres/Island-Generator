package ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions;

/**
 * Exception that represents that a grid mesh has the wrong dimensions
 */
public class NotSquareMeshException extends IllegalArgumentException {
    public NotSquareMeshException() {
        super("Dimensions must be equal for grid mesh!");
    }
}
