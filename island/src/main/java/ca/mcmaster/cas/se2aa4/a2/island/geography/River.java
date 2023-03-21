package ca.mcmaster.cas.se2aa4.a2.island.geography;

import ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment.Segment;
import ca.mcmaster.cas.se2aa4.a2.mesh.adt.vertex.Vertex;

import java.util.ArrayList;
import java.util.List;

public class River {
    private final Vertex start;
    private final List<Segment> segments;

    public River(Vertex start) {
        this.start = start;
        this.segments = new ArrayList<>();
    }
}
