package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mtechsoft.fitmy.R;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        String url = getIntent().getStringExtra("form_link");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);



        final WebView web_view  = findViewById(R.id.webview);
        web_view.setVisibility(View.GONE);



        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.getSettings().setSupportZoom(true);




        String myPdfUrl = url;
        String urlll = "https://docs.google.com/viewer?embedded=true&url="+myPdfUrl;
        web_view.loadUrl(urlll);



        web_view.setWebViewClient(new WebViewClient() {

            //once the page is loaded get the html element by class or id and through javascript hide it.
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web_view.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove();})()");
                web_view.setVisibility(View.VISIBLE);
//                web_view.loadUrl("javascript:(function() { " +
//                        "document.getElementsByClassName('ndfHFb-c4YZDc-GSQQnc-LgbsSe ndfHFb-c4YZDc-to915-LgbsSe VIpgJd-TzA9Ye-eEGnhe ndfHFb-c4YZDc-LgbsSe')[0].style.display='none'; })()");
            }
        });


        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });



    }
}