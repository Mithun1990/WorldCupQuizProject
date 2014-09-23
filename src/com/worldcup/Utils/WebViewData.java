package com.worldcup.Utils;


import com.d3bugbd.worldcupQuiz.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewData extends Activity {

	WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_website);
		
		webView = (WebView) findViewById(R.id.webView1);

		webView.clearHistory();
		webView.clearFormData();
		webView.clearCache(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

/*		webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(true);*/

		WebSettings webSettings = webView.getSettings();
		webView.getSettings().setJavaScriptEnabled(false);
		webView.getSettings().setBuiltInZoomControls(true);

		webView.loadUrl("http://wcfifa.d3bug.com/rules.php");
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		finish();
	}

	

}
