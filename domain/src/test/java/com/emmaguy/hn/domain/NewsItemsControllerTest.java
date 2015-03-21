package com.emmaguy.hn.domain;

import com.emmaguy.hn.model.HackerNewsDataSource;
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
public class NewsItemsControllerTest {
    @Mock
    private HackerNewsDataSource mMockDataSource;

    @Mock
    private Bus mMockUiBus;

    private NewsItemsReceiver mNewsItemsReceiver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNewsItemsReceiver = new NewsItemsController(mMockDataSource, mMockUiBus);
    }

    @Test
    public void test_latestStoryList_isExecuted() {
        mNewsItemsReceiver.execute();

        verify(mMockDataSource, times(1)).getLatestNewsItems();
    }
}
