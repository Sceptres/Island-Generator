package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

public interface Indexable {
    /**
     *
     * @param index The index that belongs to this object
     */
    void setIndex(int index);

    /**
     *
     * @return The index of this object
     */
    int getIndex();
}
