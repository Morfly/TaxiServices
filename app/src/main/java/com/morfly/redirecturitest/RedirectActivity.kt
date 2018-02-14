package com.morfly.redirecturitest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redirect)
        val data = intent.data
        val scheme = data.getScheme() // "http"
        val host = data.getHost() // "twitter.com"
        val code = data.getQueryParameter("code")

//        adb shell am start -a android.intent.action.VIEW -d "myapp://returnapp/?status=1"
//        http://localhost/?code=OQ4Hvcevx2YWObCIWeWz7p3FCAu0Cd#_
        Log.e("LOGG", "uri = $data, scheme = $scheme, host = $host, code = $code")
        //todo handle authorization code
    }
}
