package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties.Property;

import java.util.List;

public interface IProperties {
    /**
     *
     * @param property The {@link Property} to add to this vertex
     */
    void addProperty(Property property);

    /**
     *
     * @param properties All the properties to add
     */
    void addAllProperties(Iterable<? extends Property> properties);

    /**
     *
     * @param key The key of the property to get
     * @return The {@link Property} with the key
     */
    Property getProperty(String key);

    /**
     *
     * @return All the properties
     */
    List<Property> getProperties();
}
