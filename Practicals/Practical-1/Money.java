
package waypoint.model;
import java.util.*;

public final class Money implements Comparable<Money> {
    private final long paise;
    private final Currency currency;

    // Constructor with invariant: currency cannot be null
    private Money(long p, Currency c) {
        if (c == null) throw new IllegalArgumentException();
        paise = p;
        currency = c;
    }

    // Factory methods
    public static Money ofMinor(long p, Currency c) {
        return new Money(p, c);
    }

    public static Money rupees(long r) {
        return new Money(Math.multiplyExact(r, 100),
                Currency.getInstance("INR"));
    }

    // Precondition: currencies must match
    private void valid(Money m) {
        if (m == null || !currency.equals(m.currency))
            throw new IllegalArgumentException("Currency mismatch");
    }

    // Postcondition: returns a new Money object
    public Money plus(Money m) {
        valid(m);
        return new Money(Math.addExact(paise, m.paise), currency);
    }

    public Money minus(Money m) {
        valid(m);
        return new Money(Math.subtractExact(paise, m.paise), currency);
    }

    public Money times(int n) {
        return new Money(Math.multiplyExact(paise, n), currency);
    }

    // Condition: checks negative amount
    public boolean isNegative() {
        return paise < 0;
    }

    public int compareTo(Money m) {
        valid(m);
        return Long.compare(paise, m.paise);
    }

    // Precondition: n must be positive
    // Postcondition: all shares sum to original amount
    public List<Money> allocate(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        List<Money> a = new ArrayList<>();
        long q = paise / n, r = paise % n;

        for (int i = 0; i < n; i++)
            a.add(new Money(q + (i < Math.abs(r)
                    ? Long.signum(paise) : 0), currency));

        return Collections.unmodifiableList(a);
    }

    // Equality and hash code
    public boolean equals(Object o) {
        if (!(o instanceof Money m)) return false;
        return paise == m.paise && currency.equals(m.currency);
    }

    public int hashCode() {
        return Objects.hash(paise, currency);
    }

    public String toString() {
        return currency.getCurrencyCode() + " " +
                paise / 100 + "." +
                String.format("%02d", Math.abs(paise % 100));
    }
}
