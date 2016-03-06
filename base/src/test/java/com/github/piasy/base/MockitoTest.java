/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.base;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/15.
 */
public class MockitoTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testOnly() {
        // given
        final List mock = mock(List.class);

        // when
        mock.add(1);
        //mock.add(1);      // another add(1) will cause fail
        //mock.remove(1);   // other method call will also cause fail

        // then
        then(mock).should(only()).add(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testArgCap() {
        // given
        final List mock = mock(List.class);
        final ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        // when
        mock.add(1);
        mock.add(2);

        // then
        then(mock).should(times(2)).add(captor.capture());
        Assert.assertTrue(captor.getAllValues().size() == 2);
        Assert.assertEquals(1, captor.getAllValues().get(0).intValue());
        Assert.assertEquals(2, captor.getAllValues().get(1).intValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testArgCapList() {
        // given
        final TestInterface mock = mock(TestInterface.class);
        final ArgumentCaptor<List<Integer>> captor = ArgumentCaptor.forClass(List.class);
        final List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);

        // when
        mock.call(list1);
        list1.set(1, 3);
        mock.call(list1);

        // then
        then(mock).should(times(2)).call(captor.capture());
        Assert.assertTrue(captor.getAllValues().size() == 2);
        /*Assert.assertEquals(1, captor.getAllValues().get(0).get(0).intValue());
        Assert.assertEquals(2, captor.getAllValues().get(0).get(1).intValue());
        Assert.assertEquals(1, captor.getAllValues().get(1).get(0).intValue());
        Assert.assertEquals(3, captor.getAllValues().get(1).get(1).intValue());*/
        // above assert will fail, because ArgCaptor record args reference, rather then value, in
        // implementation
        // it'll be a better design to pass immutable params during method call
        // e.g.
        /*List<Integer> list3 = new ArrayList<>();
        list3.add(3);
        list3.add(4);
        mock.call(list3);
        List<Integer> list4 = new ArrayList<>();
        list4.add(5);
        list4.add(6);
        mock.call(list4);*/
    }

    private interface TestInterface {
        void call(List<Integer> list);
    }
}
