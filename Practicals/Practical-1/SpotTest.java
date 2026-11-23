
package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SpotTest {

    @Test
    void aSpotCannotBeAssignedTwice() {
        Spot s = new Spot(new SpotId("S1"));

        assertTrue(s.tryAssign(new VehicleId("V1")));

        // Condition: second assignment must fail
        assertFalse(s.tryAssign(new VehicleId("V2")),
                "Second claim must fail, not overwrite");
    }

    @Test
    void releaseIsIdempotent() {
        Spot s = new Spot(new SpotId("S1"));

        s.release(new VehicleId("V1"));

        // Condition: free spot remains free
        assertTrue(s.isFree());
    }

    @Test
    void spotIdentityIsItsIdNotItsState() {
        Spot a = new Spot(new SpotId("S1"));
        Spot b = new Spot(new SpotId("S1"));

        a.tryAssign(new VehicleId("V1"));

        // Condition: identity remains the same after state changes
        assertEquals(a, b);
    }
}
