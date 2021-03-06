/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection;

import javaslang.Tuple2;
import javaslang.control.Option;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interface for immutable, linear sequences.
 * <p>
 * Efficient {@code head()}, {@code tail()}, and {@code isEmpty()} methods are characteristic for linear sequences.
 *
 * @param <T> component type
 * @author Daniel Dietrich
 * @since 2.0.0
 */
public interface LinearSeq<T> extends Seq<T> {

    // -- Adjusted return types of Seq methods

    @Override
    LinearSeq<T> append(T element);

    @Override
    LinearSeq<T> appendAll(java.lang.Iterable<? extends T> elements);

    @Override
    LinearSeq<T> clear();

    @Override
    LinearSeq<Tuple2<T, T>> crossProduct();

    @Override
    LinearSeq<IndexedSeq<T>> crossProduct(int power);

    @Override
    <U> LinearSeq<Tuple2<T, U>> crossProduct(java.lang.Iterable<? extends U> that);

    @Override
    LinearSeq<? extends LinearSeq<T>> combinations();

    @Override
    LinearSeq<? extends LinearSeq<T>> combinations(int k);

    @Override
    LinearSeq<T> distinct();

    @Override
    LinearSeq<T> distinctBy(Comparator<? super T> comparator);

    @Override
    <U> LinearSeq<T> distinctBy(Function<? super T, ? extends U> keyExtractor);

    @Override
    LinearSeq<T> drop(int n);

    @Override
    LinearSeq<T> dropRight(int n);

    @Override
    LinearSeq<T> dropWhile(Predicate<? super T> predicate);

    @Override
    LinearSeq<T> filter(Predicate<? super T> predicate);

    @Override
    <U> LinearSeq<U> flatMap(Function<? super T, ? extends java.lang.Iterable<? extends U>> mapper);

    @Override
    <U> LinearSeq<U> flatten();

    @Override
    <C> Map<C, ? extends LinearSeq<T>> groupBy(Function<? super T, ? extends C> classifier);

    @Override
    default int indexWhere(Predicate<? super T> predicate, int from) {
        Objects.requireNonNull(predicate, "predicate is null");
        int i = from;
        LinearSeq<T> these = drop(from);
        while (!these.isEmpty()) {
            if (predicate.test(these.head())) {
                return i;
            }
            i++;
            these = these.tail();
        }
        return -1;
    }

    ;

    @Override
    LinearSeq<T> init();

    @Override
    Option<? extends LinearSeq<T>> initOption();

    @Override
    LinearSeq<T> insert(int index, T element);

    @Override
    LinearSeq<T> insertAll(int index, java.lang.Iterable<? extends T> elements);

    @Override
    LinearSeq<T> intersperse(T element);

    @Override
    default int lastIndexWhere(Predicate<? super T> predicate, int end) {
        Objects.requireNonNull(predicate, "predicate is null");
        int i = 0;
        LinearSeq<T> these = this;
        int last = -1;
        while (!these.isEmpty() && i <= end) {
            if (predicate.test(these.head())) {
                last = i;
            }
            these = these.tail();
            i++;
        }
        return last;
    }

    @Override
    <U> LinearSeq<U> map(Function<? super T, ? extends U> mapper);

    @Override
    LinearSeq<T> padTo(int length, T element);

    @Override
    LinearSeq<T> patch(int from, java.lang.Iterable<? extends T> that, int replaced);

    @Override
    Tuple2<? extends LinearSeq<T>, ? extends LinearSeq<T>> partition(Predicate<? super T> predicate);

    @Override
    LinearSeq<T> peek(Consumer<? super T> action);

    @Override
    LinearSeq<? extends LinearSeq<T>> permutations();

    @Override
    LinearSeq<T> prepend(T element);

    @Override
    LinearSeq<T> prependAll(java.lang.Iterable<? extends T> elements);

    @Override
    LinearSeq<T> remove(T element);

    @Override
    LinearSeq<T> removeFirst(Predicate<T> predicate);

    @Override
    LinearSeq<T> removeLast(Predicate<T> predicate);

    @Override
    LinearSeq<T> removeAt(int index);

    @Override
    LinearSeq<T> removeAll(T element);

    @Override
    LinearSeq<T> removeAll(java.lang.Iterable<? extends T> elements);

    @Override
    LinearSeq<T> replace(T currentElement, T newElement);

    @Override
    LinearSeq<T> replaceAll(T currentElement, T newElement);

    @Override
    LinearSeq<T> retainAll(java.lang.Iterable<? extends T> elements);

    @Override
    LinearSeq<T> reverse();

    @Override
    default Iterator<T> reverseIterator() {
        return reverse().iterator();
    }

    @Override
    LinearSeq<T> scan(T zero, BiFunction<? super T, ? super T, ? extends T> operation);

    @Override
    <U> LinearSeq<U> scanLeft(U zero, BiFunction<? super U, ? super T, ? extends U> operation);

    @Override
    <U> LinearSeq<U> scanRight(U zero, BiFunction<? super T, ? super U, ? extends U> operation);

    @Override
    default int segmentLength(Predicate<? super T> predicate, int from) {
        Objects.requireNonNull(predicate, "predicate is null");
        int i = 0;
        LinearSeq<T> these = this.drop(from);
        while (!these.isEmpty() && predicate.test(these.head())) {
            i++;
            these = these.tail();
        }
        return i;
    }

    @Override
    LinearSeq<T> slice(int beginIndex, int endIndex);

    @Override
    LinearSeq<T> sort();

    @Override
    LinearSeq<T> sort(Comparator<? super T> comparator);

    @Override
    <U extends Comparable<? super U>> LinearSeq<T> sortBy(Function<? super T, ? extends U> mapper);

    @Override
    <U> LinearSeq<T> sortBy(Comparator<? super U> comparator, Function<? super T, ? extends U> mapper);

    @Override
    Tuple2<? extends LinearSeq<T>, ? extends LinearSeq<T>> span(Predicate<? super T> predicate);

    @Override
    LinearSeq<T> subSequence(int beginIndex);

    @Override
    LinearSeq<T> subSequence(int beginIndex, int endIndex);

    @Override
    LinearSeq<T> tail();

    @Override
    Option<? extends LinearSeq<T>> tailOption();

    @Override
    LinearSeq<T> take(int n);

    @Override
    LinearSeq<T> takeRight(int n);

    @Override
    LinearSeq<T> takeUntil(Predicate<? super T> predicate);

    @Override
    LinearSeq<T> takeWhile(Predicate<? super T> predicate);

    @Override
    <U> LinearSeq<U> unit(java.lang.Iterable<? extends U> iterable);

    @Override
    <T1, T2> Tuple2<? extends LinearSeq<T1>, ? extends LinearSeq<T2>> unzip(Function<? super T, Tuple2<? extends T1, ? extends T2>> unzipper);

    @Override
    LinearSeq<T> update(int index, T element);

    @Override
    <U> LinearSeq<Tuple2<T, U>> zip(java.lang.Iterable<U> that);

    @Override
    <U> LinearSeq<Tuple2<T, U>> zipAll(java.lang.Iterable<U> that, T thisElem, U thatElem);

    @Override
    LinearSeq<Tuple2<T, Integer>> zipWithIndex();

}
