package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import java.awt.Color;

public interface Colorable {
    /**
     *
     * @param color The new {@link Color} to set to the object
     */
    void setColor(Color color);

    /**
     *
     * @return The {@link Color} assigned to the object
     */
    Color getColor();

}
