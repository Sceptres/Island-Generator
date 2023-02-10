package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Property {
    private Structs.Property property;

    /**
     *
     * @param key pass in key
     * @param value pass in value
     */
    public Property(String key, String value) {
        this.property = Structs.Property.newBuilder().setKey(key).setValue(value).build();
    }

    /**
     *
     * @return the key of this property
     */
    public String getKey(){
        return this.property.getKey();
    }

    /**
     *
     * @return the value of this property
     */
    public String getValue(){
        return this.property.getValue();
    }


}
