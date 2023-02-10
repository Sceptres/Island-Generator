package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;

import java.util.ArrayList;

public class Vertices extends UniqueList<Vertex> {
    @Override
    public boolean add(Vertex t) {
        t.setIndex(super.size());
        return super.add(t);
    }
}
