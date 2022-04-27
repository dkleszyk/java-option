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
 * An {@link Option} instance that contains {@code null}.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class SomeNull<T>
    implements Option<T>, Serializable
{
    public static final SomeNull<?> INSTANCE = new SomeNull<>();

    private static final long serialVersionUID = 1L;

    private SomeNull()
    {
    }

    @Override
    public <U> Option<U> and(
        final Option<U> option)
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
    public <U> Option<U> andGet(
        final Supplier<? extends Option<? extends U>> supplier)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<U> o = (Option<U>) supplier.get();
        return o;
    }

    @Override
    public <A, U> Option<U> andGet(
        final Function<? super A, ? extends Option<? extends U>> supplier,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<U> o = (Option<U>) supplier.apply(arg);
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
        final Supplier<? extends ByteOption> supplier)
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
        final Supplier<? extends CharOption> supplier)
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
        Supplier<? extends DoubleOption> supplier)
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
        return obj == this;
    }

    @Override
    public Option<T> filter(
        final Predicate<? super T> predicate)
    {
        return predicate.test(value()) ?
            this :
            noneUnchecked();
    }

    @Override
    public <A> Option<T> filter(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg)
    {
        return predicate.test(arg, value()) ?
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
    public Option<T> filterToObject()
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption filterToShort()
    {
        return noneUnchecked();
    }

    @Override
    public <U> Option<U> filterToType(
        final Class<U> type)
    {
        return noneUnchecked();
    }

    @Override
    public <U> Option<U> flatMap(
        final Function<? super T, ? extends Option<? extends U>> mapper)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<U> o = (Option<U>) mapper.apply(value());
        return o;
    }

    @Override
    public <A, U> Option<U> flatMap(
        final BiFunction<? super A, ? super T, ? extends Option<? extends U>> mapper,
        final A arg)
    {
        @SuppressWarnings(value = "unchecked")
        final Option<U> o = (Option<U>) mapper.apply(arg, value());
        return o;
    }

    @Override
    public BooleanOption flatMapToBoolean(
        final Function<? super T, ? extends BooleanOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super T, ? extends BooleanOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public ByteOption flatMapToByte(
        final Function<? super T, ? extends ByteOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super T, ? extends ByteOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public CharOption flatMapToChar(
        final Function<? super T, ? extends CharOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super T, ? extends CharOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public DoubleOption flatMapToDouble(
        final Function<? super T, ? extends DoubleOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super T, ? extends DoubleOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public FloatOption flatMapToFloat(
        final Function<? super T, ? extends FloatOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super T, ? extends FloatOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public IntOption flatMapToInt(
        final Function<? super T, ? extends IntOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super T, ? extends IntOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public LongOption flatMapToLong(
        final Function<? super T, ? extends LongOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super T, ? extends LongOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public ShortOption flatMapToShort(
        final Function<? super T, ? extends ShortOption> mapper)
    {
        return mapper.apply(value());
    }

    @Override
    public <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super T, ? extends ShortOption> mapper,
        final A arg)
    {
        return mapper.apply(arg, value());
    }

    @Override
    public T get()
    {
        return value();
    }

    @Override
    public int hashCode()
    {
        return -1;
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
        final Consumer<? super T> ifSome)
    {
        ifSome.accept(value());
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super T> ifSome,
        final A arg)
    {
        ifSome.accept(arg, value());
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Runnable ifNone)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifSome(ifSome);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifSome(ifSome, ifSomeArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifSome(ifSome, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
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
    public <U> Option<U> map(
        final Function<? super T, ? extends U> mapper)
    {
        return someNullable(mapper.apply(value()));
    }

    @Override
    public <A, U> Option<U> map(
        final BiFunction<? super A, ? super T, ? extends U> mapper,
        final A arg)
    {
        return someNullable(mapper.apply(arg, value()));
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super T> mapper)
    {
        return someNonNull(mapper.test(value()));
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.test(arg, value()));
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsByte(value()));
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsByte(arg, value()));
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsChar(value()));
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsChar(arg, value()));
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsDouble(value()));
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsDouble(arg, value()));
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsFloat(value()));
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsFloat(arg, value()));
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsInt(value()));
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsInt(arg, value()));
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsLong(value()));
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsLong(arg, value()));
    }

    @Override
    public ShortOption mapToShort(
        ToShortFunction<? super T> mapper)
    {
        return someNonNull(mapper.applyAsShort(value()));
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return someNonNull(mapper.applyAsShort(arg, value()));
    }

    @Override
    public boolean matches(
        final Predicate<? super T> predicate)
    {
        return predicate.test(value());
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg)
    {
        return predicate.test(arg, value());
    }

    @Override
    public Option<T> or(
        final Option<? extends T> option)
    {
        return this;
    }

    @Override
    public T orElse(
        final T value)
    {
        return this.value();
    }

    @Override
    public T orElseGet(
        final Supplier<? extends T> supplier)
    {
        return this.value();
    }

    @Override
    public <A> T orElseGet(
        Function<? super A, ? extends T> supplier,
        A arg)
    {
        return this.value();
    }

    @Override
    public T orElseThrow()
    {
        return this.value();
    }

    @Override
    public <X extends Throwable> T orElseThrow(
        final Supplier<? extends X> supplier)
    {
        return this.value();
    }

    @Override
    public <A, X extends Throwable> T orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
    {
        return this.value();
    }

    @Override
    public Option<T> orGet(
        final Supplier<? extends Option<? extends T>> supplier)
    {
        return this;
    }

    @Override
    public <A> Option<T> orGet(
        final Function<? super A, ? extends Option<? extends T>> supplier,
        final A arg)
    {
        return this;
    }

    @Override
    public Stream<T> stream()
    {
        return Stream.of(value());
    }

    @Override
    public Optional<T> toOptional()
    {
        // This intentionally throws an NPE
        return Optional.of(value());
    }

    @Override
    public String toString()
    {
        return "Option[null]";
    }

    private Object readResolve()
        throws ObjectStreamException
    {
        return INSTANCE;
    }

    private T value()
    {
        return null;
    }
}
