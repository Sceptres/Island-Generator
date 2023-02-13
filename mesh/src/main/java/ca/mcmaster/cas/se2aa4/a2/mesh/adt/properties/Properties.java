package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Properties extends ArrayList<Property> implements Converter<List<Structs.Property>> {
    @Override
    public boolean add(Property property) {
        Optional<Property> keyProperty = super.stream().filter(p -> p.getKey().equals(property.getKey())).findFirst();
        keyProperty.ifPresent(super::remove);

        return super.add(property);
    }

    @Override
    public List<Structs.Property> getConverted() {
        return super.stream().map(Property::getConverted).toList();
    }
}
