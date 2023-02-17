package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import java.awt.*;

public interface Thickenable {
    /**
     *
     * @param x The new {@link Color} to set to the object
     */
    void setThickness(float x);

    /**
     *
     * @return The {@link Color} assigned to the object
     */
    float getThickness();
}
