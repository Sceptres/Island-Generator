package ca.mcmaster.cas.se2aa4.a2.mesh.adt.datastructures;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;

import java.util.ArrayList;

public class UniqueList<T> extends ArrayList<T> {
    /**
     * Keep order while insuring uniqueness of each element
     * @param t The {@link T} to add
     * @return Has the segment been added?
     */
    @Override
    public boolean add(T t) {
        if(!super.contains(t))
            return super.add(t);

        return false;
    }
}
