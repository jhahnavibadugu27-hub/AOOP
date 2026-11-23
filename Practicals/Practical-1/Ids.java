
package waypoint.model;

import java.util.Objects;

public record TicketId(String value) {
    public TicketId {
        Objects.requireNonNull(value, "ID cannot be null");

        // Precondition: ID must not be blank
        if (value.isBlank())
            throw new IllegalArgumentException("Invalid Ticket ID");

        value = value.trim();
    }
}
