package com.emmaguy.hn.model;

import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnNextListenerTest {
    @Mock
    private Bus mMockNetworkBus;

    private OnNextListener mNextListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNextListener = new OnNextListener(mMockNetworkBus);
    }

    @Test
    public void test_getLatestNewsItems_postsNewsItemsOnEventBus() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem());

        mNextListener.call(newsItems);

        verify(mMockNetworkBus, times(1)).post(newsItems);
    }
}
