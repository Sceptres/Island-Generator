package ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures.UniqueList;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Converter;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Copier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Vertices extends UniqueList<Vertex> implements Copier<Vertices>, Converter<List<Structs.Vertex>> {
    @Override
    public boolean add(Vertex t) {
        boolean isAdded = super.add(t);
        if(isAdded)
            t.setIndex(super.size()-1);
        else {
            Vertex vertex = super.stream().filter(v -> v.equals(t)).findFirst().get();
            t.copy(vertex);
        }

        return isAdded;
    }

    @Override
    public boolean addAll(Collection<? extends Vertex> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Vertex> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Structs.Vertex> getConverted() {
        return super.stream().map(Vertex::getConverted).toList();
    }

    @Override
    public void copy(Vertices vertices) {
        super.clear();
        this.addAll(vertices);
    }
}
