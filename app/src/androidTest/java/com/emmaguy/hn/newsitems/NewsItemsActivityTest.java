package com.emmaguy.hn.newsitems;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.emmaguy.hn.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewsItemsActivityTest {

    @Rule
    public ActivityTestRule<NewsItemsActivity> mActivityRule = new ActivityTestRule<>(NewsItemsActivity.class);

    @Test
    public void newsItemWithNoArticle_showsCommentsWhenWebPageClickedOn() {
        //onView(withId(R.id.news_items_recyclerview_list)).perform(actionOnItemAtPosition(0, click()));

        //onView(withId(R.id.comments_progress_bar_loading)).check(matches(withText("blah")));
    }
}
