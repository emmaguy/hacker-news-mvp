package com.emmaguy.hn.model.data.newsitems;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.events.NewsItemEvents;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by emma on 21/03/15.
 */
public class OnListNewsItemNextListenerTest {
    @Mock private RxBus mMockNetworkBus;

    private OnListNewsItemNextListener mNextListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mNextListener = new OnListNewsItemNextListener(mMockNetworkBus);
    }

    @Test
    public void getLatestNewsItems_postsNewsItemsOnEventBus() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem());

        mNextListener.call(newsItems);

        verify(mMockNetworkBus, times(1)).send(any(NewsItemEvents.RequestSucceededEvent.class));
    }
}
