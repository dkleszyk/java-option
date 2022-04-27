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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.function.Executable;

import static me.dkleszyk.java.option.Throw.sneaky;

/**
 * An {@link Executable} implementation that allows retrieval of its result or
 * error.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class MethodInvoker
    implements Executable
{
    private static final Object[] EMPTY_ARGS = new Object[0];

    private static final Object ERROR = new Object();

    private static final Object NOT_EXECUTED = new Object();

    private static final Object VOID = new Object();

    private final Object[] args;

    private Throwable error;

    private final Method method;

    private volatile Object result;

    private final Object target;

    public MethodInvoker(
        final Method method,
        final Object target)
    {
        this(method, target, EMPTY_ARGS);
    }

    public MethodInvoker(
        final Method method,
        final Object target,
        final Object... args)
    {
        this.method = method;
        this.target = target;
        this.args = args != null ?
            args :
            EMPTY_ARGS;
        this.result = NOT_EXECUTED;
    }

    private static RuntimeException voidResultError()
    {
        return new ClassCastException(
            String.format(
                "%s cannot be cast to %s",
                void.class,
                Object.class));
    }

    @Override
    public void execute()
    {
        final Object r = invokeOnce();

        if (r == ERROR)
        {
            throw sneaky(error);
        }
    }

    public boolean hasError()
    {
        return result == ERROR;
    }

    public boolean hasResult()
    {
        final Object r = result;
        return r != NOT_EXECUTED && r != ERROR && r != VOID;
    }

    public Object resultOrThrow()
    {
        final Object r = invokeOnce();

        if (r == ERROR)
        {
            throw sneaky(error);
        }

        if (r == VOID)
        {
            throw voidResultError();
        }

        return r;
    }

    public boolean wasExecuted()
    {
        return result != NOT_EXECUTED;
    }

    private Object invoke()
    {
        final Method m = method;
        m.setAccessible(true);

        final Object r;
        try
        {
            r = m.invoke(target, args);
        }
        catch (IllegalAccessException ex)
        {
            throw new Error(ex);
        }
        catch (InvocationTargetException ex)
        {
            error = ex.getCause();
            return ERROR;
        }

        if (m.getReturnType() == void.class)
        {
            return VOID;
        }

        return r;
    }

    private Object invokeOnce()
    {
        Object r = result;

        if (r == NOT_EXECUTED)
        {
            r = invoke();
            result = r;
        }

        return r;
    }
}
