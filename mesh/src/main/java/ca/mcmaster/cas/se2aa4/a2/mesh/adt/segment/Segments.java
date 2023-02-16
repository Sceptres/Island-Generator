package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Copier;

import java.util.Collection;
import java.util.List;

public class Segments extends UniqueList<Segment> implements Copier<Segments>, Converter<List<Structs.Segment>> {
    @Override
    public boolean add(Segment t) {
        boolean isAdded = super.add(t);

        if(isAdded) {
            t.setIndex(super.size()-1);
        } else {
            Segment segment = super.stream().filter(s -> s.equals(t)).findFirst().get();
            t.copy(segment);
        }
        return isAdded;
    }

    @Override
    public boolean addAll(Collection<? extends Segment> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Segment> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Structs.Segment> getConverted() {
        return super.stream().map(Segment::getConverted).toList();
    }

    @Override
    public void copy(Segments segments) {
        super.clear();
        this.addAll(segments);
    }
}
