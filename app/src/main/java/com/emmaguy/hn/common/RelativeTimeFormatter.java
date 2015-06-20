package com.emmaguy.hn.common;

import android.text.format.DateUtils;

import org.joda.time.DateTime;

/**
 * Created by emma on 26/04/15.
 */
public class RelativeTimeFormatter {
    public CharSequence format(long time) {
        return DateUtils.getRelativeTimeSpanString(time * 1000, DateTime.now().getMillis(), DateUtils.SECOND_IN_MILLIS);
    }
}
