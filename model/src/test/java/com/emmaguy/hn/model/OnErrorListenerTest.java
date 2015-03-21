package com.emmaguy.hn.model;

import com.emmaguy.hn.common.Events;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnErrorListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnErrorListener mErrorListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mErrorListener = new OnErrorListener(mMockNetworkBus);
    }

    @Test
    public void test_errorListener_postsRequestFailedOnEventBus() {
        mErrorListener.call(new Throwable("blah"));

        verify(mMockNetworkBus, times(1)).post(Events.LATEST_NEWS_ITEMS_REQUEST_FAILED);
    }
}
