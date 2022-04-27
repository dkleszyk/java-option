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

/**
 * Provides methods for constructing options.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class Options
{
    private Options()
    {
    }

    public static <T_OPTN extends Option<?>> T_OPTN noneUnchecked()
    {
        @SuppressWarnings("unchecked")
        final T_OPTN none = (T_OPTN) None.INSTANCE;
        return none;
    }

    public static <T> Option<T> someNonNull(
        final T value)
    {
        if (value instanceof Boolean b)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeBoolean.of(b);
            return o;
        }
        else if (value instanceof Byte b)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeByte.of(b);
            return o;
        }
        else if (value instanceof Character c)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeChar.of(c);
            return o;
        }
        else if (value instanceof Double d)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeDouble.of(d);
            return o;
        }
        else if (value instanceof Float f)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeFloat.of(f);
            return o;
        }
        else if (value instanceof Integer i)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeInt.of(i);
            return o;
        }
        else if (value instanceof Long l)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeLong.of(l);
            return o;
        }
        else if (value instanceof Short s)
        {
            @SuppressWarnings("unchecked")
            final Option<T> o = (Option<T>) SomeShort.of(s);
            return o;
        }
        else
        {
            assert value != null;
            return SomeReference.of(value);
        }
    }

    public static BooleanOption someNonNull(
        final Boolean value)
    {
        return SomeBoolean.of(value);
    }

    public static ByteOption someNonNull(
        final Byte value)
    {
        return SomeByte.of(value);
    }

    public static CharOption someNonNull(
        final Character value)
    {
        return SomeChar.of(value);
    }

    public static DoubleOption someNonNull(
        final Double value)
    {
        return SomeDouble.of(value);
    }

    public static FloatOption someNonNull(
        final Float value)
    {
        return SomeFloat.of(value);
    }

    public static IntOption someNonNull(
        final Integer value)
    {
        return SomeInt.of(value);
    }

    public static LongOption someNonNull(
        final Long value)
    {
        return SomeLong.of(value);
    }

    public static ShortOption someNonNull(
        final Short value)
    {
        return SomeShort.of(value);
    }

    public static BooleanOption someNonNull(
        final boolean value)
    {
        return SomeBoolean.of(value);
    }

    public static ByteOption someNonNull(
        final byte value)
    {
        return SomeByte.of(value);
    }

    public static CharOption someNonNull(
        final char value)
    {
        return SomeChar.of(value);
    }

    public static DoubleOption someNonNull(
        final double value)
    {
        return SomeDouble.of(value);
    }

    public static FloatOption someNonNull(
        final float value)
    {
        return SomeFloat.of(value);
    }

    public static IntOption someNonNull(
        final int value)
    {
        return SomeInt.of(value);
    }

    public static LongOption someNonNull(
        final long value)
    {
        return SomeLong.of(value);
    }

    public static ShortOption someNonNull(
        final short value)
    {
        return SomeShort.of(value);
    }

    public static <T> Option<T> someNull()
    {
        @SuppressWarnings("unchecked")
        final Option<T> someNull = (Option<T>) SomeNull.INSTANCE;
        return someNull;
    }

    public static <T> Option<T> someNullable(
        final T value)
    {
        return value == null ?
            someNull() :
            someNonNull(value);
    }
}
