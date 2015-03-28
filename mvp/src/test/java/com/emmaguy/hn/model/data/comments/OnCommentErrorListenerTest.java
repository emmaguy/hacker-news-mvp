package com.emmaguy.hn.model.data.comments;

import com.emmaguy.hn.model.data.Events;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by emma on 21/03/15.
 */
public class OnCommentErrorListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnCommentErrorListener mErrorListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mErrorListener = new OnCommentErrorListener(mMockNetworkBus);
    }

    @Test
    public void test_errorListener_postsRequestFailedOnEventBus() {
        mErrorListener.call(new Throwable("blah"));

        verify(mMockNetworkBus, times(1)).post(any(Events.CommentRequestFailedEvent.class));
        verifyNoMoreInteractions(mMockNetworkBus);
    }
}
