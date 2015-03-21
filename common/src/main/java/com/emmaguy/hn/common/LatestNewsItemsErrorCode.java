package com.emmaguy.hn.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by emma on 21/03/15.
 */
@IntDef({Events.LATEST_NEWS_ITEMS_REQUEST_FAILED})
@Retention(RetentionPolicy.SOURCE)
public @interface LatestNewsItemsErrorCode {
}
