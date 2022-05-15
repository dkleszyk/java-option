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
import java.util.OptionalInt;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNullable;

/**
 * An {@link IntOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeInt
    implements IntOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Integer boxedValue;

    private final int value;

    private SomeInt(
        final int value)
    {
        this(value, null);
    }

    private SomeInt(
        final int value,
        final Integer boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeInt of(
        final int value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeInt(value);
    }

    public static SomeInt of(
        final Integer value)
    {
        final int i = (int) value;

        if (Cache.isCached(i))
        {
            return Cache.get(i);
        }

        return new SomeInt(i, value);
    }

    @Override
    public <T> Option<T> and(
        final Option<T> option)
    {
        return option;
    }

    @Override
    public BooleanOption and(
        final BooleanOption option)
    {
        return option;
    }

    @Override
    public ByteOption and(
        final ByteOption option)
    {
        return option;
    }

    @Override
    public CharOption and(
        final CharOption option)
    {
        return option;
    }

    @Override
    public DoubleOption and(
        final DoubleOption option)
    {
        return option;
    }

    @Override
    public FloatOption and(
        final FloatOption option)
    {
        return option;
    }

    @Override
    public IntOption and(
        final IntOption option)
    {
        return option;
    }

    @Override
    public LongOption and(
        final LongOption option)
    {
        return option;
    }

    @Override
    public ShortOption and(
        final ShortOption option)
    {
        return option;
    }

    @Override
    public <T> Option<T> andGet(
        final Supplier<? extends Option<? extends T>> supplier)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) supplier.get();
        return o;
    }

    @Override
    public <A, T> Option<T> andGet(
        final Function<? super A, ? extends Option<? extends T>> supplier,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) supplier.apply(arg);
        return o;
    }

    @Override
    public BooleanOption andGetBoolean(
        final Supplier<? extends BooleanOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> BooleanOption andGetBoolean(
        final Function<? super A, ? extends BooleanOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public ByteOption andGetByte(
        Supplier<? extends ByteOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> ByteOption andGetByte(
        final Function<? super A, ? extends ByteOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public CharOption andGetChar(
        Supplier<? extends CharOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> CharOption andGetChar(
        final Function<? super A, ? extends CharOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public DoubleOption andGetDouble(
        final Supplier<? extends DoubleOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> DoubleOption andGetDouble(
        final Function<? super A, ? extends DoubleOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public FloatOption andGetFloat(
        final Supplier<? extends FloatOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> FloatOption andGetFloat(
        final Function<? super A, ? extends FloatOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public IntOption andGetInt(
        final Supplier<? extends IntOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> IntOption andGetInt(
        final Function<? super A, ? extends IntOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public LongOption andGetLong(
        final Supplier<? extends LongOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> LongOption andGetLong(
        final Function<? super A, ? extends LongOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public ShortOption andGetShort(
        final Supplier<? extends ShortOption> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> ShortOption andGetShort(
        final Function<? super A, ? extends ShortOption> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeInt other &&
            value == other.value;
    }

    @Override
    public IntOption filter(
        final Predicate<? super Integer> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> IntOption filter(
        final BiPredicate<? super A, ? super Integer> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public IntOption filterInt(
        final IntPredicate predicate)
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
        return noneUnchecked();
    }

    @Override
    public FloatOption filterToFloat()
    {
        return noneUnchecked();
    }

    @Override
    public IntOption filterToInt()
    {
        return this;
    }

    @Override
    public LongOption filterToLong()
    {
        return noneUnchecked();
    }

    @Override
    public IntOption filterToObject()
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
        if (type == int.class || type.isAssignableFrom(Integer.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <O extends Option<?>> O flatMap(
        final Function<? super Integer, ? extends O> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A, O extends Option<?>> O flatMap(
        final BiFunction<? super A, ? super Integer, ? extends O> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public <O extends Option<?>> O flatMapInt(
        final IntFunction<? extends O> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public Integer get()
    {
        return boxedValue();
    }

    @Override
    public int getAsInt()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(value);
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
        final Consumer<? super Integer> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Integer> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeInt(
        final IntConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeInt(
        final ObjIntConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeIntOrElse(
        final IntConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeInt(ifSome);
    }

    @Override
    public <A> void ifSomeIntOrElse(
        final IntConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeInt(ifSome);
    }

    @Override
    public <A> void ifSomeIntOrElse(
        final ObjIntConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeInt(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeIntOrElse(
        final ObjIntConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeInt(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeIntOrElse(
        final ObjIntConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeInt(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Integer> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Integer> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Integer> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Integer> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Integer> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public int intOrElse(
        final int value)
    {
        return this.value;
    }

    @Override
    public int intOrElseGet(
        final IntSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> int intOrElseGet(
        final ToIntFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public int intOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> int intOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> int intOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public IntStream intStream()
    {
        return IntStream.of(value);
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
        final Function<? super Integer, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Integer, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapInt(
        final IntFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapIntToBoolean(
        final IntPredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapIntToByte(
        final IntToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapIntToChar(
        final IntToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapIntToDouble(
        final IntToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapIntToFloat(
        final IntToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapIntToInt(
        final IntUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public IntOption mapIntToInt(
        final IntBinaryOperator mapper,
        final int arg)
    {
        return someNonNull(mapper.applyAsInt(arg, value));
    }

    @Override
    public LongOption mapIntToLong(
        final IntToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapIntToShort(
        final IntToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Integer> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Integer> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Integer> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Integer> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Integer> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesInt(
        final IntPredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Integer> or(
        final Option<? extends Integer> option)
    {
        return this;
    }

    @Override
    public IntOption or(
        final IntOption option)
    {
        return this;
    }

    @Override
    public Integer orElse(
        final Integer value)
    {
        return boxedValue();
    }

    @Override
    public Integer orElseGet(
        final Supplier<? extends Integer> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Integer orElseGet(
        final Function<? super A, ? extends Integer> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Integer orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Integer orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Integer orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Integer> orGet(
        final Supplier<? extends Option<? extends Integer>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Integer> orGet(
        final Function<? super A, ? extends Option<? extends Integer>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public IntOption orGetInt(
        final Supplier<? extends IntOption> supplier)
    {
        return this;
    }

    @Override
    public <A> IntOption orGetInt(
        final Function<? super A, ? extends IntOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Integer> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Integer> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public OptionalInt toOptionalInt()
    {
        return OptionalInt.of(value);
    }

    @Override
    public String toString()
    {
        return String.format("IntOption[%s]", boxedValue());
    }

    private Integer boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Integer) value;
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
        private static final SomeInt[] CACHE;

        private static final int MAX = 127;

        private static final int MIN = -128;

        static
        {
            final int size = MAX - MIN + 1;
            CACHE = new SomeInt[size];

            for (int i = 0; i < size; i++)
            {
                final int n = MIN + i;
                CACHE[i] = new SomeInt(n, n);
            }
        }

        public static final SomeInt get(
            final int value)
        {
            return CACHE[value - MIN];
        }

        public static final boolean isCached(
            final int value)
        {
            return MIN <= value && value <= MAX;
        }
    }
}
