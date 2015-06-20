package com.emmaguy.hn.common;

import android.content.Intent;

/**
 * Created by emma on 26/04/15.
 */
public class PlainTextShareIntentBuilder {
    public Intent build(String url) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, url);
        return Intent.createChooser(i, null);
    }
}
