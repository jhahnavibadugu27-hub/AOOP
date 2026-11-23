
package waypoint.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Arrays;

class TicketTest {

    static final Instant T10 = Instant.parse("2026-03-01T10:00:00Z");
    static final Instant T09 = Instant.parse("2026-03-01T09:00:00Z");
    static final Instant T12 = Instant.parse("2026-03-01T12:00:00Z");

    // Flat fare policy
    static final FarePolicy FLAT = (e, x, s) -> Money.rupees(40);

    @Test
    void exitBeforeEntryIsImpossible() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);

        assertThrows(IllegalArgumentException.class,
                () -> t.close(T09, FLAT));

        // Rejected close must leave ticket open
        assertTrue(t.isOpen());
    }

    @Test
    void closingTwiceIsRejected() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);

        t.close(T12, FLAT);

        // Second close must throw exception
        assertThrows(TicketAlreadyClosed.class,
                () -> t.close(T12, FLAT));
    }

    @Test
    void fareAndExitAppearTogether() {
        Ticket t = Ticket.open(new TicketId("T1"), new SpotId("S1"), T10);

        assertNull(t.fare());

        assertEquals(Money.rupees(40), t.close(T12, FLAT));

        assertFalse(t.isOpen());
        assertEquals(Money.rupees(40), t.fare());
    }

    @Test
    void classExposesNoSetters() {
        // No public setter methods allowed
        assertTrue(Arrays.stream(Ticket.class.getMethods())
                .noneMatch(m -> m.getName().startsWith("set")));
    }
}
