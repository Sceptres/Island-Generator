package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import java.util.ArrayList;

public class Properties extends ArrayList<Property> {
    @Override
    public boolean add(Property property) {
        super.remove(property);
        return super.add(property);
    }
}
