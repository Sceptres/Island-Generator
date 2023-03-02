package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

import java.util.List;

public interface Neighborable<T> {
    /**
     *
     * @param t The element to add as a neighbor
     * @return True if the element is a neighbor
     */
    boolean isNeighbor(T t);

    /**
     *
     * @return The {@link List} of neighbors
     */
    List<T> getNeighbors();

    /**
     *
     * @param t The element to add as a neighbor
     */
    void addNeighbor(T t);

    /**
     *
     * @param t The list of elements to add as neighbors
     */
    void addNeighbors(List<T> t);
}
