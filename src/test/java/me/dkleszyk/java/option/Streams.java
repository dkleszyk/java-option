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

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

/**
 * Provides a helper method to wrap a stream as an iterable.
 *
 * @author David Kleszyk <dkleszyk@gmail.com>
 */
final class Streams
{
    public static <T> Iterable<T> asIterable(
        final BaseStream<T, ?> stream)
    {
        return new StreamIterable<>(stream);
    }

    private static final class StreamIterable<T>
        implements Iterable<T>
    {
        private final BaseStream<T, ?> stream;

        public StreamIterable(
            final BaseStream<T, ?> stream)
        {
            this.stream = stream;
        }

        @Override
        public Iterator<T> iterator()
        {
            return stream.iterator();
        }

        @Override
        public Spliterator<T> spliterator()
        {
            return stream.spliterator();
        }
    }
}
