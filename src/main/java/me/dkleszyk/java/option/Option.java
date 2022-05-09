/*
 * The MIT License
 *
 * Copyright 2022 David Kleszyk <dkleszyk@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.dkleszyk.java.option;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNull;

/**
 * A value that may or may not be present.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 *
 * @param <T> The value type of the option.
 */
public interface Option<T>
{
    // Design note: type hierarchy
    //
    // I considered a design similar to BaseStream<T, S>, but decided against it
    //
    //         ==Implemented==                      ==Rejected==
    //
    //            Option<T>                      BaseOption<T, T_OPT>
    //              /   \                         /       |       \
    //             /     \                       /        |        \
    //        IntOption  ...                IntOption    ...     Option<T>
    //
    // The reason being: when passing a boxed primitive to Option.some, I wanted
    // to be able to return the specialized implementation for the underlying
    // primitive. Having IntOption and Option<T> as disjoint types prevents this
    //
    // e.g.
    // Number x = 5;
    // Object o = Option.some(x);
    // assert o instanceof IntOption;
    //
    // Now, you could make Option.<T>some(T) return a BaseOption<T, ?>, but that
    // would be a very poor (imo) tradeoff. The API gets significantly more
    // complicated and the only gain is some minor deduplication in declarations
    //
    // Another option would be a three-level hierarchy where
    // Option<T> -> PrimitiveOption<T, T_OPT> -> IntOption
    // But again, I don't think that you get anywhere near enough value to
    // offset the increase in complexity

    /**
     * Converts the specified {@link java.util.Optional} instance to an
     * {@link Option} instance.
     *
     * @param <T>      The value type of the option.
     * @param optional The instance to convert.
     *
     * @return The converted instance.
     */
    static <T> Option<T> from(
        final Optional<T> optional)
    {
        return optional.isEmpty() ?
            noneUnchecked() :
            someNonNull(optional.get());
    }

    /**
     * Converts the specified {@link java.util.OptionalDouble} instance to a
     * {@link DoubleOption} instance.
     *
     * @param optional The instance to convert.
     *
     * @return The converted instance.
     */
    static DoubleOption from(
        final OptionalDouble optional)
    {
        return optional.isEmpty() ?
            noneUnchecked() :
            someNonNull(optional.getAsDouble());
    }

    /**
     * Converts the specified {@link java.util.OptionalInt} instance to an
     * {@link IntOption} instance.
     *
     * @param optional The instance to convert.
     *
     * @return The converted instance.
     */
    static IntOption from(
        final OptionalInt optional)
    {
        return optional.isEmpty() ?
            noneUnchecked() :
            someNonNull(optional.getAsInt());
    }

