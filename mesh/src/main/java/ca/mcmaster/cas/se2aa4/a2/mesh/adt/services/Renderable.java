package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import java.awt.*;

public interface Renderable {

    default void render(Graphics2D canvas) {
        // Store old canvas settings
        Color oldColor = canvas.getColor();
        Stroke oldStroke = canvas.getStroke();

        // Draw the object
        this.draw(canvas);

        // Reset color
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    void draw(Graphics2D canvas);
}
