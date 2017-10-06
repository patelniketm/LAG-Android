package org.esyz.lagc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * Created by Sonu on 12-02-2017.
 */

public class AboutActivity extends Activity {
    WebView webView;
    ImageView imgAboutBack;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
   /*     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdView adView = (AdView) this.findViewById(R.id.adViewAboutUs);
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);
*/
        imgAboutBack = (ImageView) findViewById(R.id.imgAboutBack);
        webView = (WebView) findViewById(R.id.weblorenApsum);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/lorem.html");

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.contains("message2space.es.vu")) {
                    view.loadUrl(url);
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
                return true;
            }
        });
/*
        if (Utils.isTablet(AboutActivity.this)) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setTextSize(WebSettings.TextSize.LARGER);
        }
*/

        imgAboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
