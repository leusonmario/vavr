/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collector;

public class ArrayTest extends AbstractSeqTest {

    @Override
    protected <T> Collector<T, ArrayList<T>, ? extends Seq<T>> collector() {
        return Array.collector();
    }

    @Override
    protected <T> Array<T> empty() {
        return Array.empty();
    }

    @Override
    protected <T> Array<T> of(T element) {
        return Array.of(element);
    }

    @SuppressWarnings("varargs")
    @SafeVarargs
    @Override
    protected final <T> Array<T> ofAll(T... elements) {
        return Array.ofAll(elements);
    }

    @Override
    protected <T> Array<T> ofAll(Iterable<? extends T> elements) {
        return Array.ofAll(elements);
    }

    @Override
    protected Array<Boolean> ofAll(boolean[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Byte> ofAll(byte[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Character> ofAll(char[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Double> ofAll(double[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Float> ofAll(float[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Integer> ofAll(int[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Long> ofAll(long[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Short> ofAll(short[] array) {
        return Array.ofAll(array);
    }

    @Override
    protected Array<Character> range(char from, char toExclusive) {
        return Array.range(from, toExclusive);
    }

    @Override
    protected Array<Character> rangeBy(char from, char toExclusive, int step) {
        return Array.rangeBy(from, toExclusive, step);
    }

    @Override
    protected Array<Double> rangeBy(double from, double toExclusive, double step) {
        return Array.rangeBy(from, toExclusive, step);
    }

    @Override
    protected Array<Integer> range(int from, int toExclusive) {
        return Array.range(from, toExclusive);
    }

    @Override
    protected Array<Integer> rangeBy(int from, int toExclusive, int step) {
        return Array.rangeBy(from, toExclusive, step);
    }

    @Override
    protected Array<Long> range(long from, long toExclusive) {
        return Array.range(from, toExclusive);
    }

    @Override
    protected Array<Long> rangeBy(long from, long toExclusive, long step) {
        return Array.rangeBy(from, toExclusive, step);
    }

    @Override
    protected Array<Character> rangeClosed(char from, char toInclusive) {
        return Array.rangeClosed(from, toInclusive);
    }

    @Override
    protected Array<Character> rangeClosedBy(char from, char toInclusive, int step) {
        return Array.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected Array<Double> rangeClosedBy(double from, double toInclusive, double step) {
        return Array.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected Array<Integer> rangeClosed(int from, int toInclusive) {
        return Array.rangeClosed(from, toInclusive);
    }

    @Override
    protected Array<Integer> rangeClosedBy(int from, int toInclusive, int step) {
        return Array.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected Array<Long> rangeClosed(long from, long toInclusive) {
        return Array.rangeClosed(from, toInclusive);
    }

    @Override
    protected Array<Long> rangeClosedBy(long from, long toInclusive, long step) {
        return Array.rangeClosedBy(from, toInclusive, step);
    }

    @Override
    protected int getPeekNonNilPerformingAnAction() {
        return 1;
    }

    @Override
    protected boolean useIsEqualToInsteadOfIsSameAs() {
        return false;
    }

    // -- toString

    @Test
    public void shouldStringifyNil() {
        assertThat(empty().toString()).isEqualTo("Array()");
    }

    @Test
    public void shouldStringifyNonNil() {
        assertThat(ofAll(1, 2, 3).toString()).isEqualTo("Array(1, 2, 3)");
    }

}
