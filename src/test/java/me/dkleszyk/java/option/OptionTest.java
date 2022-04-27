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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import me.dkleszyk.java.function.extra.primitive.*;
import org.junit.jupiter.api.Test;

import static me.dkleszyk.java.option.Streams.asIterable;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for {@link Option}.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
public class OptionTest
{
    private static final byte B0 = 0;

    private static final byte B1 = 1;

    private static final char C0 = 0;

    private static final char C1 = 1;

    private static final double D0 = 0;

    private static final double D1 = 1;

    private static final float F0 = 0;

    private static final float F1 = 1;

    private static final int I0 = 0;

    private static final int I1 = 1;

    private static final long J0 = 0;

    private static final long J1 = 1;

    private static final Object L0 = null;

    private static final Object L1 = new Object();

    private static final ByteSupplier M_B0 = () -> B0;

    private static final ByteSupplier M_B1 = () -> B1;

    private static final ByteBinaryOperator M_BXY_TO_BX = (x, y) -> x;

    private static final ByteBinaryOperator M_BXY_TO_BY = (x, y) -> y;

    private static final ByteUnaryOperator M_B_TO_B = (x) -> x;

    private static final CharSupplier M_C0 = () -> C0;

    private static final CharSupplier M_C1 = () -> C1;

    private static final CharBinaryOperator M_CXY_TO_CX = (x, y) -> x;

    private static final CharBinaryOperator M_CXY_TO_CY = (x, y) -> y;

    private static final CharUnaryOperator M_C_TO_C = (x) -> x;

    private static final DoubleSupplier M_D0 = () -> D0;

    private static final DoubleSupplier M_D1 = () -> D1;

    private static final DoubleBinaryOperator M_DXY_TO_DX = (x, y) -> x;

    private static final DoubleBinaryOperator M_DXY_TO_DY = (x, y) -> y;

    private static final DoubleUnaryOperator M_D_TO_D = (x) -> x;

    private static final FloatSupplier M_F0 = () -> F0;

    private static final FloatSupplier M_F1 = () -> F1;

    private static final FloatBinaryOperator M_FXY_TO_FX = (x, y) -> x;

    private static final FloatBinaryOperator M_FXY_TO_FY = (x, y) -> y;

    private static final FloatUnaryOperator M_F_TO_F = (x) -> x;

    private static final IntSupplier M_I0 = () -> I0;

    private static final IntSupplier M_I1 = () -> I1;

    private static final IntBinaryOperator M_IXY_TO_IX = (x, y) -> x;

    private static final IntBinaryOperator M_IXY_TO_IY = (x, y) -> y;

    private static final IntUnaryOperator M_I_TO_I = (x) -> x;

    private static final LongSupplier M_J0 = () -> J0;

    private static final LongSupplier M_J1 = () -> J1;

    private static final LongBinaryOperator M_JXY_TO_JX = (x, y) -> x;

    private static final LongBinaryOperator M_JXY_TO_JY = (x, y) -> y;

    private static final LongUnaryOperator M_J_TO_J = (x) -> x;

    private static final Supplier<Object> M_L0 = () -> L0;

    private static final Supplier<Object> M_L1 = () -> L1;

    private static final BiFunction<Object, Object, Object> M_LXY_TO_LX =
        (x, y) -> x;

    private static final BiFunction<Object, Object, Object> M_LXY_TO_LY =
        (x, y) -> y;

    private static final ToByteFunction<Object> M_L_TO_B = (x) -> (byte) x;

    private static final ToCharFunction<Object> M_L_TO_C = (x) -> (char) x;

    private static final ToDoubleFunction<Object> M_L_TO_D = (x) -> (double) x;

    private static final ToFloatFunction<Object> M_L_TO_F = (x) -> (float) x;

    private static final ToIntFunction<Object> M_L_TO_I = (x) -> (int) x;

    private static final ToLongFunction<Object> M_L_TO_J = (x) -> (long) x;

    private static final Function<Object, Object> M_L_TO_L = (x) -> x;

    private static final ToShortFunction<Object> M_L_TO_S = (x) -> (short) x;

    private static final Predicate<Object> M_L_TO_Z = (x) -> (boolean) x;

