package com.piasy.common.utils;

import java.util.List;
import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/15.
 */
public class MockitoTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testOnly() {
        List mock = mock(List.class);

        // when
        mock.add(1);
        //mock.add(1);      // another add(1) will cause fail
        //mock.remove(1);   // other method call will also cause fail

        // then
        then(mock).should(only()).add(1);
    }
}
