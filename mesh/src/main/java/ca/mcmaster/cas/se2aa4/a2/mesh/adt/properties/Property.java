package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Property {
    private final String key;
    private final String value;

    /**
     *
     * @param key pass in key
     * @param value pass in value
     */
    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     *
     * @param property The {@link Structs.Property} to wrap into this class
     */
    public Property(Structs.Property property) {
        this.key = property.getKey();
        this.value = property.getValue();
    }

    /**
     *
     * @return the key of this property
     */
    public String getKey(){
        return this.key;
    }

    /**
     *
     * @return the value of this property
     */
    public String getValue(){
        return this.value;
    }

    /**
     *
     * @return A {@link Structs.Property} instance that holds information of this property
     */
    public Structs.Property getProperty() {
        return Structs.Property.newBuilder().setKey(this.key).setValue(this.value).build();
    }


}