    private static final ShortBinaryOperator M_SXY_TO_SX = (x, y) -> x;

    private static final ShortBinaryOperator M_SXY_TO_SY = (x, y) -> y;

    private static final ShortUnaryOperator M_S_TO_S = (x) -> x;

    private static final BooleanBinaryOperator M_ZXY_TO_ZX = (x, y) -> x;

    private static final BooleanBinaryOperator M_ZXY_TO_ZY = (x, y) -> y;

    private static final BooleanUnaryOperator M_Z_TO_Z = (x) -> x;

    private static final short S0 = 0;

    private static final ShortSupplier M_S0 = () -> S0;

    private static final short S1 = 1;

    private static final ShortSupplier M_S1 = () -> S1;

    private static final boolean Z0 = false;

    private static final BooleanSupplier M_Z0 = () -> Z0;

    private static final boolean Z1 = true;

    private static final BooleanSupplier M_Z1 = () -> Z1;

    private static <T> T emptyOptional(
        final Class<T> optionalType)
    {
        final Class<?> type = optionalType;

        if (type == Optional.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Optional.empty();
            return t;
        }
        else if (type == OptionalDouble.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) OptionalDouble.empty();
            return t;
        }
        else if (type == OptionalInt.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) OptionalInt.empty();
            return t;
        }
        else if (type == OptionalLong.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) OptionalLong.empty();
            return t;
        }
        else
        {
            return fail(
                () -> String.format(
                    "Unsupported optional type: %s",
                    type));
        }
    }

    private static <T> T emptyStream(
        final Class<T> streamType)
    {
        final Class<?> type = streamType;

        if (type == Stream.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Stream.empty();
            return t;
        }
        else if (type == DoubleStream.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) DoubleStream.empty();
            return t;
        }
        else if (type == IntStream.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) IntStream.empty();
            return t;
        }
        else if (type == LongStream.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) LongStream.empty();
            return t;
        }
        else
        {
            return fail(
                () -> String.format(
                    "Unsupported stream type: %s",
                    type));
        }
    }

    private static <T> T some0(
        final Class<T> optionType)
    {
        final Class<?> type = optionType;

        if (type == Option.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.someNullable(L0);
            return t;
        }
        else if (type == BooleanOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(Z0);
            return t;
        }
        else if (type == ByteOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(B0);
            return t;
        }
        else if (type == CharOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(C0);
            return t;
        }
        else if (type == DoubleOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(D0);
            return t;
        }
        else if (type == FloatOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(F0);
            return t;
        }
        else if (type == IntOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(I0);
            return t;
        }
        else if (type == LongOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(J0);
            return t;
        }
        else if (type == ShortOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(S0);
            return t;
        }
        else
        {
            return fail(
                () -> String.format(
                    "Unsupported option type: %s",
                    type));
        }
    }

    private static <T> T some1(
        final Class<T> optionType)
    {
        final Class<?> type = optionType;

        if (type == Option.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.someNullable(L1);
            return t;
        }
        else if (type == BooleanOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(Z1);
            return t;
        }
        else if (type == ByteOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(B1);
            return t;
        }
        else if (type == CharOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(C1);
            return t;
        }
        else if (type == DoubleOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(D1);
            return t;
        }
        else if (type == FloatOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(F1);
            return t;
        }
        else if (type == IntOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(I1);
            return t;
        }
        else if (type == LongOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(J1);
            return t;
        }
        else if (type == ShortOption.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) Option.some(S1);
            return t;
        }
        else
        {
            return fail(
                () -> String.format(
                    "Unsupported option type: %s",
                    type));
        }
    }

    private static <T> T val0(
        final Class<T> valueType)
    {
        final Class<?> type = valueType;

        if (type == Object.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) L0;
            return t;
        }
        else if (type == boolean.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) Z0;
            return t;
        }
        else if (type == byte.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) B0;
            return t;
        }
        else if (type == char.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) C0;
            return t;
        }
        else if (type == double.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) D0;
            return t;
        }
        else if (type == float.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) F0;
            return t;
        }
        else if (type == int.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) I0;
            return t;
        }
        else if (type == long.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) J0;
            return t;
        }
        else if (type == short.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) S0;
            return t;
        }
        else
        {
            return null;
        }
    }

    private static <T> T val1(
        final Class<T> valueType)
    {
        final Class<?> type = valueType;

        if (type == Object.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) L1;
            return t;
        }
        else if (type == boolean.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) Z1;
            return t;
        }
        else if (type == byte.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) B1;
            return t;
        }
        else if (type == char.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) C1;
            return t;
        }
        else if (type == double.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) D1;
            return t;
        }
        else if (type == float.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) F1;
            return t;
        }
        else if (type == int.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) I1;
            return t;
        }
        else if (type == long.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) J1;
            return t;
        }
        else if (type == short.class)
        {
            @SuppressWarnings("unchecked")
            final T t = (T) (Object) S1;
            return t;
        }
        else
        {
            return null;
        }
    }

    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    void none()
    {
        final Option<?> none = Option.none();

        final Class<?>[] opts =
        {
            Option.class, BooleanOption.class, ByteOption.class,
            CharOption.class, DoubleOption.class, FloatOption.class,
            IntOption.class, LongOption.class, ShortOption.class
        };

        for (final Class<?> opt : opts)
        {
            assertTrue(
                opt.isInstance(none),
                () -> String.format(
                    "none should implement %s",
                    opt));

            for (final Method mthd : opt.getDeclaredMethods())
            {
                final int mod = mthd.getModifiers();

                if (Modifier.isStatic(mod) || !Modifier.isPublic(mod))
                {
                    continue;
                }

                final String name = mthd.getName();
                final Class<?> ret = mthd.getReturnType();
                final Parameter[] ps = mthd.getParameters();
                final int argc = ps.length;
                final Object[] argv = new Object[argc];

                for (int i = 0; i < argc; i++)
                {
                    argv[i] = val0(ps[i].getType());
                }

                final MethodInvoker mi = new MethodInvoker(mthd, none, argv);

                if (argc == 0)
                {
                    if (name.startsWith("get") || name.endsWith("ElseThrow"))
                    {
                        assertThrows(
                            NoSuchElementException.class,
                            mi,
                            () -> String.format(
                                "none.%s should throw %s",
                                name,
                                NoSuchElementException.class));
                        continue;
                    }
                    else if (name.startsWith("toOptional"))
                    {
                        assertSame(
                            emptyOptional(ret),
                            mi.resultOrThrow(),
                            () -> String.format(
                                "none.%s should return an empty optional",
                                name));
                        continue;
                    }
                    else if (name.equals("stream") || name.endsWith("Stream"))
                    {
                        assertIterableEquals(
                            asIterable((BaseStream<?, ?>) emptyStream(ret)),
                            asIterable((BaseStream<?, ?>) mi.resultOrThrow()),
                            () -> String.format(
                                "none.%s should return an empty stream",
                                name));
                        continue;
                    }
                }
                else if (name.startsWith("andGet"))
                {
                    if (name.equals("andGet"))
                    {
                        if (argc == 1)
                        {
                            assertSame(
                                none,
                                mi.resultOrThrow(),
                                () -> String.format(
                                    "none.%s should return none",
                                    name));
                            continue;
                        }
                        else if (argc == 2)
                        {
                            assertSame(
                                none,
                                mi.resultOrThrow(),
                                () -> String.format(
                                    "none.%s should return none",
                                    name));
                            continue;
                        }
                    }
                    else
                    {
                        if (argc == 1)
                        {
                            assertSame(
                                none,
                                mi.resultOrThrow(),
                                () -> String.format(
                                    "none.%s should return none",
                                    name));
                            continue;
                        }
                        else if (argc == 2)
                        {
                            assertSame(
                                none,
                                mi.resultOrThrow(),
                                () -> String.format(
                                    "none.%s should return none",
                                    name));
                            continue;
                        }
                    }
                }
                else if (name.equals("and"))
                {
                    if (argc == 1)
                    {
                        assertSame(
                            none,
                            mi.resultOrThrow(),
                            () -> String.format(
                                "none.%s should return none",
                                name));
                        continue;
                    }
                }
                /*
                 * fail( () -> String.format( "Untested method: %s", mthd));
                 */
            }
        }
    }
}
