package com.morfly.redirecturitest.controllers

import android.app.Activity
import android.content.Intent
import android.util.Base64
import android.util.Log
import com.morfly.redirecturitest.*
import com.morfly.redirecturitest.controllers.lyft.AuthUser
import com.morfly.redirecturitest.controllers.lyft.LyftLoginActivity
import com.morfly.redirecturitest.controllers.lyft.LyftService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Alex on 2/12/18.
 */
class LyftController(var activity: Activity) : TaxiService {

    companion object {
        const val CLIENT_ID = "6yPxmLNHA2Si"
        const val CLIENT_SECRET = "ot0GWmXAvVyFT92R0uQD7R1ZWPZKmDAK"
        const val GRAND_TYPE = "authorization_code"
        const val AUTH_URL = "https://www.lyft.com/oauth/authorize?client_id=6yPxmLNHA2Si" +
                "&scope=public%20profile%20rides.read%20rides.request%20offline&state=state&response_type=code"
    }

    override fun authorize() {
//        var intent = Intent(activity, RedirectActivity::class.java)
//        intent.putExtra(EXTRA_PARAM_AUTH_URL, AUTH_URL)
//        activity.startActivityForResult(intent, AUTH_ACTIVITY_REQUEST_CODE)
        var intent = Intent(activity, LyftLoginActivity::class.java)
        intent.putExtra(EXTRA_PARAM_AUTH_URL, AUTH_URL)
        activity.startActivity(intent)
    }

    override fun getRideInfo() {
        var lyftService = LyftService.retrofit.create(LyftService::class.java)
        var authorizationCode = "fqwefcr1c4123c_TEST_CODE"
        var authorizationHeader = "$CLIENT_ID:$CLIENT_SECRET"

        val data = authorizationHeader.toByteArray(Charsets.UTF_8)
        val encryptedHeader = Base64.encodeToString(data, Base64.DEFAULT)
        //
        var call: Call<AuthUser> = lyftService.authorization(encryptedHeader, GRAND_TYPE, authorizationCode)
        //String result = call.execute().body().toString();
        call.enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>?, response: Response<AuthUser>?) {

            }

            override fun onFailure(call: Call<AuthUser>?, t: Throwable?) {

            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var authCode = data?.getStringExtra(QUERY_PARAMETER_CODE)
        Log.e("LOGG", "code = $authCode")
    }
}