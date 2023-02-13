package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.ArrayList;
import java.util.List;

public class Vertices extends UniqueList<Vertex> implements Converter<List<Structs.Vertex>> {
    @Override
    public boolean add(Vertex t) {
        t.setIndex(super.size());
        return super.add(t);
    }

    @Override
    public List<Structs.Vertex> getConverted() {
        return super.stream().map(Vertex::getConverted).toList();
    }
}
