package com.emmaguy.hn;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;

import org.joda.time.DateTime;

/**
 * Created by emma on 29/03/15.
 */
public class Utils {

    public static Intent getShareIntent(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, url);
        return Intent.createChooser(i, null);
    }

    public static CharSequence getRelativeTimeSpanString(long time) {
        return DateUtils.getRelativeTimeSpanString(time * 1000, DateTime.now().getMillis(), DateUtils.SECOND_IN_MILLIS);
    }
}
