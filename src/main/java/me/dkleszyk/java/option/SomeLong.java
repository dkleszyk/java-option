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
import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNullable;

/**
 * A {@link LongOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeLong
    implements LongOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Long boxedValue;

    private final long value;

    private SomeLong(
        final long value)
    {
        this(value, null);
    }

    private SomeLong(
        final long value,
        final Long boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeLong of(
        final long value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeLong(value);
    }

    public static SomeLong of(
        final Long value)
    {
        final long l = (long) value;

        if (Cache.isCached(l))
        {
            return Cache.get(l);
        }

        return new SomeLong(l, value);
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
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeLong other &&
            value == other.value;
    }

    @Override
    public LongOption filter(
        final Predicate<? super Long> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> LongOption filter(
        final BiPredicate<? super A, ? super Long> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public LongOption filterLong(
        final LongPredicate predicate)
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
        return this;
    }

    @Override
    public LongOption filterToObject()
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
        if (type == long.class || type.isAssignableFrom(Long.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <O extends Option<?>> O flatMap(
        final Function<? super Long, ? extends O> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A, O extends Option<?>> O flatMap(
        final BiFunction<? super A, ? super Long, ? extends O> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public <O extends Option<?>> O flatMapLong(
        final LongFunction<? extends O> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(value);
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
        final Consumer<? super Long> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Long> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeLong(
        final LongConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeLong(
        final ObjLongConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeLongOrElse(
        final LongConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeLong(ifSome);
    }

    @Override
    public <A> void ifSomeLongOrElse(
        final LongConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeLong(ifSome);
    }

    @Override
    public <A> void ifSomeLongOrElse(
        final ObjLongConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeLong(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeLongOrElse(
        final ObjLongConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeLong(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeLongOrElse(
        final ObjLongConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeLong(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Long> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Long> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Long> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Long> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Long> ifSome,
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
    public long longOrElse(
        final long value)
    {
        return this.value;
    }

    @Override
    public long longOrElseGet(
        final LongSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> long longOrElseGet(
        final ToLongFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public long longOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> long longOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> long longOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public LongStream longStream()
    {
        return LongStream.of(value);
    }

    @Override
    public <T> Option<T> map(
        final Function<? super Long, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Long, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapLong(
        final LongFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapLongToBoolean(
        final LongPredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapLongToByte(
        final LongToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapLongToChar(
        final LongToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapLongToDouble(
        final LongToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapLongToFloat(
        final LongToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapLongToInt(
        final LongToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapLongToLong(
        final LongUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public LongOption mapLongToLong(
        final LongBinaryOperator mapper,
        final long arg)
    {
        return someNonNull(mapper.applyAsLong(arg, value));
    }

    @Override
    public ShortOption mapLongToShort(
        final LongToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Long> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Long> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Long> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Long> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Long> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesLong(
        final LongPredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Long> or(
        final Option<? extends Long> option)
    {
        return this;
    }

    @Override
    public LongOption or(
        final LongOption option)
    {
        return this;
    }

    @Override
    public Long orElse(
        final Long value)
    {
        return boxedValue();
    }

    @Override
    public Long orElseGet(
        final Supplier<? extends Long> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Long orElseGet(
        final Function<? super A, ? extends Long> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Long orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Long orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Long orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Long> orGet(
        final Supplier<? extends Option<? extends Long>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Long> orGet(
        final Function<? super A, ? extends Option<? extends Long>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public LongOption orGetLong(
        final Supplier<? extends LongOption> supplier)
    {
        return this;
    }

    @Override
    public <A> LongOption orGetLong(
        final Function<? super A, ? extends LongOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Long> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Long> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public OptionalLong toOptionalLong()
    {
        return OptionalLong.of(value);
    }

    @Override
    public String toString()
    {
        return String.format("LongOption[%s]", boxedValue());
    }

    private Long boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Long) value;
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
        private static final SomeLong[] CACHE;

        private static final long MAX = 127;

        private static final long MIN = -128;

        static
        {
            final int size = (int) (MAX - MIN + 1);
            CACHE = new SomeLong[size];

            for (int i = 0; i < size; i++)
            {
                final long l = MIN + i;
                CACHE[i] = new SomeLong(l, l);
            }
        }

        public static final SomeLong get(
            final long value)
        {
            return CACHE[(int) (value - MIN)];
        }

        public static final boolean isCached(
            final long value)
        {
            return MIN <= value && value <= MAX;
        }
    }
}
