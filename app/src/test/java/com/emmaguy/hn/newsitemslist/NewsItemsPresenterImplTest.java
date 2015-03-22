package com.emmaguy.hn.newsitemslist;

import com.emmaguy.hn.model.NewsItemsRequestFailedEvent;
import com.emmaguy.hn.model.HackerNewsDataSource;
import com.emmaguy.hn.model.NewsItem;
import com.squareup.otto.Bus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenterImplTest {
    @Mock
    private Bus mMockNetworkBus;

    @Mock
    private NewsItemsView mMockView;

    @Mock
    private HackerNewsDataSource mMockDataSource;

    private NewsItemsPresenterImpl mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NewsItemsPresenterImpl(mMockView, mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void test_onStart_registersNetworkBus() {
        mPresenter.onStart();

        verify(mMockNetworkBus, times(1)).register(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void test_onStop_unregistersNetworkBus() {
        mPresenter.onStop();

        verify(mMockNetworkBus, times(1)).unregister(mPresenter);
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void test_onStartWithEmptyList_showsLoadingIndicator() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart();

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithEmptyList_hidesError() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart();

        verify(mMockView, times(1)).hideError();
    }

    @Test
    public void test_onStartWithEmptyList_retrievesLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart();

        verify(mMockDataSource, times(1)).getLatestNewsItems();
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void test_onStartWithNonEmptyList_doesNotShowLoadingIndicator() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart();

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithNonEmptyList_doesNotRetrieveLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart();

        verify(mMockDataSource, times(0)).getLatestNewsItems();
    }

    @Test
    public void test_newsItemsReceived_hidesLoadingIndicator() {
        mPresenter.onNewsItemsReceived(new ArrayList<NewsItem>());

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void test_newsItemsReceived_showNewsItems() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        mPresenter.onNewsItemsReceived(newsItems);

        verify(mMockView, times(1)).showNewsItems(newsItems);
    }

    @Test
    public void test_newsItemsFailedToRetrieve_showError() {
        mPresenter.onRetrieveNewsItemsFailed(new NewsItemsRequestFailedEvent());

        verify(mMockView, times(1)).showError();
    }

    @Test
    public void test_newsItemsFailedToRetrieve_hidesLoadingIndicator() {
        mPresenter.onRetrieveNewsItemsFailed(new NewsItemsRequestFailedEvent());

        verify(mMockView, times(1)).hideLoadingIndicator();
    }
}
