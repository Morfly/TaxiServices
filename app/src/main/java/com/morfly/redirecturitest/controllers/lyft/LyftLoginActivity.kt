package com.morfly.redirecturitest.controllers.lyft

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.morfly.redirecturitest.EXTRA_PARAM_AUTH_URL
import com.morfly.redirecturitest.QUERY_PARAMETER_CODE
import com.morfly.redirecturitest.R


class LyftLoginActivity : AppCompatActivity() {

    lateinit private var webView: WebView
    lateinit private var authUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyft_login)

        authUrl = intent.extras.getString(EXTRA_PARAM_AUTH_URL, "https://google.com")
        webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(authUrl)
        //webView.webViewClient = CustomWeViewClient()

        //worked solution for clearing cache
        CookieSyncManager.createInstance(this)
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookie()
    }

    inner class CustomWeViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (url!!.contains("?code=")) {
                var uri = Uri.parse(url)
                var authCode = uri.getQueryParameter(QUERY_PARAMETER_CODE)
                Log.e("LOGG", "code = $authCode")
                sendResultAndFinish(authCode)
            }
        }
    }

    fun sendResultAndFinish(code: String) {
        var data = Intent()
        data.putExtra(QUERY_PARAMETER_CODE, code)
        setResult(Activity.RESULT_OK, data)
        finish()
    }


}
