package ca.mcmaster.cas.se2aa4.a2.mesh.adt.segment;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class Segment {

    private Structs.Segment segment;

    public Segment() {
        this.segment = Structs.Segment.newBuilder().build();
    }

    /**
     *
     * @param v1Idx Index of the first {@link Structs.Vertex} of the {@link Segment}
     * @param v2Idx Index of the second {@link Structs.Vertex} of the {@link Segment}
     */
    public Segment(int v1Idx, int v2Idx) {
        this.segment = Structs.Segment.newBuilder().setV1Idx(v1Idx).setV2Idx(v2Idx).build();
    }

    /**
     *
     * @param segment Create a {@link Segment} from a {@link Structs.Segment}
     */
    public Segment(Structs.Segment segment) {
        this.segment = segment;
    }

}
