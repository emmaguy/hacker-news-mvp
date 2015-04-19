package com.emmaguy.hn.presenter;

import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.Events;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.view.NewsItemsView;
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
public class NewsItemsPresenterTest {
    @Mock
    private Bus mMockNetworkBus;

    @Mock
    private NewsItemsView mMockView;

    @Mock
    private NewsDataSource mMockDataSource;

    private NewsItemsPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NewsItemsPresenter(mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void test_onStart_registersNetworkBus() {
        mPresenter.onStart(mMockView);

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

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithEmptyList_hidesError() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).hideError();
    }

    @Test
    public void test_onStartWithEmptyList_retrievesLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(1)).getLatestNewsItems();
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void test_onStartWithNonEmptyList_doesNotShowLoadingIndicator() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void test_onStartWithNonEmptyList_doesNotRetrieveLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(0)).getLatestNewsItems();
    }

    @Test
    public void test_newsItemsReceived_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
        mPresenter.onNewsItemsReceived(new Events.NewsItemsSuccessEvent(new ArrayList<NewsItem>()));

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void test_newsItemsReceived_showNewsItems() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        mPresenter.onStart(mMockView);
        mPresenter.onNewsItemsReceived(new Events.NewsItemsSuccessEvent(newsItems));

        verify(mMockView, times(1)).showNewsItems(newsItems);
    }

    @Test
    public void test_newsItemsFailedToRetrieve_showError() {
        mPresenter.onStart(mMockView);
        mPresenter.onRetrieveNewsItemsFailed(new Events.NewsItemsRequestFailedEvent());

        verify(mMockView, times(1)).showError();
    }

    @Test
    public void test_newsItemsFailedToRetrieve_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
        mPresenter.onRetrieveNewsItemsFailed(new Events.NewsItemsRequestFailedEvent());

        verify(mMockView, times(1)).hideLoadingIndicator();
    }
}
