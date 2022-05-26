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
 * A {@link CharOption} instance that contains a value.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeChar
    implements CharOption, Serializable
{
    private static final long serialVersionUID = 1L;

    private final transient Character boxedValue;

    private final char value;

    private SomeChar(
        final char value)
    {
        this(value, null);
    }

    private SomeChar(
        final char value,
        final Character boxedValue)
    {
        this.value = value;
        this.boxedValue = boxedValue;
    }

    public static SomeChar of(
        final char value)
    {
        if (Cache.isCached(value))
        {
            return Cache.get(value);
        }

        return new SomeChar(value);
    }

    public static SomeChar of(
        final Character value)
    {
        final char c = (char) value;

        if (Cache.isCached(c))
        {
            return Cache.get(c);
        }

        return new SomeChar(c, value);
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
    public char charOrElse(
        final char value)
    {
        return this.value;
    }

    @Override
    public char charOrElseGet(
        final CharSupplier supplier)
    {
        return this.value;
    }

    @Override
    public <A> char charOrElseGet(
        final ToCharFunction<? super A> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public char charOrElseThrow()
    {
        return this.value;
    }

    @Override
    public <X extends Throwable> char charOrElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value;
    }

    @Override
    public <A, X extends Throwable> char charOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value;
    }

    @Override
    public boolean equals(
        final Object obj)
    {
        return obj instanceof SomeChar other &&
            value == other.value;
    }

    @Override
    public CharOption filter(
        final Predicate<? super Character> predicate)
    {
        return predicate.test(boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> CharOption filter(
        final BiPredicate<? super A, ? super Character> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue()) ?
            this :
            noneUnchecked();
    }

    @Override
    public CharOption filterChar(
        final CharPredicate predicate)
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
        return this;
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
    public CharOption filterToObject()
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
        if (type == char.class || type.isAssignableFrom(Character.class))
        {
            @SuppressWarnings(value = "unchecked")
            final Option<T> o = (Option<T>) this;
            return o;
        }

        return noneUnchecked();
    }

    @Override
    public <T> Option<T> flatMap(
        final Function<? super Character, ? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(boxedValue());
        return o;
    }

    @Override
    public <A, T> Option<T> flatMap(
        final BiFunction<? super A, ? super Character, ? extends Option<? extends T>> mapper,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(arg, boxedValue());
        return o;
    }

    @Override
    public <T> Option<T> flatMapChar(
        final CharFunction<? extends Option<? extends T>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<T> o = (Option<T>) mapper.apply(value);
        return o;
    }

    @Override
    public BooleanOption flatMapCharToBoolean(
        final CharFunction<? extends BooleanOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ByteOption flatMapCharToByte(
        final CharFunction<? extends ByteOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public CharOption flatMapCharToChar(
        final CharFunction<? extends CharOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public DoubleOption flatMapCharToDouble(
        final CharFunction<? extends DoubleOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public FloatOption flatMapCharToFloat(
        final CharFunction<? extends FloatOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public IntOption flatMapCharToInt(
        final CharFunction<? extends IntOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public LongOption flatMapCharToLong(
        final CharFunction<? extends LongOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public ShortOption flatMapCharToShort(
        final CharFunction<? extends ShortOption> mapper)
    {
        return mapper.apply(value);
    }

    @Override
    public BooleanOption flatMapToBoolean(
        final Function<? super Character, ? extends BooleanOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super Character, ? extends BooleanOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public ByteOption flatMapToByte(
        final Function<? super Character, ? extends ByteOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super Character, ? extends ByteOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public CharOption flatMapToChar(
        final Function<? super Character, ? extends CharOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super Character, ? extends CharOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public DoubleOption flatMapToDouble(
        final Function<? super Character, ? extends DoubleOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super Character, ? extends DoubleOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public FloatOption flatMapToFloat(
        final Function<? super Character, ? extends FloatOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super Character, ? extends FloatOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public IntOption flatMapToInt(
        final Function<? super Character, ? extends IntOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super Character, ? extends IntOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public LongOption flatMapToLong(
        final Function<? super Character, ? extends LongOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super Character, ? extends LongOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public ShortOption flatMapToShort(
        final Function<? super Character, ? extends ShortOption> mapper)
    {
        return mapper.apply(boxedValue());
    }

    @Override
    public <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super Character, ? extends ShortOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, boxedValue());
    }

    @Override
    public Character get()
    {
        return boxedValue();
    }

    @Override
    public char getAsChar()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return Character.hashCode(value);
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
        final Consumer<? super Character> ifSome)
    {
        ifSome.accept(boxedValue());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super Character> ifSome,
        final A arg)
    {
        ifSome.accept(arg, boxedValue());
    }

    @Override
    public void ifSomeChar(
        final CharConsumer ifSome)
    {
        ifSome.accept(value);
    }

    @Override
    public <A> void ifSomeChar(
        final ObjCharConsumer<? super A> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value);
    }

    @Override
    public void ifSomeCharOrElse(
        final CharConsumer ifSome,
        final Runnable ifNone)
    {
        ifSomeChar(ifSome);
    }

    @Override
    public <A> void ifSomeCharOrElse(
        final CharConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSomeChar(ifSome);
    }

    @Override
    public <A> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSomeChar(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSomeChar(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifSomeChar(ifSome, ifSomeArg);
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super Character> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super Character> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Character> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Character> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super Character> ifSome,
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
        final Function<? super Character, ? extends T> mapper)
    {
        return someNullable(mapper.apply(boxedValue()));
    }

    @Override
    public <A, T> Option<T> map(
        final BiFunction<? super A, ? super Character, ? extends T> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, boxedValue()));
    }

    @Override
    public <T> Option<T> mapChar(
        final CharFunction<? extends T> mapper)
    {
        return someNullable(mapper.apply(value));
    }

    @Override
    public BooleanOption mapCharToBoolean(
        final CharPredicate mapper)
    {
        return someNonNull(mapper.test(value));
    }

    @Override
    public ByteOption mapCharToByte(
        final CharToByteFunction mapper)
    {
        return someNonNull(mapper.applyAsByte(value));
    }

    @Override
    public CharOption mapCharToChar(
        final CharUnaryOperator mapper)
    {
        return someNonNull(mapper.applyAsChar(value));
    }

    @Override
    public CharOption mapCharToChar(
        final CharBinaryOperator mapper,
        final char arg)
    {
        return someNonNull(mapper.applyAsChar(arg, value));
    }

    @Override
    public DoubleOption mapCharToDouble(
        final CharToDoubleFunction mapper)
    {
        return someNonNull(mapper.applyAsDouble(value));
    }

    @Override
    public FloatOption mapCharToFloat(
        final CharToFloatFunction mapper)
    {
        return someNonNull(mapper.applyAsFloat(value));
    }

    @Override
    public IntOption mapCharToInt(
        final CharToIntFunction mapper)
    {
        return someNonNull(mapper.applyAsInt(value));
    }

    @Override
    public LongOption mapCharToLong(
        final CharToLongFunction mapper)
    {
        return someNonNull(mapper.applyAsLong(value));
    }

    @Override
    public ShortOption mapCharToShort(
        final CharToShortFunction mapper)
    {
        return someNonNull(mapper.applyAsShort(value));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super Character> mapper)
    {
        return someNonNull(mapper.test(boxedValue()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, boxedValue()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsByte(boxedValue()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, boxedValue()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsChar(boxedValue()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, boxedValue()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsDouble(boxedValue()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, boxedValue()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsFloat(boxedValue()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, boxedValue()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsInt(boxedValue()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, boxedValue()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsLong(boxedValue()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, boxedValue()));
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super Character> mapper)
    {
        return someNonNull(mapper.applyAsShort(boxedValue()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super Character> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, boxedValue()));
    }

    @Override
    public boolean matches(
        final Predicate<? super Character> predicate)
    {
        return predicate.test(boxedValue());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super Character> predicate,
        final A arg)
    {
        return predicate.test(arg, boxedValue());
    }

    @Override
    public boolean matchesChar(
        final CharPredicate predicate)
    {
        return predicate.test(value);
    }

    @Override
    public Option<Character> or(
        final Option<? extends Character> option)
    {
        return this;
    }

    @Override
    public CharOption or(
        final CharOption option)
    {
        return this;
    }

    @Override
    public Character orElse(
        final Character value)
    {
        return boxedValue();
    }

    @Override
    public Character orElseGet(
        final Supplier<? extends Character> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A> Character orElseGet(
        final Function<? super A, ? extends Character> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Character orElseThrow()
    {
        return boxedValue();
    }

    @Override
    public <X extends Throwable> Character orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return boxedValue();
    }

    @Override
    public <A, X extends Throwable> Character orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return boxedValue();
    }

    @Override
    public Option<Character> orGet(
        final Supplier<? extends Option<? extends Character>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<Character> orGet(
        final Function<? super A, ? extends Option<? extends Character>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public CharOption orGetChar(
        final Supplier<? extends CharOption> supplier)
    {
        return this;
    }

    @Override
    public <A> CharOption orGetChar(
        final Function<? super A, ? extends CharOption> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<Character> stream()
    {
        return Stream.of(boxedValue());
    }

    @Override
    public Optional<Character> toOptional()
    {
        return Optional.of(boxedValue());
    }

    @Override
    public String toString()
    {
        return String.format("CharOption[%s]", boxedValue());
    }

    private Character boxedValue()
    {
        return boxedValue != null ?
            boxedValue :
            (Character) value;
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
        private static final SomeChar[] CACHE;

        private static final int MAX = 255;

        private static final int MIN = 0;

        static
        {
            final int size = MAX - MIN + 1;
            CACHE = new SomeChar[size];

            for (int i = 0; i < size; i++)
            {
                final char c = (char) (MIN + i);
                CACHE[i] = new SomeChar(c, c);
            }
        }

        public static final SomeChar get(
            final char value)
        {
            return CACHE[value - MIN];
        }

        public static final boolean isCached(
            final char value)
        {
            return MIN <= value && value <= MAX;
        }
    }
}
