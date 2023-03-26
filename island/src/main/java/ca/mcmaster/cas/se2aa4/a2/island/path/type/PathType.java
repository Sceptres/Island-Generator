package ca.mcmaster.cas.se2aa4.a2.island.path.type;

import ca.mcmaster.cas.se2aa4.a2.island.color.ColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.color.colors.paths.ClearColorGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.color.colors.paths.RiverColorGenerator;

public enum PathType {
    RIVER(new RiverColorGenerator()),
    NONE(new ClearColorGenerator());

    private final ColorGenerator colorGenerator;

    PathType(ColorGenerator colorGenerator) {
        this.colorGenerator = colorGenerator;
    }

    /**
     *
     * @return The {@link ColorGenerator} of this path type
     */
    public ColorGenerator getColorGenerator() {
        return this.colorGenerator;
    }
}
