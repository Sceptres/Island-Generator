package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.List;

public class Segments extends UniqueList<Segment> implements Converter<List<Structs.Segment>> {
    @Override
    public boolean add(Segment t) {
        boolean isAdded = super.add(t);

        if(isAdded) {
            t.setIndex(super.size()-1);
            t.calculateColor();
        } else {
            Segment segment = super.stream().filter(s -> s.equals(t)).findFirst().get();
            t.setIndex(super.indexOf(segment));
        }
        return isAdded;
    }

    @Override
    public List<Structs.Segment> getConverted() {
        return super.stream().map(Segment::getConverted).toList();
    }
}
