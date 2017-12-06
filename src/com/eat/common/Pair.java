package com.eat.common;

import java.util.Objects;

/**
 * An immutable pair of values.  The values may be null.  The values themselves may be mutable.
 *
 * @param <A> the type of the first element of the pair
 * @param <B> the type of the second element of the pair
 *
 * @author  sylar
 * @version 0.0.1
 * @date    2017-12-07
 */

public final class Pair<A, B> {
    public final A first;
    public final B second;

    /**
     *
     *
     */
    public Pair() {
        first = null;
        second = null;
    }

    /**
     *
     *
     * @param a
     * @param b
     */
    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    /**
     * Returns a pair whose elements are the first and second
     *
     * @param c
     * @param d
     * @param <C>
     * @param <D>
     * @return a pair constructed from the arguments
     */
    public static <C, D> Pair<C, D> valueOf(C c, D d) {
        // Don't mandate new values.
        return new Pair<C, D>(c, d);
    }

    /**
     * to string
     *
     * @return
     */
    @Override
    public String toString() {
        return "[" + Objects.toString(first) + ", " + Objects.toString(second) + "]";
    }


    /**
     *
     *
     * @param x
     * @return
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
     *
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    public static void main(String[] args) {

    }
}
