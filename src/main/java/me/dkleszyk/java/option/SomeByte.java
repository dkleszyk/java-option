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
 * A {@link ByteOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeByte
    implements ByteOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final byte value;

    private SomeByte(
        final byte value)
    {
        this.value = value;
    }

    public static SomeByte of(
        final byte value)
    {
        return Cache.get(value);
    }

    public static SomeByte of(
        final Byte value)
    {
        return Cache.get((byte) value);
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
    public byte byteOrElse(
        final byte value)
    {
        return this.value;
    }

    @Override
    public byte byteOrElseGet(
        final ByteSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> byte byteOrElseGet(
        final ToByteFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public byte byteOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> byte byteOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> byte byteOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeByte other &&
            value == other.value;
    }

    @Override
    public ByteOption filter(
        final Predicate<? super Byte> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> ByteOption filter(
        final BiPredicate<? super A, ? super Byte> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public ByteOption filterByte(
        final BytePredicate predicate)
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
        return this;
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
    public ByteOption filterToObject()
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
        if (type == byte.class || type.isAssignableFrom(Byte.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <O extends Option<?>> O flatMap(
        final Function<? super Byte, ? extends O> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A, O extends Option<?>> O flatMap(
        final BiFunction<? super A, ? super Byte, ? extends O> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public <O extends Option<?>> O flatMapByte(
        final ByteFunction<? extends O> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public Byte get()
    {
        return boxedValue();
    }

    @Override
    public byte getAsByte()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Byte.hashCode(value);
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
        final Consumer<? super Byte> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Byte> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeByte(
        final ByteConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeByte(
        final ObjByteConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeByteOrElse(
        final ByteConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeByte(ifSome);
    }

    @Override
    public <A> void ifSomeByteOrElse(
        final ByteConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeByte(ifSome);
    }

    @Override
    public <A> void ifSomeByteOrElse(
        final ObjByteConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeByte(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeByteOrElse(
        final ObjByteConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeByte(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeByteOrElse(
        final ObjByteConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeByte(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Byte> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Byte> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Byte> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Byte> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Byte> ifSome,
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
        final Function<? super Byte, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Byte, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapByte(
        final ByteFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapByteToBoolean(
        final BytePredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapByteToByte(
        final ByteUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public ByteOption mapByteToByte(
        final ByteBinaryOperator mapper,
        final byte arg)
    {
        return someNonNull(mapper.applyAsByte(arg, value));
    }

    @Override
    public CharOption mapByteToChar(
        final ByteToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapByteToDouble(
        final ByteToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapByteToFloat(
        final ByteToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapByteToInt(
        final ByteToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapByteToLong(
        final ByteToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapByteToShort(
        final ByteToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Byte> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Byte> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Byte> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Byte> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Byte> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesByte(
        final BytePredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Byte> or(
        final Option<? extends Byte> option)
    {
        return this;
    }

    @Override
    public ByteOption or(
        final ByteOption option)
    {
        return this;
    }

    @Override
    public Byte orElse(
        final Byte value)
    {
        return boxedValue();
    }

    @Override
    public Byte orElseGet(
        final Supplier<? extends Byte> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Byte orElseGet(
        final Function<? super A, ? extends Byte> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Byte orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Byte orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Byte orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Byte> orGet(
        final Supplier<? extends Option<? extends Byte>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Byte> orGet(
        final Function<? super A, ? extends Option<? extends Byte>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public ByteOption orGetByte(
        final Supplier<? extends ByteOption> supplier)
    {
        return this;
    }

    @Override
    public <A> ByteOption orGetByte(
        final Function<? super A, ? extends ByteOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Byte> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Byte> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public String toString()
    {
        return String.format("ByteOption[%s]", boxedValue());
    }

    private Byte boxedValue()
    {
        return (Byte) value;
    }

    private Object readResolve()
        throws ObjectStreamException
    {
        return Cache.get(value);
    }

    private static final class Cache
    {
        private static final SomeByte[] CACHE;

        private static final int MAX = Byte.MAX_VALUE;

        private static final int MIN = Byte.MIN_VALUE;

        static
        {
            final int size = MAX - MIN + 1;
            CACHE = new SomeByte[size];

            for (int i = 0; i < size; i++)
            {
                final byte b = (byte) (MIN + i);
                CACHE[i] = new SomeByte(b);
            }
        }

        public static final SomeByte get(
            final byte value)
        {
            return CACHE[value - MIN];
        }
    }
}
