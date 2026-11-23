
package waypoint.model;

import java.time.Instant;

@FunctionalInterface
public interface FarePolicy {

    // Calculates fare using entry, exit and spot
    Money fareFor(Instant entry, Instant exit, SpotId spot);
}
