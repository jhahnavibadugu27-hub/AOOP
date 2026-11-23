
package waypoint.model;

import java.time.Instant;
import java.util.Objects;

public final class Ticket {
    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;
    private Instant exit;       // Mutable field 1
    private Money fare;         // Mutable field 2

    // Constructor: validate required values
    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id);
        this.spotId = Objects.requireNonNull(spotId);
        this.entry = Objects.requireNonNull(entry);
    }

    // Factory method
    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    // Check whether ticket is open
    public boolean isOpen() {
        return exit == null;
    }

    // Close ticket and calculate fare
    public Money close(Instant at, FarePolicy policy) {
        if (!isOpen())
            throw new TicketAlreadyClosed(); // Already closed

        Objects.requireNonNull(at);
        Objects.requireNonNull(policy);

        if (at.isBefore(entry))
            throw new IllegalArgumentException(); // Invalid exit

        Money amount = Objects.requireNonNull(
            policy.fareFor(entry, at, spotId)); // Correct method

        if (amount.isNegative())
            throw new IllegalArgumentException(); // Negative fare

        exit = at;
        fare = amount;
        return fare;
    }

    // Getter: returns current fare
    public Money fare() {
        return fare;
    }
}
