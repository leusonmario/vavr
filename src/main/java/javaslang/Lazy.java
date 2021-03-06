/*     / \____  _    _  ____   ______  / \ ____  __    _ _____
 *    /  /    \/ \  / \/    \ /  /\__\/  //    \/  \  / /  _  \   Javaslang
 *  _/  /  /\  \  \/  /  /\  \\__\\  \  //  /\  \ /\\/  \__/  /   Copyright 2014-now Daniel Dietrich
 * /___/\_/  \_/\____/\_/  \_/\__\/__/___\_/  \_//  \__/_____/    Licensed under the Apache License, Version 2.0
 */
package javaslang;

import javaslang.collection.Iterator;
import javaslang.control.None;
import javaslang.control.Option;
import javaslang.control.Some;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a lazy evaluated value. Compared to a Supplier, Lazy is memoizing, i.e. it evaluates only once and
 * therefore is referential transparent.
 *
 * <pre>
 * <code>
 * final Lazy&lt;Double&gt; l = Lazy.of(Math::random);
 * l.isDefined(); // = false
 * l.get();       // = 0.123 (random generated)
 * l.isDefined(); // = true
 * l.get();       // = 0.123 (memoized)
 * </code>
 * </pre>
 *
 * Since 2.0.0 you may also create a <em>real</em> lazy value (works only with interfaces):
 *
 * <pre><code>final CharSequence chars = Lazy.of(() -&gt; "Yay!", CharSequence.class);</code></pre>
 *
 * @author Daniel Dietrich
 * @since 1.2.1
 */
public final class Lazy<T> implements Supplier<T>, Value<T>, Serializable {

    private static final long serialVersionUID = 1L;

    // read http://javarevisited.blogspot.de/2014/05/double-checked-locking-on-singleton-in-java.html
    private transient volatile Supplier<? extends T> supplier;
    private volatile T value = null;

    private final boolean isEmpty;

    private Lazy(Supplier<? extends T> supplier, boolean isEmpty) {
        this.supplier = supplier;
        this.isEmpty = isEmpty;
    }

    /**
     * Returns a Lazy instance that will throw a {@code NoSuchElementException} on {@code get()} and {@code isEmpty()}
     * returns {@code true}.
     *
     * @param <T> Component type
     * @return An undefined lazy value.
     */
    public static <T> Lazy<T> empty() {
        return new Lazy<>(() -> {
            throw new NoSuchElementException("get() on empty lazy");
        }, true);
    }

    /**
     * Creates a {@code Lazy} that requests its value from a given {@code Supplier}. The supplier is asked only once,
     * the value is memoized. Initially the {@code Lazy} is marked as not evaluated.
     *
     * @param <T>      type of the lazy value
     * @param supplier A supplier
     * @return A new instance of Lazy
     */
    @SuppressWarnings("unchecked")
    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier is null");
        if (supplier instanceof Lazy) {
            return (Lazy<T>) supplier;
        } else {
            return new Lazy<>(supplier, false);
        }
    }

    /**
     * Creates a real _lazy value_ of type {@code T}, backed by a {@linkplain java.lang.reflect.Proxy} which delegates
     * to a {@code Lazy} instance.
     *
     * @param supplier A supplier
     * @param type     An interface
     * @param <T>      type of the lazy value
     * @return A new instance of T
     */
    @SuppressWarnings("unchecked")
    public static <T> T val(Supplier<? extends T> supplier, Class<T> type) {
        Objects.requireNonNull(supplier, "supplier is null");
        Objects.requireNonNull(type, "type is null");
        if (!type.isInterface()) {
            throw new IllegalArgumentException("type has to be an interface");
        }
        final Lazy<T> lazy = Lazy.of(supplier);
        final InvocationHandler handler = (proxy, method, args) -> method.invoke(lazy.get(), args);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, handler);
    }

    /**
     * Evaluates this lazy value and caches it, when called the first time.
     * On subsequent calls, returns the cached value.
     *
     * @return the lazy evaluated value
     */
    @Override
    public T get() {
        if (!isEvaluated()) {
            synchronized (this) {
                if (!isEvaluated()) {
                    value = supplier.get();
                    supplier = null; // free mem
                }
            }
        }
        return value;
    }

    /**
     * Returns {@code false} because a present value cannot be empty, even if it is lazy.
     * <p>
     * This implies that {@link #get()} will always succeed, i.e.{@link #orElse(Object)} et.al. will not return an
     * alternate value.
     *
     * @return false
     */
    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * A lazy value is a singleton type.
     *
     * @return {@code true}
     */
    @Override
    public boolean isSingletonType() {
        return true;
    }

    /**
     * Checks, if this lazy value is evaluated.
     * <p>
     * Note: A value is internally evaluated (once) by calling {@link #get()}.
     *
     * @return true, if the value is evaluated, false otherwise.
     */
    public boolean isEvaluated() {
        return supplier == null;
    }

    /**
     * Filters this value. If the filter result is empty, {@code None} is returned, otherwise Some of this lazy value
     * is returned.
     *
     * @param predicate A predicate
     * @return A new Option instance
     */
    @Override
    public Option<T> filter(Predicate<? super T> predicate) {
        final T value = get();
        if (predicate.test(value)) {
            return new Some<>(value);
        } else {
            return None.instance();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Lazy<U> flatMap(Function<? super T, ? extends java.lang.Iterable<? extends U>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        if (isEmpty()) {
            return (Lazy<U>) this;
        } else {
            return Lazy.of(() -> Value.get(mapper.apply(get())));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> Lazy<U> flatten() {
        try {
            return ((Lazy<? extends java.lang.Iterable<U>>) this).flatMap(Function.identity());
        } catch(ClassCastException x) {
            throw new UnsupportedOperationException("flatten of non-iterable elements");
        }
    }

    @Override
    public <U> Lazy<U> map(Function<? super T, ? extends U> mapper) {
        return Lazy.of(() -> mapper.apply(get()));
    }

    @Override
    public Iterator<T> iterator() {
        return Iterator.of(get());
    }

    @Override
    public Lazy<T> peek(Consumer<? super T> action) {
        action.accept(get());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return (o == this) || (o instanceof Lazy && Objects.equals(((Lazy<?>) o).get(), get()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(get());
    }

    @Override
    public String toString() {
        return String.format("Lazy(%s)", !isEvaluated() ? "?" : value);
    }

    /**
     * Ensures that the value is evaluated before serialization.
     *
     * @param s An object serialization stream.
     * @throws java.io.IOException If an error occurs writing to the stream.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        get(); // evaluates the lazy value if it isn't evaluated yet!
        s.defaultWriteObject();
    }
}
