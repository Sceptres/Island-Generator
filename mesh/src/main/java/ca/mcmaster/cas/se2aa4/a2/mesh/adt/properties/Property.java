package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.Objects;

public class Property implements Converter<Structs.Property> {
    private final String key;
    private final String value;

    /**
     *
     * @param key pass in key
     * @param value pass in value
     */
    protected Property(String key, String value) {
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

    @Override
    public Structs.Property getConverted() {
        return Structs.Property.newBuilder().setKey(this.key).setValue(this.value).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(key, property.key) && Objects.equals(value, property.value);
    }
}