    /**
     * Converts the specified {@link java.util.OptionalLong} instance to a
     * {@link LongOption} instance.
     *
     * @param optional The instance to convert.
     *
     * @return The converted instance.
     */
    static LongOption from(
        final OptionalLong optional)
    {
        return optional.isEmpty() ?
            noneUnchecked() :
            someNonNull(optional.getAsLong());
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Object)} if {@code value} is not {@code null}.
     *
     * @param <T>   The value type of the option.
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static <T> Option<T> maybe(
        final T value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Boolean)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static BooleanOption maybe(
        final Boolean value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Byte)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static ByteOption maybe(
        final Byte value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Character)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static CharOption maybe(
        final Character value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Double)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static DoubleOption maybe(
        final Double value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Float)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static FloatOption maybe(
        final Float value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Integer)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static IntOption maybe(
        final Integer value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Long)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static LongOption maybe(
        final Long value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns {@link #none()} if {@code value} is {@code null}, or
     * {@link #some(Short)} if {@code value} is not {@code null}.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static ShortOption maybe(
        final Short value)
    {
        return value == null ?
            noneUnchecked() :
            someNonNull(value);
    }

    /**
     * Returns an option that does not contain a value.
     *
     * @param <T> The option type.
     *
     * @return The empty option.
     */
    static <T> Option<T> none()
    {
        return noneUnchecked();
    }

    /**
     * Returns an option that contains the specified value.
     *
     * @param <T>   The value type of the option.
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static <T> Option<T> some(
        final T value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code boolean} that
     * contains the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static BooleanOption some(
        final Boolean value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code byte} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static ByteOption some(
        final Byte value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code char} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static CharOption some(
        final Character value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code double} that
     * contains the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static DoubleOption some(
        final Double value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code float} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static FloatOption some(
        final Float value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code int} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static IntOption some(
        final Integer value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code long} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static LongOption some(
        final Long value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code short} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     *
     * @throws NullPointerException {@code value} is {@code null}.
     */
    static ShortOption some(
        final Short value)
    {
        return someNonNull(Objects.requireNonNull(value));
    }

    /**
     * Returns an option specialized for a primitive {@code boolean} that
     * contains the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static BooleanOption some(
        final boolean value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code byte} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static ByteOption some(
        final byte value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code char} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static CharOption some(
        final char value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code double} that
     * contains the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static DoubleOption some(
        final double value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code float} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static FloatOption some(
        final float value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code int} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static IntOption some(
        final int value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code long} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static LongOption some(
        final long value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option specialized for a primitive {@code short} that contains
     * the specified value.
     *
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static ShortOption some(
        final short value)
    {
        return someNonNull(value);
    }

    /**
     * Returns an option that contains the specified possibly-{@code null}
     * value.
     * <p>
     * This method returns a non-empty option containing {@code null} if the
     * input parameter is {@code null}. Compare to {@link #maybe(Object)}, which
     * returns an empty option if its input parameter is {@code null}.
     *
     * @param <T>   The value type of the option.
     * @param value The value from which to create the option.
     *
     * @return The option created from {@code value}.
     */
    static <T> Option<T> someNullable(
        final T value)
    {
        return value == null ?
            someNull() :
            someNonNull(value);
    }

    /**
     * Returns the specified option if this instance is non-empty; otherwise
     * returns an empty option.
     *
     * @param <O>    The type of the returned option.
     * @param option The option to return if this instance is non-empty.
     *
     * @return {@code option} if this instance is non-empty; otherwise, an empty
     *         option.
     */
    <O extends Option<?>> O and(
        final O option);

    /**
     * Returns the option supplied by the specified method if this instance is
     * non-empty; otherwise returns an empty option.
     *
     * @param <O>      The type of the returned option.
     * @param supplier A method that supplies the option to return if this
     *                 instance is non-empty.
     *
     * @return The option supplied by {@code supplier} if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <O extends Option<?>> O andGet(
        final Supplier<? extends O> supplier);

    /**
     * Returns the option supplied by the specified method if this instance is
     * non-empty; otherwise returns an empty option.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param <O>      The type of the returned option.
     * @param supplier A method that supplies the option to return if this
     *                 instance is non-empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return The option supplied by {@code supplier} if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A, O extends Option<?>> O andGet(
        final Function<? super A, ? extends O> supplier,
        final A arg);

    /**
     * Returns this instance if it is non-empty and the specified predicate
     * returns {@code true} for its contained value; otherwise, returns an empty
     * option.
     *
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     *
     * @return This instance if it is non-empty and {@code predicate} returns
     *         {@code true} for its contained value; otherwise, an empty option.
     */
    Option<T> filter(
        final Predicate<? super T> predicate);

    /**
     * Returns this instance if it is non-empty and the specified predicate
     * returns {@code true} for its contained value; otherwise, returns an empty
     * option.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code predicate}.
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     * @param arg       An additional argument to provide to {@code predicate}.
     *
     * @return This instance if it is non-empty and {@code predicate} returns
     *         {@code true} for its contained value; otherwise, an empty option.
     */
    <A> Option<T> filter(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg);

    /**
     * Returns this instance if it is non-empty and contains a {@code boolean}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code boolean}
     *         value; otherwise, an empty option.
     */
    BooleanOption filterToBoolean();

    /**
     * Returns this instance if it is non-empty and contains a {@code byte}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code byte}
     *         value; otherwise, an empty option.
     */
    ByteOption filterToByte();

    /**
     * Returns this instance if it is non-empty and contains a {@code char}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code char}
     *         value; otherwise, an empty option.
     */
    CharOption filterToChar();

    /**
     * Returns this instance if it is non-empty and contains a {@code double}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code double}
     *         value; otherwise, an empty option.
     */
    DoubleOption filterToDouble();

    /**
     * Returns this instance if it is non-empty and contains a {@code float}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code float}
     *         value; otherwise, an empty option.
     */
    FloatOption filterToFloat();

    /**
     * Returns this instance if it is non-empty and contains an {@code int}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains an {@code int}
     *         value; otherwise, an empty option.
     */
    IntOption filterToInt();

    /**
     * Returns this instance if it is non-empty and contains a {@code long}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code long}
     *         value; otherwise, an empty option.
     */
    LongOption filterToLong();

    /**
     * Returns this instance it is non-empty and contains a non-{@code null}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a non-{@code null}
     *         value; otherwise, an empty option.
     */
    Option<T> filterToObject();

    /**
     * Returns this instance if it is non-empty and contains a {@code short}
     * value; otherwise, returns an empty option.
     *
     * @return This instance if it is non-empty and contains a {@code short}
     *         value; otherwise, an empty option.
     */
    ShortOption filterToShort();

    /**
     * Returns this instance if it is non-empty and contains a value of the
     * specified type; otherwise, returns an empty option.
     *
     * @param <U>  The value type of the returned option.
     * @param type A type to compare against the value contained by this
     *             instance if it is non-empty.
     *
     * @return This instance if it is non-empty and contains a value of type
     *         {@code type}; otherwise, an empty option.
     */
    <U> Option<U> filterToType(
        final Class<U> type);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <U>    The value type of the returned option.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <U> Option<U> flatMap(
        final Function<? super T, ? extends Option<? extends U>> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param <U>    The value type of the returned option.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A, U> Option<U> flatMap(
        final BiFunction<? super A, ? super T, ? extends Option<? extends U>> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    BooleanOption flatMapToBoolean(
        final Function<? super T, ? extends BooleanOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super T, ? extends BooleanOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    ByteOption flatMapToByte(
        final Function<? super T, ? extends ByteOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super T, ? extends ByteOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    CharOption flatMapToChar(
        final Function<? super T, ? extends CharOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super T, ? extends CharOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    DoubleOption flatMapToDouble(
        final Function<? super T, ? extends DoubleOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super T, ? extends DoubleOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    FloatOption flatMapToFloat(
        final Function<? super T, ? extends FloatOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super T, ? extends FloatOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    IntOption flatMapToInt(
        final Function<? super T, ? extends IntOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super T, ? extends IntOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    LongOption flatMapToLong(
        final Function<? super T, ? extends LongOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super T, ? extends LongOption> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    ShortOption flatMapToShort(
        final Function<? super T, ? extends ShortOption> mapper);

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super T, ? extends ShortOption> mapper,
        final A arg);

    /**
     * Returns the value contained by this instance.
     *
     * @return The value contained by this instance.
     *
     * @throws NoSuchElementException This instance is empty.
     */
    T get()
        throws NoSuchElementException;

    /**
     * Executes the specified method if this instance is empty.
     *
     * @param ifNone A method to execute if this instance is empty.
     */
    void ifNone(
        final Runnable ifNone);

    /**
     * Executes the specified method if this instance is empty.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code ifNone}.
     * @param ifNone A method to execute if this instance is empty.
     * @param arg    An additional argument to provide to {@code ifNone}.
     */
    <A> void ifNone(
        final Consumer<? super A> ifNone,
        final A arg);

    /**
     * Executes the specified method if this instance is non-empty.
     *
     * @param ifSome A method to execute if this instance is non-empty.
     */
    void ifSome(
        final Consumer<? super T> ifSome);

    /**
     * Executes the specified method if this instance is non-empty.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code ifSome}.
     * @param ifSome A method to execute if this instance is non-empty.
     * @param arg    An additional argument to provide to {@code ifSome}.
     */
    <A> void ifSome(
        final BiConsumer<? super A, ? super T> ifSome,
        final A arg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param ifSome A method to execute if this instance is non-empty.
     * @param ifNone A method to execute if this instance is empty.
     */
    void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Runnable ifNone);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifNone}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifNone    A method to execute if this instance is empty.
     * @param ifNoneArg An additional argument to provide to {@code ifNone}.
     */
    <A> void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifSome}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifSomeArg An additional argument to provide to {@code ifSome}.
     * @param ifNone    A method to execute if this instance is empty.
     */
    <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final A ifSomeArg,
        final Runnable ifNone);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code ifSome} and {@code ifNone}.
     * @param ifSome A method to execute if this instance is non-empty.
     * @param ifNone A method to execute if this instance is empty.
     * @param arg    An additional argument to provide to {@code ifSome} and
     *               {@code ifNone}.
     */
    <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A arg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifSome}.
     * @param <B>       The type of the additional argument provided to
     *                  {@code ifNone}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifSomeArg An additional argument to provide to {@code ifSome}.
     * @param ifNone    A method to execute if this instance is empty.
     * @param ifNoneArg An additional argument to provide to {@code ifNone}.
     */
    <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg);

    /**
     * Indicates whether this instance is empty.
     *
     * @return {@code true} if this instance is empty; otherwise, {@code false}.
     */
    boolean isNone();

    /**
     * Indicates whether this instance is non-empty.
     *
     * @return {@code true} if this instance is non-empty; otherwise,
     *         {@code false}.
     */
    boolean isSome();

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <U>    The value type of the returned option.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <U> Option<U> map(
        final Function<? super T, ? extends U> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param <U>    The value type of the returned option.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A, U> Option<U> map(
        final BiFunction<? super A, ? super T, ? extends U> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    BooleanOption mapToBoolean(
        final Predicate<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    ByteOption mapToByte(
        final ToByteFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    CharOption mapToChar(
        final ToCharFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    DoubleOption mapToDouble(
        final ToDoubleFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    FloatOption mapToFloat(
        final ToFloatFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    IntOption mapToInt(
        final ToIntFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    LongOption mapToLong(
        final ToLongFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    ShortOption mapToShort(
        final ToShortFunction<? super T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code mapper}.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     * @param arg    An additional argument to provide to {@code mapper}.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super T> mapper,
        final A arg);

    /**
     * Indicates whether this instance is non-empty and the specified predicate
     * returns {@code true} for its contained value.
     *
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     *
     * @return {@code true} if this instance is non-empty and {@code predicate}
     *         returns {@code true} for its contained value; otherwise,
     *         {@code false}.
     */
    boolean matches(
        final Predicate<? super T> predicate);

    /**
     * Indicates whether this instance is non-empty and the specified predicate
     * returns {@code true} for its contained value.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code predicate}.
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     * @param arg       An additional argument to provide to {@code predicate}.
     *
     * @return {@code true} if this instance is non-empty and {@code predicate}
     *         returns {@code true} for its contained value; otherwise,
     *         {@code false}.
     */
    <A> boolean matches(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg);

    /**
     * Returns this instance if it is non-empty; otherwise, returns the
     * specified option.
     *
     * @param option The option to return if this instance is empty.
     *
     * @return This instance if it is non-empty; otherwise, {@code option}.
     */
    Option<T> or(
        final Option<? extends T> option);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the specified value.
     *
     * @param value The value to return if this instance is empty.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, {@code value}.
     */
    T orElse(
        final T value);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the value supplier by the specified method.
     *
     * @param supplier A method that supplies the value to return if this
     *                 instance is empty.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, the value supplied by {@code supplier}.
     */
    T orElseGet(
        final Supplier<? extends T> supplier);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the value supplier by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param supplier A method that supplies the value to return if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, the value supplied by {@code supplier}.
     */
    <A> T orElseGet(
        final Function<? super A, ? extends T> supplier,
        final A arg);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws NoSuchElementException This instance is empty.
     */
    T orElseThrow()
        throws NoSuchElementException;

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception supplied by the specified method.
     *
     * @param <X>      The type of the exception thrown if this instance is
     *                 empty.
     * @param supplier A method that supplies the exception to throw if this
     *                 instance is empty.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws X This instance is empty.
     */
    <X extends Throwable> T orElseThrow(
        final Supplier<? extends X> supplier)
        throws X;

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception supplied by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param <X>      The type of the exception thrown if this instance is
     *                 empty.
     * @param supplier A method that supplies the exception to throw if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws X This instance is empty.
     */
    <A, X extends Throwable> T orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
        throws X;

    /**
     * Returns this instance if it is non-empty; otherwise, returns the option
     * supplied by the specified method.
     *
     * @param supplier A method that supplies the option to return if this
     *                 instance is empty.
     *
     * @return This instance if it is non-empty; otherwise, the option supplied
     *         by {@code supplier}.
     */
    Option<T> orGet(
        final Supplier<? extends Option<? extends T>> supplier);

    /**
     * Returns this instance if it is non-empty; otherwise, returns the option
     * supplied by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param supplier A method that supplies the option to return if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return This instance if it is non-empty; otherwise, the option supplied
     *         by {@code supplier}.
     */
    <A> Option<T> orGet(
        final Function<? super A, ? extends Option<? extends T>> supplier,
        final A arg);

    /**
     * Returns a stream that produces the value contained by this instance.
     *
     * @return A stream that produces the value contained by this instance.
     */
    Stream<T> stream();

    /**
     * Converts this instance to a {@link java.util.Optional} instance.
     *
     * @return The converted instance.
     */
    Optional<T> toOptional();
}
