package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import java.awt.*;

public class DimensionProperty extends Property {
    public static final String KEY = "dimension";
    private static final String VALUE_FORMAT = "%dx%d";

    /**
     * @param dimensions The dimensions stored by this property
     */
    public DimensionProperty(int[] dimensions) {
        super(KEY, String.format(VALUE_FORMAT, dimensions[0], dimensions[1]));
    }
}