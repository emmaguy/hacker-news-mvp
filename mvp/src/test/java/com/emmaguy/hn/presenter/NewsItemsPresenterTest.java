package com.emmaguy.hn.presenter;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.NewsItemEvents;
import com.emmaguy.hn.view.NewsItemsView;

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
    @Mock private RxBus mMockNetworkBus;
    @Mock private NewsItemsView mMockView;
    @Mock private NewsDataSource mMockDataSource;

    private NewsItemsPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NewsItemsPresenter(mMockDataSource, mMockNetworkBus);
    }

    @Test
    public void onStart_registersNetworkBus() {
        mPresenter.onStart(mMockView);

        verify(mMockNetworkBus, times(1)).toObservable();
        verifyNoMoreInteractions(mMockNetworkBus);
    }

    @Test
    public void onStartWithEmptyList_showsLoadingIndicator() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void onStartWithEmptyList_hidesError() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(1)).hideError();
    }

    @Test
    public void onStartWithEmptyList_retrievesLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(true);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(1)).getLatestNewsItems(mMockNetworkBus);
        verifyNoMoreInteractions(mMockDataSource);
    }

    @Test
    public void onStartWithNonEmptyList_doesNotShowLoadingIndicator() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void onStartWithNonEmptyList_doesNotRetrieveLatestNewsItems() {
        when(mMockView.isEmptyList()).thenReturn(false);

        mPresenter.onStart(mMockView);

        verify(mMockDataSource, times(0)).getLatestNewsItems(mMockNetworkBus);
    }

    @Test
    public void newsItemsReceived_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
//        mPresenter.onNewsItemsReceived(new NewsItemEvents.RequestSucceededEvent(new ArrayList<NewsItem>()));

        verify(mMockView, times(1)).hideLoadingIndicator();
    }

    @Test
    public void newsItemsReceived_showNewsItems() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        mPresenter.onStart(mMockView);
//        mPresenter.onNewsItemsReceived(new NewsItemEvents.RequestSucceededEvent(newsItems));

        verify(mMockView, times(1)).showNewsItems(newsItems);
    }

    @Test
    public void newsItemsFailedToRetrieve_showError() {
        mPresenter.onStart(mMockView);
//        mPresenter.onRetrieveNewsItemsFailed(new NewsItemEvents.RequestFailedEvent());

        verify(mMockView, times(1)).showError();
    }

    @Test
    public void newsItemsFailedToRetrieve_hidesLoadingIndicator() {
        mPresenter.onStart(mMockView);
//        mPresenter.onRetrieveNewsItemsFailed(new NewsItemEvents.RequestFailedEvent());

        verify(mMockView, times(1)).hideLoadingIndicator();
    }
}
