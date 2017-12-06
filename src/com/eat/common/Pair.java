package com.eat.common;

import java.util.Objects;

/**
 * An immutable pair of values.  The values may be null.  The values
 * themselves may be mutable.
 *
 * @param <A> the type of the first element of the pair
 * @param <B> the type of the second element of the pair
 *
 * @since 1.7
 */
public final class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair() {
        first = null;
        second = null;
    }

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    /**
     * Returns a pair whose elements are the first and second
     * arguments, respectively.
     *
     * @return a pair constructed from the arguments
     */
    public static <C, D> Pair<C, D> valueOf(C c, D d) {
        // Don't mandate new values.
        return new Pair<C, D>(c, d);
    }

    /**
     * TBD
     */
    @Override
    public String toString() {
        return "[" + Objects.toString(first) + ", " + Objects.toString(second) + "]";
    }

    /**
     * TBD
     */
    @Override
    public boolean equals(Object x) {
        if (!(x instanceof Pair))
            return false;
        else {
            Pair<?, ?> that = (Pair<?, ?>) x;
            return Objects.equals(this.first, that.first) && Objects.equals(this.second, that.second);
        }
    }

    /**
     * TBD
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
