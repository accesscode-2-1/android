package com.github.mobile;

import com.github.mobile.ui.WebView;

/**
 * Created by c4q-jorgereina on 6/19/15.
 */
public class ProfileWebEditor {


    public ProfileWebEditor() {
        String url = "https://github.com/settings/profile";
        WebView view = this.findById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);

    }
}
