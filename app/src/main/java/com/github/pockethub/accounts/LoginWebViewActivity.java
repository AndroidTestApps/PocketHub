package com.github.pockethub.accounts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebViewClient;

import com.github.pockethub.R;
import com.github.pockethub.ui.LightProgressDialog;
import com.github.pockethub.ui.WebView;

public class LoginWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.loadUrl(getIntent().getStringExtra(LoginActivity.INTENT_EXTRA_URL));
        webView.setWebViewClient(new WebViewClient() {
            LightProgressDialog dialog = (LightProgressDialog) LightProgressDialog.create(
                    LoginWebViewActivity.this, R.string.loading);

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
                dialog.show();
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                dialog.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals(getString(R.string.github_oauth_scheme))) {
                    Intent data = new Intent();
                    data.setData(uri);
                    setResult(RESULT_OK, data);
                    finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        setContentView(webView);
    }
}