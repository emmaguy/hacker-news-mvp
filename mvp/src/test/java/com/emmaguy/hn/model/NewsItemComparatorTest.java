package com.emmaguy.hn.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemComparatorTest {

    @Test
    public void test_item1WithScoreLowerThanItem2_returns1() {
        NewsItemComparator comparator = new NewsItemComparator();

        NewsItem item1 = new NewsItem();
        item1.setScore(10);

        NewsItem item2 = new NewsItem();
        item2.setScore(20);

        assertThat(comparator.compare(item1, item2), equalTo(1));
    }

    @Test
    public void test_item2WithScoreLowerThanItem1_returnsNegative1() {
        NewsItemComparator comparator = new NewsItemComparator();

        NewsItem item1 = new NewsItem();
        item1.setScore(20);

        NewsItem item2 = new NewsItem();
        item2.setScore(10);

        assertThat(comparator.compare(item1, item2), equalTo(-1));
    }

    @Test
    public void test_item1WithScoreEqualToThanItem2AndNoComments_returns0() {
        NewsItemComparator comparator = new NewsItemComparator();

        NewsItem item1 = new NewsItem();
        item1.setScore(10);

        NewsItem item2 = new NewsItem();
        item2.setScore(10);

        assertThat(comparator.compare(item1, item2), equalTo(0));
    }

    @Test
    public void test_itemsWithEqualScoresButItem2WithMoreComments_returns1() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("blah");

        NewsItemComparator comparator = new NewsItemComparator();

        NewsItem item1 = new NewsItem();
        item1.setScore(10);

        NewsItem item2 = new NewsItem();
        item2.setScore(10);
        item2.setRootCommentIds(ids);

        assertThat(comparator.compare(item1, item2), equalTo(1));
    }

    @Test
    public void test_itemsWithEqualScoresButItem1WithMoreComments_returnsNegative1() {
        ArrayList<String> ids = new ArrayList<>();
        ids.add("blah");

        NewsItemComparator comparator = new NewsItemComparator();

        NewsItem item1 = new NewsItem();
        item1.setScore(10);
        item1.setRootCommentIds(ids);

        NewsItem item2 = new NewsItem();
        item2.setScore(10);

        assertThat(comparator.compare(item1, item2), equalTo(-1));
    }
}
