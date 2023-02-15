package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;

import java.util.ArrayList;
import java.util.List;

public class Vertices extends UniqueList<Vertex> implements Converter<List<Structs.Vertex>> {
    @Override
    public boolean add(Vertex t) {
        boolean isAdded = super.add(t);
        if(isAdded)
            t.setIndex(super.size()-1);
        else {
            Vertex vertex = super.stream().filter(v -> v.equals(t)).findFirst().get();
            t.setIndex(vertex.getIndex());
        }

        return isAdded;
    }

    @Override
    public List<Structs.Vertex> getConverted() {
        return super.stream().map(Vertex::getConverted).toList();
    }
}
