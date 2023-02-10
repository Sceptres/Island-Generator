package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;

public class Segments extends UniqueList<Segment> {
    @Override
    public boolean add(Segment t) {
        t.setIndex(super.size());
        t.calculateColor();
        return super.add(t);
    }
}
