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

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNullable;

/**
 * A {@link DoubleOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeDouble
    implements DoubleOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Double boxedValue;

    private final double value;

    private SomeDouble(
        final double value)
    {
        this(value, null);
    }

    private SomeDouble(
        final double value,
        final Double boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeDouble of(
        final double value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeDouble(value);
    }

    public static SomeDouble of(
        final Double value)
    {
        final double d = (double) value;

        if (Cache.isCached(d))
        {
            return Cache.get(d);
        }

        return new SomeDouble(d, value);
    }

    @Override
    public <O extends Option<?>> O and(
        final O option)
    {
        return option;
    }

    @Override
    public <O extends Option<?>> O andGet(
        final Supplier<? extends O> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A, O extends Option<?>> O andGet(
        final Function<? super A, ? extends O> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public double doubleOrElse(
        final double value)
    {
        return this.value;
    }

    @Override
    public double doubleOrElseGet(
        final DoubleSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> double doubleOrElseGet(
        final ToDoubleFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public double doubleOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> double doubleOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> double doubleOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public DoubleStream doubleStream()
    {
        return DoubleStream.of(value);
    }

    @Override
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeDouble o &&
            Double.doubleToLongBits(value) == Double.doubleToLongBits(o.value);
    }

    @Override
    public DoubleOption filter(
        final Predicate<? super Double> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> DoubleOption filter(
        final BiPredicate<? super A, ? super Double> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public DoubleOption filterDouble(
        final DoublePredicate predicate)
    {
        return predicate.test(value) ?
            this :
            noneUnchecked();
    }

    @Override
    public BooleanOption filterToBoolean()
    {
        return noneUnchecked();
    }

    @Override
    public ByteOption filterToByte()
    {
        return noneUnchecked();
    }

    @Override
    public CharOption filterToChar()
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption filterToDouble()
    {
        return this;
    }

    @Override
    public FloatOption filterToFloat()
    {
        return noneUnchecked();
    }

    @Override
    public IntOption filterToInt()
    {
        return noneUnchecked();
    }

    @Override
    public LongOption filterToLong()
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption filterToObject()
    {
        return this;
    }

    @Override
    public ShortOption filterToShort()
    {
        return noneUnchecked();
    }

    @Override
    public <T> Option<T> filterToType(
        final Class<T> type)
    {
        if (type == double.class || type.isAssignableFrom(Double.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <T> Option<T> flatMap(
        final Function<? super Double, ? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(boxedValue());
        return o;
    }

    @Override
    public <A, T> Option<T> flatMap(
        final BiFunction<? super A, ? super Double, ? extends Option<? extends T>> mapper,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(arg, boxedValue());
        return o;
    }

    @Override
    public <T> Option<T> flatMapDouble(
        final DoubleFunction<? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(value);
        return o;
    }

    @Override
    public BooleanOption flatMapDoubleToBoolean(
        final DoubleFunction<? extends BooleanOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ByteOption flatMapDoubleToByte(
        final DoubleFunction<? extends ByteOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public CharOption flatMapDoubleToChar(
        final DoubleFunction<? extends CharOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public DoubleOption flatMapDoubleToDouble(
        final DoubleFunction<? extends DoubleOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public FloatOption flatMapDoubleToFloat(
        final DoubleFunction<? extends FloatOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public IntOption flatMapDoubleToInt(
        final DoubleFunction<? extends IntOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public LongOption flatMapDoubleToLong(
        final DoubleFunction<? extends LongOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ShortOption flatMapDoubleToShort(
        final DoubleFunction<? extends ShortOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public BooleanOption flatMapToBoolean(
        final Function<? super Double, ? extends BooleanOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super Double, ? extends BooleanOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public ByteOption flatMapToByte(
        final Function<? super Double, ? extends ByteOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super Double, ? extends ByteOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public CharOption flatMapToChar(
        final Function<? super Double, ? extends CharOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super Double, ? extends CharOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public DoubleOption flatMapToDouble(
        final Function<? super Double, ? extends DoubleOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super Double, ? extends DoubleOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public FloatOption flatMapToFloat(
        final Function<? super Double, ? extends FloatOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super Double, ? extends FloatOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public IntOption flatMapToInt(
        final Function<? super Double, ? extends IntOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super Double, ? extends IntOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public LongOption flatMapToLong(
        final Function<? super Double, ? extends LongOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super Double, ? extends LongOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public ShortOption flatMapToShort(
        final Function<? super Double, ? extends ShortOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super Double, ? extends ShortOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public Double get()
    {
        return boxedValue();
    }

    @Override
    public double getAsDouble()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Double.hashCode(value);
    }

    @Override
    public void ifNone(
        final Runnable ifNone)
    {
    }

    @Override
    public <A> void ifNone(
        final Consumer<? super A> ifNone,
        final A arg)
    {
    }

    @Override
    public void ifSome(
        final Consumer<? super Double> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Double> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeDouble(
        final DoubleConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeDouble(
        final ObjDoubleConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeDoubleOrElse(
        final DoubleConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeDouble(ifSome);
    }

    @Override
    public <A> void ifSomeDoubleOrElse(
        final DoubleConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeDouble(ifSome);
    }

    @Override
    public <A> void ifSomeDoubleOrElse(
        final ObjDoubleConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeDouble(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeDoubleOrElse(
        final ObjDoubleConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeDouble(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeDoubleOrElse(
        final ObjDoubleConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeDouble(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Double> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Double> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Double> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Double> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Double> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public boolean isNone()
    {
        return false;
    }

    @Override
    public boolean isSome()
    {
        return true;
    }

    @Override
    public <T> Option<T> map(
        final Function<? super Double, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Double, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapDouble(
        final DoubleFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapDoubleToBoolean(
        final DoublePredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapDoubleToByte(
        final DoubleToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapDoubleToChar(
        final DoubleToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapDoubleToDouble(
        final DoubleUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public DoubleOption mapDoubleToDouble(
        final DoubleBinaryOperator mapper,
        final double arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, value));
    }

    @Override
    public FloatOption mapDoubleToFloat(
        final DoubleToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapDoubleToInt(
        final DoubleToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapDoubleToLong(
        final DoubleToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapDoubleToShort(
        final DoubleToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Double> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Double> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Double> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Double> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Double> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesDouble(
        final DoublePredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Double> or(
        final Option<? extends Double> option)
    {
        return this;
    }

    @Override
    public DoubleOption or(
        final DoubleOption option)
    {
        return this;
    }

    @Override
    public Double orElse(
        final Double value)
    {
        return boxedValue();
    }

    @Override
    public Double orElseGet(
        final Supplier<? extends Double> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Double orElseGet(
        final Function<? super A, ? extends Double> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Double orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Double orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Double orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Double> orGet(
        final Supplier<? extends Option<? extends Double>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Double> orGet(
        final Function<? super A, ? extends Option<? extends Double>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public DoubleOption orGetDouble(
        final Supplier<? extends DoubleOption> supplier)
    {
        return this;
    }

    @Override
    public <A> DoubleOption orGetDouble(
        final Function<? super A, ? extends DoubleOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Double> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Double> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public OptionalDouble toOptionalDouble()
    {
        return OptionalDouble.of(value);
    }

    @Override
    public String toString()
    {
        return String.format("DoubleOption[%s]", boxedValue());
    }

    private Double boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Double) value;
    }

    private Object readResolve()
        throws ObjectStreamException
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return this;
    }

    private static final class Cache
    {
        private static final SomeDouble NEGATIVE_INFINITY =
            new SomeDouble(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

        private static final SomeDouble NaN =
            new SomeDouble(Double.NaN, Double.NaN);

        private static final SomeDouble POSITIVE_INFINITY =
            new SomeDouble(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

        public static final SomeDouble get(
            final double value)
        {
            if (Double.isNaN(value))
            {
                return NaN;
            }

            return value < 0.0d ?
                NEGATIVE_INFINITY :
                POSITIVE_INFINITY;
        }

        public static final boolean isCached(
            final double value)
        {
            return !Double.isFinite(value);
        }
    }
}
