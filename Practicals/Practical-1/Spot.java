
package waypoint.model;

import java.util.Objects;

public final class Spot {
    private final SpotId id;
    private VehicleId occupant;

    // Constructor: validate ID
    public Spot(SpotId id) {
        this.id = Objects.requireNonNull(id);
    }

    // Assign only if spot is free
    public boolean tryAssign(VehicleId v) {
        if (v == null || occupant != null)
            return false;

        occupant = v;
        return true;
    }

    // Release only matching vehicle
    public void release(VehicleId v) {
        if (Objects.equals(occupant, v))
            occupant = null;
    }

    // Check availability
    public boolean isFree() {
        return occupant == null;
    }

    // Entity identity depends only on ID
    public boolean equals(Object o) {
        if (!(o instanceof Spot s)) return false;
        return id.equals(s.id);
    }

    public int hashCode() {
        return id.hashCode();
    }
}
