package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.List;

public class Segments extends UniqueList<Segment> implements Converter<List<Structs.Segment>> {
    @Override
    public boolean add(Segment t) {
        t.setIndex(super.size());
        t.calculateColor();
        return super.add(t);
    }

    @Override
    public List<Structs.Segment> getConverted() {
        return super.stream().map(Segment::getConverted).toList();
    }
}
