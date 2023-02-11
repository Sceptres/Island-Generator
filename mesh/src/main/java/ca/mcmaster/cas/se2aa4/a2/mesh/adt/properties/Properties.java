package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import java.util.ArrayList;
import java.util.Optional;

public class Properties extends ArrayList<Property> {
    @Override
    public boolean add(Property property) {
        Optional<Property> keyProperty = super.stream().filter(p -> p.getKey().equals(property.getKey())).findFirst();
        keyProperty.ifPresent(super::remove);

        return super.add(property);
    }
}
