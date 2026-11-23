
package waypoint.model;

import java.util.Objects;

public record Registration(String value) {

    // Invariant: registration must always be valid
    public Registration {
        Objects.requireNonNull(value, "Registration cannot be null");

        value = value.trim().toUpperCase();

        // Precondition: must match required format
        if (!value.matches("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}"))
            throw new IllegalArgumentException("Invalid registration");

        // Postcondition: value is normalised and valid
    }
}
