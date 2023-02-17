package ca.mcmaster.cas.se2aa4.a2.mesh.adt.services;

public interface Converter<T> {
    /**
     *
     * @return The subclass converted to type {@link T}
     */
    T getConverted();
}
