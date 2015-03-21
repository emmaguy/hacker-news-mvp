package com.emmaguy.hn.newsitemslist;

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
import static org.mockito.Mockito.when;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenterTest {
    @Mock
    private Bus mMockUiBus;

    @Mock
    private NewsItemsView mMockView;

    @Mock
    private HackerNewsDataSource mMockDataSource;

    private NewsItemsPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new NewsItemsPresenter(mMockView, mMockDataSource, mMockUiBus);
    }

    @Test
    public void test_listIsEmpty_showsLoadingIndicator() {
        when(mMockView.listIsEmpty()).thenReturn(true);

        mPresenter.onStart();

        verify(mMockView, times(1)).showLoadingIndicator();
    }

    @Test
    public void test_listIsEmpty_doesNotShowLoadingIndicator() {
        when(mMockView.listIsEmpty()).thenReturn(false);

        mPresenter.onStart();

        verify(mMockView, times(0)).showLoadingIndicator();
    }

    @Test
    public void test_whenNewsItemsReceived_shouldHideLoadingIndicatorAndShowNewsItems() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();

        mPresenter.onNewsItemsReceived(newsItems);

        verify(mMockView, times(1)).hideLoadingIndicator();
        verify(mMockView, times(1)).showNewsItems(newsItems);
    }
}
