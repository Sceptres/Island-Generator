package ca.mcmaster.cas.se2aa4.a2.island.geometry;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.services.Positionable;

public interface Shape {
    <T extends Positionable<Double>> boolean contains(T t);
}
