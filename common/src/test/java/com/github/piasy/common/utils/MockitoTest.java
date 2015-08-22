package com.github.piasy.common.utils;

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
        List mock = mock(List.class);

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
        List mock = mock(List.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

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
        TestInterface mock = mock(TestInterface.class);
        ArgumentCaptor<List<Integer>> captor = ArgumentCaptor.forClass(List.class);
        List<Integer> list1 = new ArrayList<>();
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
