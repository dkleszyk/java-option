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
import java.util.function.*;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNullable;

/**
 * A {@link ShortOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeShort
    implements ShortOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Short boxedValue;

    private final short value;

    private SomeShort(
        final short value)
    {
        this(value, null);
    }

    private SomeShort(
        final short value,
        final Short boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeShort of(
        final short value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeShort(value);
    }

    public static SomeShort of(
        final Short value)
    {
        final short s = (short) value;

        if (Cache.isCached(s))
        {
            return Cache.get(s);
        }

        return new SomeShort(s, value);
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
        return obj instanceof SomeShort other &&
            value == other.value;
    }

    @Override
    public ShortOption filter(
        final Predicate<? super Short> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> ShortOption filter(
        final BiPredicate<? super A, ? super Short> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public ShortOption filterShort(
        final ShortPredicate predicate)
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
        return noneUnchecked();
    }

    @Override
    public LongOption filterToLong()
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption filterToObject()
    {
        return this;
    }

    @Override
    public ShortOption filterToShort()
    {
        return this;
    }

    @Override
    public <T> Option<T> filterToType(
        final Class<T> type)
    {
        if (type == short.class || type.isAssignableFrom(Short.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <O extends Option<?>> O flatMap(
        final Function<? super Short, ? extends O> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A, O extends Option<?>> O flatMap(
        final BiFunction<? super A, ? super Short, ? extends O> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public <T> Option<T> flatMapShort(
        final ShortFunction<? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(value);
        return o;
    }

    @Override
    public BooleanOption flatMapShortToBoolean(
        final ShortFunction<? extends BooleanOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ByteOption flatMapShortToByte(
        final ShortFunction<? extends ByteOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public CharOption flatMapShortToChar(
        final ShortFunction<? extends CharOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public DoubleOption flatMapShortToDouble(
        final ShortFunction<? extends DoubleOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public FloatOption flatMapShortToFloat(
        final ShortFunction<? extends FloatOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public IntOption flatMapShortToInt(
        final ShortFunction<? extends IntOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public LongOption flatMapShortToLong(
        final ShortFunction<? extends LongOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ShortOption flatMapShortToShort(
        final ShortFunction<? extends ShortOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public Short get()
    {
        return boxedValue();
    }

    @Override
    public short getAsShort()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Short.hashCode(value);
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
        final Consumer<? super Short> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Short> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Short> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Short> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Short> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Short> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Short> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeShort(
        final ShortConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeShort(
        final ObjShortConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeShortOrElse(
        final ShortConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeShort(ifSome);
    }

    @Override
    public <A> void ifSomeShortOrElse(
        final ShortConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeShort(ifSome);
    }

    @Override
    public <A> void ifSomeShortOrElse(
        final ObjShortConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeShort(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeShortOrElse(
        final ObjShortConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeShort(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeShortOrElse(
        final ObjShortConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeShort(ifSome, ifSomeArg);
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
        final Function<? super Short, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Short, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapShort(
        final ShortFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapShortToBoolean(
        final ShortPredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapShortToByte(
        final ShortToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapShortToChar(
        final ShortToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapShortToDouble(
        final ShortToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapShortToFloat(
        final ShortToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapShortToInt(
        final ShortToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapShortToLong(
        final ShortToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapShortToShort(
        final ShortUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public ShortOption mapShortToShort(
        final ShortBinaryOperator mapper,
        final short arg)
    {
        return someNonNull(mapper.applyAsShort(arg, value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Short> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Short> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Short> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Short> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Short> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesShort(
        final ShortPredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Short> or(
        final Option<? extends Short> option)
    {
        return this;
    }

    @Override
    public ShortOption or(
        final ShortOption option)
    {
        return this;
    }

    @Override
    public Short orElse(
        final Short value)
    {
        return boxedValue();
    }

    @Override
    public Short orElseGet(
        final Supplier<? extends Short> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Short orElseGet(
        final Function<? super A, ? extends Short> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Short orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Short orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Short orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Short> orGet(
        final Supplier<? extends Option<? extends Short>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Short> orGet(
        final Function<? super A, ? extends Option<? extends Short>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public ShortOption orGetShort(
        final Supplier<? extends ShortOption> supplier)
    {
        return this;
    }

    @Override
    public <A> ShortOption orGetShort(
        final Function<? super A, ? extends ShortOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public short shortOrElse(
        final short value)
    {
        return this.value;
    }

    @Override
    public short shortOrElseGet(
        final ShortSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> short shortOrElseGet(
        final ToShortFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public short shortOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> short shortOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> short shortOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public Stream<Short> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Short> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public String toString()
    {
        return String.format("ShortOption[%s]", boxedValue());
    }

    private Short boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Short) value;
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
        private static final SomeShort[] CACHE;

        private static final int MAX = 127;

        private static final int MIN = -128;

        static
        {
            final int size = MAX - MIN + 1;
            CACHE = new SomeShort[size];

            for (int i = 0; i < size; i++)
            {
                final short s = (short) (MIN + i);
                CACHE[i] = new SomeShort(s, s);
            }
        }

        public static final SomeShort get(
            final short value)
        {
            return CACHE[value - MIN];
        }

        public static final boolean isCached(
            final short value)
        {
            return MIN <= value && value <= MAX;
        }
    }
}
