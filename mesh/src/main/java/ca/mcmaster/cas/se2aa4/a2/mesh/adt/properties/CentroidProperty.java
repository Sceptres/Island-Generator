package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

public class CentroidProperty extends Property {
    public static final String KEY = "centroid";
    private static final String FORMAT = "%b";

    /**
     *
     * @param isCentroid Value of this property
     */
    public CentroidProperty(boolean isCentroid) {
        super(CentroidProperty.KEY, String.format(CentroidProperty.FORMAT, isCentroid));
    }
}
