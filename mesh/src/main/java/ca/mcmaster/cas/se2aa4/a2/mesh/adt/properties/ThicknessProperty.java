package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.Util;

import java.awt.*;

public class ThicknessProperty extends Property{
    public static final String KEY = "thickness";

    private static final String VALUE_FORMAT = "%f";
    public ThicknessProperty(float x) {
        super(KEY, String.format(VALUE_FORMAT, Util.precision(x)));
    }

}
