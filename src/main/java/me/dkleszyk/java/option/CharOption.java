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

import java.util.NoSuchElementException;
import java.util.function.*;
import me.dkleszyk.java.function.extra.primitive.*;

/**
 * A {@code char} value that may or may not be present.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
public interface CharOption
    extends Option<Character>
{
    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the specified value.
     *
     * @param value The value to return if this instance is empty.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, {@code value}.
     */
    char charOrElse(
        final char value);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the value supplier by the specified method.
     *
     * @param supplier A method that supplies the value to return if this
     *                 instance is empty.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, the value supplied by {@code supplier}.
     */
    char charOrElseGet(
        final CharSupplier supplier);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, returns the value supplier by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param supplier A method that supplies the value to return if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return The value contained by this instance if it is non-empty;
     *         otherwise, the value supplied by {@code supplier}.
     */
    <A> char charOrElseGet(
        final ToCharFunction<? super A> supplier,
        final A arg);

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws NoSuchElementException This instance is empty.
     */
    char charOrElseThrow()
        throws NoSuchElementException;

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception supplied by the specified method.
     *
     * @param <X>      The type of the exception thrown if this instance is
     *                 empty.
     * @param supplier A method that supplies the exception to throw if this
     *                 instance is empty.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws X This instance is empty.
     */
    <X extends Throwable> char charOrElseThrow(
        final Supplier<? extends X> supplier)
        throws X;

    /**
     * Returns the value contained by this instance if it is non-empty;
     * otherwise, throws an exception supplied by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param <X>      The type of the exception thrown if this instance is
     *                 empty.
     * @param supplier A method that supplies the exception to throw if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return The value contained by this instance if it is non-empty.
     *
     * @throws X This instance is empty.
     */
    <A, X extends Throwable> char charOrElseThrow(
        final Function<? super A, ? extends X> supplier,
        final A arg)
        throws X;

    @Override
    CharOption filter(
        final Predicate<? super Character> predicate);

    @Override
    <A> CharOption filter(
        final BiPredicate<? super A, ? super Character> predicate,
        final A arg);

    /**
     * Returns this instance if it is non-empty and the specified predicate
     * returns {@code true} for its contained value; otherwise, returns an empty
     * option.
     *
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     *
     * @return This instance if it is non-empty and {@code predicate} returns
     *         {@code true} for its contained value; otherwise, an empty option.
     */
    CharOption filterChar(
        final CharPredicate predicate);

    @Override
    CharOption filterToObject();

    /**
     * Applies the specified mapper function to the contained value to create a
     * new option.
     *
     * @param <O>    The type of the returned option.
     * @param mapper A function that creates a new option from the value
     *               contained by this instance.
     *
     * @return The result of applying the mapper function if this instance is
     *         non-empty; otherwise, an empty option.
     */
    <O extends Option<?>> O flatMapChar(
        final CharFunction<? extends O> mapper);

    /**
     * Returns the value contained by this instance.
     *
     * @return The value contained by this instance.
     *
     * @throws NoSuchElementException This instance is empty.
     */
    char getAsChar()
        throws NoSuchElementException;

    /**
     * Executes the specified method if this instance is non-empty.
     *
     * @param ifSome A method to execute if this instance is non-empty.
     */
    void ifSomeChar(
        final CharConsumer ifSome);

    /**
     * Executes the specified method if this instance is non-empty.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code ifSome}.
     * @param ifSome A method to execute if this instance is non-empty.
     * @param arg    An additional argument to provide to {@code ifSome}.
     */
    <A> void ifSomeChar(
        final ObjCharConsumer<? super A> ifSome,
        final A arg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param ifSome A method to execute if this instance is non-empty.
     * @param ifNone A method to execute if this instance is empty.
     */
    void ifSomeCharOrElse(
        final CharConsumer ifSome,
        final Runnable ifNone);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifNone}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifNone    A method to execute if this instance is empty.
     * @param ifNoneArg An additional argument to provide to {@code ifNone}.
     */
    <A> void ifSomeCharOrElse(
        final CharConsumer ifSome,
        final Consumer<? super A> ifNone,
        final A ifNoneArg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifSome}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifSomeArg An additional argument to provide to {@code ifSome}.
     * @param ifNone    A method to execute if this instance is empty.
     */
    <A> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Runnable ifNone);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>    The type of the additional argument provided to
     *               {@code ifSome} and {@code ifNone}.
     * @param ifSome A method to execute if this instance is non-empty.
     * @param ifNone A method to execute if this instance is empty.
     * @param arg    An additional argument to provide to {@code ifSome} and
     *               {@code ifNone}.
     */
    <A> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final Consumer<? super A> ifNone,
        final A arg);

    /**
     * Executes one of the specified methods depending on whether this instance
     * is empty or non-empty.
     *
     * @param <A>       The type of the additional argument provided to
     *                  {@code ifSome}.
     * @param <B>       The type of the additional argument provided to
     *                  {@code ifNone}.
     * @param ifSome    A method to execute if this instance is non-empty.
     * @param ifSomeArg An additional argument to provide to {@code ifSome}.
     * @param ifNone    A method to execute if this instance is empty.
     * @param ifNoneArg An additional argument to provide to {@code ifNone}.
     */
    <A, B> void ifSomeCharOrElse(
        final ObjCharConsumer<? super A> ifSome,
        final A ifSomeArg,
        final Consumer<? super B> ifNone,
        final B ifNoneArg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param <T>    The value type of the returned option.
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    <T> Option<T> mapChar(
        final CharFunction<? extends T> mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    BooleanOption mapCharToBoolean(
        final CharPredicate mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    ByteOption mapCharToByte(
        final CharToByteFunction mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    CharOption mapCharToChar(
        final CharUnaryOperator mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    CharOption mapCharToChar(
        final CharBinaryOperator mapper,
        final char arg);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    DoubleOption mapCharToDouble(
        final CharToDoubleFunction mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    FloatOption mapCharToFloat(
        final CharToFloatFunction mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    IntOption mapCharToInt(
        final CharToIntFunction mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    LongOption mapCharToLong(
        final CharToLongFunction mapper);

    /**
     * Applies the specified mapper function to the contained value.
     *
     * @param mapper A function to apply to the value contained by this
     *               instance.
     *
     * @return An option containing the result of applying the mapper function
     *         if this instance is non-empty; otherwise, an empty option.
     */
    ShortOption mapCharToShort(
        final CharToShortFunction mapper);

    /**
     * Indicates whether this instance is non-empty and the specified predicate
     * returns {@code true} for its contained value.
     *
     * @param predicate A predicate to apply to the contained value if this
     *                  instance is non-empty.
     *
     * @return {@code true} if this instance is non-empty and {@code predicate}
     *         returns {@code true} for its contained value; otherwise,
     *         {@code false}.
     */
    boolean matchesChar(
        final CharPredicate predicate);

    /**
     * Returns this instance if it is non-empty; otherwise, returns the
     * specified option.
     *
     * @param option The option to return if this instance is empty.
     *
     * @return This instance if it is non-empty; otherwise, {@code option}.
     */
    CharOption or(
        final CharOption option);

    /**
     * Returns this instance if it is non-empty; otherwise, returns the option
     * supplied by the specified method.
     *
     * @param supplier A method that supplies the option to return if this
     *                 instance is empty.
     *
     * @return This instance if it is non-empty; otherwise, the option supplied
     *         by {@code supplier}.
     */
    CharOption orGetChar(
        final Supplier<? extends CharOption> supplier);

    /**
     * Returns this instance if it is non-empty; otherwise, returns the option
     * supplied by the specified method.
     *
     * @param <A>      The type of the additional argument provided to
     *                 {@code supplier}.
     * @param supplier A method that supplies the option to return if this
     *                 instance is empty.
     * @param arg      An additional argument to provide to {@code supplier}.
     *
     * @return This instance if it is non-empty; otherwise, the option supplied
     *         by {@code supplier}.
     */
    <A> CharOption orGetChar(
        final Function<? super A, ? extends CharOption> supplier,
        final A arg);
}
