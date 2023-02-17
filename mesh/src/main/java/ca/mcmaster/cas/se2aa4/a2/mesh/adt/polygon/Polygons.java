package ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Copier;

import java.util.Collection;
import java.util.List;

public class Polygons extends UniqueList<Polygon> implements Copier<Polygons>, Converter<List<Structs.Polygon>> {
    @Override
    public boolean add(Polygon t) {
        boolean isAdded = super.add(t);

        if(isAdded) {
            t.setIndex(super.size()-1);
        } else {
            Polygon polygon = super.stream().filter(p -> p.equals(t)).findFirst().get();
            t.copy(polygon);
        }

        return isAdded;
    }

    @Override
    public boolean addAll(Collection<? extends Polygon> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Polygon> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Structs.Polygon> getConverted() {
        return super.stream().map(Polygon::getConverted).toList();
    }


    @Override
    public void copy(Polygons polygons) {
        super.clear();
        this.addAll(polygons);
    }
}
