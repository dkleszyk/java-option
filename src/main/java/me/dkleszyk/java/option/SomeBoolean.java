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

import java.io.Serializable;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;

import static me.dkleszyk.java.option.Options.noneUnchecked;
import static me.dkleszyk.java.option.Options.someNonNull;
import static me.dkleszyk.java.option.Options.someNullable;

/**
 * A {@link BooleanOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeBoolean
    implements BooleanOption, Serializable
{
    private static final SomeBoolean FALSE = new SomeBoolean(false);

    private static final SomeBoolean TRUE = new SomeBoolean(true);

    private static final long serialVersionUID = 1L;

    private final boolean value;

    private SomeBoolean(
        final boolean value)
    {
        this.value = value;
    }

    public static SomeBoolean of(
        final boolean value)
    {
        return value ?
            TRUE :
            FALSE;
    }

    public static SomeBoolean of(
        final Boolean value)
    {
        return Boolean.TRUE.equals(value) ?
            TRUE :
            FALSE;
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
    public boolean booleanOrElse(
        final boolean value)
    {
        return this.value;
    }

    @Override
    public boolean booleanOrElseGet(
        final BooleanSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> boolean booleanOrElseGet(
        final Predicate<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public boolean booleanOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> boolean booleanOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> boolean booleanOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeBoolean other &&
            value == other.value;
    }

    @Override
    public BooleanOption filter(
        final Predicate<? super Boolean> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> BooleanOption filter(
        final BiPredicate<? super A, ? super Boolean> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public BooleanOption filterBoolean(
        final BooleanUnaryOperator predicate)
    {
        return predicate.applyAsBoolean(value) ?
            this :
            noneUnchecked();
    }

    @Override
    public BooleanOption filterToBoolean()
    {
        return this;
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
    public BooleanOption filterToObject()
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
        if (type == boolean.class || type.isAssignableFrom(Boolean.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <T> Option<T> flatMap(
        final Function<? super Boolean, ? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(boxedValue());
        return o;
    }

    @Override
    public <A, T> Option<T> flatMap(
        final BiFunction<? super A, ? super Boolean, ? extends Option<? extends T>> mapper,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(arg, boxedValue());
        return o;
    }

    @Override
    public <T> Option<T> flatMapBoolean(
        final BooleanFunction<? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(value);
        return o;
    }

    @Override
    public BooleanOption flatMapBooleanToBoolean(
        final BooleanFunction<? extends BooleanOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ByteOption flatMapBooleanToByte(
        final BooleanFunction<? extends ByteOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public CharOption flatMapBooleanToChar(
        final BooleanFunction<? extends CharOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public DoubleOption flatMapBooleanToDouble(
        final BooleanFunction<? extends DoubleOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public FloatOption flatMapBooleanToFloat(
        final BooleanFunction<? extends FloatOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public IntOption flatMapBooleanToInt(
        final BooleanFunction<? extends IntOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public LongOption flatMapBooleanToLong(
        final BooleanFunction<? extends LongOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ShortOption flatMapBooleanToShort(
        final BooleanFunction<? extends ShortOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public BooleanOption flatMapToBoolean(
        final Function<? super Boolean, ? extends BooleanOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super Boolean, ? extends BooleanOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public ByteOption flatMapToByte(
        final Function<? super Boolean, ? extends ByteOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super Boolean, ? extends ByteOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public CharOption flatMapToChar(
        final Function<? super Boolean, ? extends CharOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super Boolean, ? extends CharOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public DoubleOption flatMapToDouble(
        final Function<? super Boolean, ? extends DoubleOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super Boolean, ? extends DoubleOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public FloatOption flatMapToFloat(
        final Function<? super Boolean, ? extends FloatOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super Boolean, ? extends FloatOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public IntOption flatMapToInt(
        final Function<? super Boolean, ? extends IntOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super Boolean, ? extends IntOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public LongOption flatMapToLong(
        final Function<? super Boolean, ? extends LongOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super Boolean, ? extends LongOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public ShortOption flatMapToShort(
        final Function<? super Boolean, ? extends ShortOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super Boolean, ? extends ShortOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value);
    }

    @Override
    public Boolean get()
    {
        return boxedValue();
    }

    @Override
    public boolean getAsBoolean()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Boolean.hashCode(value);
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
        final Consumer<? super Boolean> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Boolean> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeBoolean(
        final BooleanConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeBoolean(
        final ObjBooleanConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeBooleanOrElse(
        final BooleanConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeBoolean(ifSome);
    }

    @Override
    public <A> void ifSomeBooleanOrElse(
        final BooleanConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeBoolean(ifSome);
    }

    @Override
    public <A> void ifSomeBooleanOrElse(
        final ObjBooleanConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeBoolean(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeBooleanOrElse(
        final ObjBooleanConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeBoolean(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeBooleanOrElse(
        final ObjBooleanConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeBoolean(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Boolean> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Boolean> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Boolean> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Boolean> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Boolean> ifSome,
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
        final Function<? super Boolean, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Boolean, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapBoolean(
        final BooleanFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapBooleanToBoolean(
        final BooleanUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsBoolean(value));
    }

    @Override
    public BooleanOption mapBooleanToBoolean(
        final BooleanBinaryOperator mapper,
        final boolean arg)
    {
        return someNonNull(mapper.applyAsBoolean(arg, value));
    }

    @Override
    public ByteOption mapBooleanToByte(
        final BooleanToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapBooleanToChar(
        final BooleanToCharFunction mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public DoubleOption mapBooleanToDouble(
        final BooleanToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapBooleanToFloat(
        final BooleanToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapBooleanToInt(
        final BooleanToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapBooleanToLong(
        final BooleanToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapBooleanToShort(
        final BooleanToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Boolean> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Boolean> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Boolean> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Boolean> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Boolean> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesBoolean(
        final BooleanUnaryOperator predicate)
    {
        return predicate.applyAsBoolean(value);
    }

    @Override
    public Option<Boolean> or(
        final Option<? extends Boolean> option)
    {
        return this;
    }

    @Override
    public BooleanOption or(
        final BooleanOption option)
    {
        return this;
    }

    @Override
    public Boolean orElse(
        final Boolean value)
    {
        return boxedValue();
    }

    @Override
    public Boolean orElseGet(
        final Supplier<? extends Boolean> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Boolean orElseGet(
        final Function<? super A, ? extends Boolean> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Boolean orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Boolean orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Boolean orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Boolean> orGet(
        final Supplier<? extends Option<? extends Boolean>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Boolean> orGet(
        final Function<? super A, ? extends Option<? extends Boolean>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public BooleanOption orGetBoolean(
        final Supplier<? extends BooleanOption> supplier)
    {
        return this;
    }

    @Override
    public <A> BooleanOption orGetBoolean(
        final Function<? super A, ? extends BooleanOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Boolean> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Boolean> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public String toString()
    {
        return String.format("BooleanOption[%s]", boxedValue());
    }

    private Boolean boxedValue()
    {
        return (Boolean) value;
    }

    private Object readResolve()
    {
        return value ?
            TRUE :
            FALSE;
    }
}
