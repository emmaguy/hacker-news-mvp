package com.emmaguy.hn;

import android.content.Intent;

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
}
