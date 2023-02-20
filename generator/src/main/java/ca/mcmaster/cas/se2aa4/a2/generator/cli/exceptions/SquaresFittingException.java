package ca.mcmaster.cas.se2aa4.a2.generator.cli.exceptions;

public class SquaresFittingException extends IllegalArgumentException {
    public SquaresFittingException(int width, int height) {
        super(String.format("All squares must fit within the %dx%d mesh.", width, height));
    }
}
