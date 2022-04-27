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

/**
 * An {@link Option} that does not contain a value.
 * <p>
 * Note that <c>rewriter.py</c> is called after compilation to weave in the
 * implementations for the primitive-typed option specializations. Even though
 * javac forbids implementing a generic interface multiple times with differing
 * generic parameters, the runtime doesn't seem to have a problem with it. Since
 * this class doesn't actually contain a value of type <c>T</c>, we shouldn't
 * run into any <c>ClassCastException</c>s after erasure.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class None<T>
    implements Option<T>, Serializable
{
    public static final None<?> INSTANCE = new None<>();

    private static final long serialVersionUID = 1L;

    private None()
    {
    }

    @Override
    public <U> Option<U> and(
        final Option<U> option)
    {
        return noneUnchecked();
    }

    @Override
    public BooleanOption and(
        final BooleanOption option)
    {
        return noneUnchecked();
    }

    @Override
    public ByteOption and(
        final ByteOption option)
    {
        return noneUnchecked();
    }

    @Override
    public CharOption and(
        final CharOption option)
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption and(
        final DoubleOption option)
    {
        return noneUnchecked();
    }

    @Override
    public FloatOption and(
        final FloatOption option)
    {
        return noneUnchecked();
    }

    @Override
    public IntOption and(
        final IntOption option)
    {
        return noneUnchecked();
    }

    @Override
    public LongOption and(
        final LongOption option)
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption and(
        final ShortOption option)
    {
        return noneUnchecked();
    }

    @Override
    public <U> Option<U> andGet(
        final Supplier<? extends Option<? extends U>> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A, U> Option<U> andGet(
        final Function<? super A, ? extends Option<? extends U>> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public BooleanOption andGetBoolean(
        final Supplier<? extends BooleanOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> BooleanOption andGetBoolean(
        final Function<? super A, ? extends BooleanOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ByteOption andGetByte(
        final Supplier<? extends ByteOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ByteOption andGetByte(
        final Function<? super A, ? extends ByteOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public CharOption andGetChar(
        final Supplier<? extends CharOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> CharOption andGetChar(
        final Function<? super A, ? extends CharOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption andGetDouble(
        final Supplier<? extends DoubleOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> DoubleOption andGetDouble(
        final Function<? super A, ? extends DoubleOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public FloatOption andGetFloat(
        final Supplier<? extends FloatOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> FloatOption andGetFloat(
        final Function<? super A, ? extends FloatOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public IntOption andGetInt(
        final Supplier<? extends IntOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> IntOption andGetInt(
        final Function<? super A, ? extends IntOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public LongOption andGetLong(
        final Supplier<? extends LongOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> LongOption andGetLong(
        final Function<? super A, ? extends LongOption> supplier,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption andGetShort(
        final Supplier<? extends ShortOption> supplier)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ShortOption andGetShort(
        final Function<? super A, ? extends ShortOption> supplier,
        final A arg)
    {
        return noneUnchecked();
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
        return noneUnchecked();
    }

    @Override
    public <A> Option<T> filter(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg)
    {
        return noneUnchecked();
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
        return noneUnchecked();
    }

    @Override
    public <A, U> Option<U> flatMap(
        final BiFunction<? super A, ? super T, ? extends Option<? extends U>> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public BooleanOption flatMapToBoolean(
        final Function<? super T, ? extends BooleanOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> BooleanOption flatMapToBoolean(
        final BiFunction<? super A, ? super T, ? extends BooleanOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ByteOption flatMapToByte(
        final Function<? super T, ? extends ByteOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ByteOption flatMapToByte(
        final BiFunction<? super A, ? super T, ? extends ByteOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public CharOption flatMapToChar(
        final Function<? super T, ? extends CharOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> CharOption flatMapToChar(
        final BiFunction<? super A, ? super T, ? extends CharOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption flatMapToDouble(
        final Function<? super T, ? extends DoubleOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> DoubleOption flatMapToDouble(
        final BiFunction<? super A, ? super T, ? extends DoubleOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public FloatOption flatMapToFloat(
        final Function<? super T, ? extends FloatOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> FloatOption flatMapToFloat(
        final BiFunction<? super A, ? super T, ? extends FloatOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public IntOption flatMapToInt(
        final Function<? super T, ? extends IntOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> IntOption flatMapToInt(
        final BiFunction<? super A, ? super T, ? extends IntOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public LongOption flatMapToLong(
        final Function<? super T, ? extends LongOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> LongOption flatMapToLong(
        final BiFunction<? super A, ? super T, ? extends LongOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption flatMapToShort(
        final Function<? super T, ? extends ShortOption> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ShortOption flatMapToShort(
        final BiFunction<? super A, ? super T, ? extends ShortOption> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public T get()
    {
        throw Exceptions.optionNoValue();
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public void ifNone(
        final Runnable ifNone)
    {
        ifNone.run();
    }

    @Override
    public <A> void ifNone(
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifNone.accept(arg);
    }

    @Override
    public void ifSome(
        final Consumer<? super T> ifSome)
    {
    }

    @Override
    public <A> void ifSome(
        final BiConsumer<? super A, ? super T> ifSome,
        final A arg)
    {
    }

    @Override
    public void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Runnable ifNone)
    {
        ifNone(ifNone);
    }

    @Override
    public <A> void ifSomeOrElse(
        final Consumer<? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg)
    {
        ifNone(ifNone, ifNoneArg);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final A ifSomeArg,
        final Runnable ifNone)
    {
        ifNone(ifNone);
    }

    @Override
    public <A> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final Consumer<? super A> ifNone,
        final A arg)
    {
        ifNone(ifNone, arg);
    }

    @Override
    public <A, B> void ifSomeOrElse(
        final BiConsumer<? super A, ? super T> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg)
    {
        ifNone(ifNone, ifNoneArg);
    }

    @Override
    public boolean isNone()
    {
        return true;
    }

    @Override
    public boolean isSome()
    {
        return false;
    }

    @Override
    public <U> Option<U> map(
        final Function<? super T, ? extends U> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A, U> Option<U> map(
        final BiFunction<? super A, ? super T, ? extends U> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public BooleanOption mapToBoolean(
        final Predicate<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> BooleanOption mapToBoolean(
        final BiPredicate<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ByteOption mapToByte(
        final ToByteFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ByteOption mapToByte(
        final ToByteBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public CharOption mapToChar(
        final ToCharFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> CharOption mapToChar(
        final ToCharBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public DoubleOption mapToDouble(
        final ToDoubleFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> DoubleOption mapToDouble(
        final ToDoubleBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public FloatOption mapToFloat(
        final ToFloatFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> FloatOption mapToFloat(
        final ToFloatBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public IntOption mapToInt(
        final ToIntFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> IntOption mapToInt(
        final ToIntBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public LongOption mapToLong(
        final ToLongFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> LongOption mapToLong(
        final ToLongBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public ShortOption mapToShort(
        final ToShortFunction<? super T> mapper)
    {
        return noneUnchecked();
    }

    @Override
    public <A> ShortOption mapToShort(
        final ToShortBiFunction<? super A, ? super T> mapper,
        final A arg)
    {
        return noneUnchecked();
    }

    @Override
    public boolean matches(
        final Predicate<? super T> predicate)
    {
        return false;
    }

    @Override
    public <A> boolean matches(
        final BiPredicate<? super A, ? super T> predicate,
        final A arg)
    {
        return false;
    }

    @Override
    public Option<T> or(
        final Option<? extends T> option)
    {
        @SuppressWarnings("unchecked")
        final Option<T> o = (Option<T>) option;
        return o;
    }

    @Override
    public T orElse(
        final T value)
    {
        return value;
    }

    @Override
    public T orElseGet(
        final Supplier<? extends T> supplier)
    {
        return supplier.get();
    }

    @Override
    public <A> T orElseGet(
        final Function<? super A, ? extends T> supplier,
        final A arg)
    {
        return supplier.apply(arg);
    }

    @Override
    public T orElseThrow()
    {
        throw Exceptions.optionNoValue();
    }

    @Override
    public <X extends Throwable> T orElseThrow(
        final Supplier<? extends X> supplier)
        throws X
    {
        throw supplier.get();
    }

    @Override
    public <A, X extends Throwable> T orElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
        throws X
    {
        throw supplier.apply(arg);
    }

    @Override
    public Option<T> orGet(
        final Supplier<? extends Option<? extends T>> supplier)
    {
        @SuppressWarnings("unchecked")
        final Option<T> o = (Option<T>) supplier.get();
        return (o);
    }

    @Override
    public <A> Option<T> orGet(
        final Function<? super A, ? extends Option<? extends T>> supplier,
        final A arg)
    {
        @SuppressWarnings("unchecked")
        final Option<T> o = (Option<T>) supplier.apply(arg);
        return (o);
    }

    @Override
    public Stream<T> stream()
    {
        return Stream.empty();
    }

    @Override
    public Optional<T> toOptional()
    {
        return Optional.empty();
    }

    @Override
    public String toString()
    {
        return "Option.none";
    }

    private Object readResolve()
        throws ObjectStreamException
    {
        return INSTANCE;
    }
}