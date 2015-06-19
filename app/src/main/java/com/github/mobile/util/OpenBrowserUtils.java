package com.github.mobile.util;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;

/**
 * Created by jaellysbales on 6/19/15.
 * Utilities for opening file in browser.
 */
public class OpenBrowserUtils {

    /**
     * Create intent with file URL
     *
     * @param url
     * @return intent
     */
    public static Intent create(final String url) {
        String fileUrl = url;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    /**
     * Get URL from intent
     *
     * @param intent
     * @return url
     */
    public static String getBody(final Intent intent) {
        return intent != null ? intent.getStringExtra(Intent.ACTION_VIEW) : null;
    }
}
