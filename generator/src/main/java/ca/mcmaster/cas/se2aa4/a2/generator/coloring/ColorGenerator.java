package ca.mcmaster.cas.se2aa4.a2.generator.coloring;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Colorable;

import java.awt.*;

public interface ColorGenerator<T extends Colorable> {
    /**
     *
     * Sets the {@link Color}
     */
    void generateColor(T colorable);
}
