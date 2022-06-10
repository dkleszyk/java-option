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
 * A {@link FloatOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeFloat
    implements FloatOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Float boxedValue;

    private final float value;

    private SomeFloat(
        final float value)
    {
        this(value, null);
    }

    private SomeFloat(
        final float value,
        final Float boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeFloat of(
        final float value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeFloat(value);
    }

    public static SomeFloat of(
        final Float value)
    {
        final float f = (float) value;

        if (Cache.isCached(f))
        {
            return Cache.get(f);
        }

        return new SomeFloat(f, value);
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
        return obj instanceof SomeFloat other &&
            Float.floatToIntBits(value) == Float.floatToIntBits(other.value);
    }

    @Override
    public FloatOption filter(
        final Predicate<? super Float> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> FloatOption filter(
        final BiPredicate<? super A, ? super Float> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public FloatOption filterFloat(
        final FloatPredicate predicate)
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
        return this;
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
    public FloatOption filterToObject()
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
        if (type == float.class || type.isAssignableFrom(Float.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <O extends Option<?>> O flatMap(
        final Function<? super Float, ? extends O> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A, O extends Option<?>> O flatMap(
        final BiFunction<? super A, ? super Float, ? extends O> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public <O extends Option<?>> O flatMapFloat(
        final FloatFunction<? extends O> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public float floatOrElse(
        final float value)
    {
        return this.value;
    }

    @Override
    public float floatOrElseGet(
        final FloatSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> float floatOrElseGet(
        final ToFloatFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public float floatOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> float floatOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> float floatOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public Float get()
    {
        return boxedValue();
    }

    @Override
    public float getAsFloat()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Float.hashCode(value);
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
        final Consumer<? super Float> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Float> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeFloat(
        final FloatConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeFloat(
        final ObjFloatConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeFloatOrElse(
        final FloatConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeFloat(ifSome);
    }

    @Override
    public <A> void ifSomeFloatOrElse(
        final FloatConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeFloat(ifSome);
    }

    @Override
    public <A> void ifSomeFloatOrElse(
        final ObjFloatConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeFloat(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeFloatOrElse(
        final ObjFloatConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeFloat(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeFloatOrElse(
        final ObjFloatConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeFloat(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Float> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Float> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Float> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Float> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Float> ifSome,
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
        final Function<? super Float, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Float, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapFloat(
        final FloatFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapFloatToBoolean(
        final FloatPredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapFloatToByte(
        final FloatToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapFloatToChar(
        final FloatToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapFloatToDouble(
        final FloatToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapFloatToFloat(
        final FloatUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public FloatOption mapFloatToFloat(
        final FloatBinaryOperator mapper,
        final float arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, value));
    }

    @Override
    public IntOption mapFloatToInt(
        final FloatToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapFloatToLong(
        final FloatToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapFloatToShort(
        final FloatToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Float> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Float> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Float> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Float> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Float> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesFloat(
        final FloatPredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Float> or(
        final Option<? extends Float> option)
    {
        return this;
    }

    @Override
    public FloatOption or(
        final FloatOption option)
    {
        return this;
    }

    @Override
    public Float orElse(
        final Float value)
    {
        return boxedValue();
    }

    @Override
    public Float orElseGet(
        final Supplier<? extends Float> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Float orElseGet(
        final Function<? super A, ? extends Float> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Float orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Float orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Float orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Float> orGet(
        final Supplier<? extends Option<? extends Float>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Float> orGet(
        final Function<? super A, ? extends Option<? extends Float>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public FloatOption orGetFloat(
        final Supplier<? extends FloatOption> supplier)
    {
        return this;
    }

    @Override
    public <A> FloatOption orGetFloat(
        final Function<? super A, ? extends FloatOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Float> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Float> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public String toString()
    {
        return String.format("FloatOption[%s]", boxedValue());
    }

    private Float boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Float) value;
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
        private static final int CANONICAL_NAN_BITS = 0x7fc00000;

        private static final SomeFloat NEGATIVE_INFINITY =
            new SomeFloat(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);

        private static final SomeFloat NEGATIVE_ZERO =
            new SomeFloat(-0.0f, -0.0f);

        private static final SomeFloat NaN =
            new SomeFloat(Float.NaN, Float.NaN);

        private static final SomeFloat POSITIVE_INFINITY =
            new SomeFloat(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);

        private static final SomeFloat POSITIVE_ZERO =
            new SomeFloat(+0.0f, +0.0f);

        public static final SomeFloat get(
            final float value)
        {
            final int bits = Float.floatToRawIntBits(value);
            return switch ((bits >> 22) & 0x3ff)
            {
                case 0x000 ->
                    POSITIVE_ZERO;
                case 0x1fe ->
                    POSITIVE_INFINITY;
                case 0x1ff ->
                    NaN;
                case 0x200 ->
                    NEGATIVE_ZERO;
                case 0x3fe ->
                    NEGATIVE_INFINITY;
                default ->
                    throw new AssertionError(bits);
            };
        }

        public static final boolean isCached(
            final float value)
        {
            // We only cache the canonical NaN (0x7fc00000) because IEEE 754
            // is lax about what goes on in the mantissa bits for NaNs,
            // and we don't want to transmute all NaNs into a single
            // cached value in case applications are doing something funky
            // with those other bits
            return value == 0.0f || // (handles both +0.0 and -0.0)
                Float.isInfinite(value) ||
                Float.floatToRawIntBits(value) == CANONICAL_NAN_BITS;
        }
    }
}
