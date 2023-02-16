package ca.mcmaster.cas.se2aa4.a2.mesh.adt.polygon;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.List;

public class Polygons extends UniqueList<Polygon> implements Converter<List<Structs.Polygon>> {
    @Override
    public boolean add(Polygon t) {
        boolean isAdded = super.add(t);

        if(isAdded) {
            t.setIndex(super.size()-1);
        } else {
            Polygon polygon = super.stream().filter(p -> p.equals(t)).findFirst().get();
            t.setIndex(polygon.getIndex());
        }

        return isAdded;
    }

    @Override
    public List<Structs.Polygon> getConverted() {
        return super.stream().map(Polygon::getConverted).toList();
    }
}
