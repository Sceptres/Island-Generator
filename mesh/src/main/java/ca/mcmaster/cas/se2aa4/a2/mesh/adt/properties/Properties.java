package ca.mcmaster.cas.se2aa4.a2.mesh.adt.properties;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Copier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Properties extends ArrayList<Property> implements Copier<Properties>, Converter<List<Structs.Property>> {
    @Override
    public boolean add(Property property) {
        Optional<Property> keyProperty = super.stream().filter(p -> p.getKey().equals(property.getKey())).findFirst();
        keyProperty.ifPresent(super::remove);

        return super.add(property);
    }

    @Override
    public boolean addAll(Collection<? extends Property> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Property> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Structs.Property> getConverted() {
        return super.stream().map(Property::getConverted).toList();
    }


    @Override
    public void copy(Properties properties) {
        super.clear();
        this.addAll(properties);
    }
}
