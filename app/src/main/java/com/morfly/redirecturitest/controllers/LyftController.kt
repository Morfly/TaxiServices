package com.morfly.redirecturitest.controllers

import android.app.Activity
import android.content.Intent
import android.util.Base64
import android.util.Log
import com.morfly.redirecturitest.EXTRA_PARAM_AUTH_URL
import com.morfly.redirecturitest.QUERY_PARAMETER_CODE
import com.morfly.redirecturitest.TaxiService
import com.morfly.redirecturitest.controllers.lyft.AuthUser
import com.morfly.redirecturitest.controllers.lyft.LyftLoginActivity
import com.morfly.redirecturitest.controllers.lyft.LyftService
import com.morfly.redirecturitest.controllers.lyft.UserRidesHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Alex on 2/12/18.
 */
class LyftController(var activity: Activity) : TaxiService {

    private lateinit var lyftService: LyftService

    companion object {
        const val CLIENT_ID = "6yPxmLNHA2Si"
        const val CLIENT_SECRET = "ot0GWmXAvVyFT92R0uQD7R1ZWPZKmDAK"
        const val GRAND_TYPE = "authorization_code"
        const val AUTH_URL = "https://www.lyft.com/oauth/authorize?client_id=6yPxmLNHA2Si" +
                "&scope=public%20profile%20rides.read%20rides.request%20offline&state=state&response_type=code"
    }

    init {
        lyftService = LyftService.retrofit.create(LyftService::class.java)
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
        authUserAndGetRide()
    }

    private fun authUserAndGetRide() {
        var authorizationCode = "fqwefcr1c4123c_TEST_CODE"
        var authorizationHeader = "$CLIENT_ID:$CLIENT_SECRET"

        val data = authorizationHeader.toByteArray(Charsets.UTF_8)
        val encryptedHeader = Base64.encodeToString(data, Base64.DEFAULT)
        //
        var call: Call<AuthUser> = lyftService.authorization(encryptedHeader, GRAND_TYPE, authorizationCode)
        //String result = call.execute().body().toString();
        call.enqueue(object : Callback<AuthUser> {
            override fun onResponse(call: Call<AuthUser>?, response: Response<AuthUser>?) {
                getRide(response?.body())
            }

            override fun onFailure(call: Call<AuthUser>?, t: Throwable?) {
                Log.d("test", "Failure user auth")
            }

        })
    }

    private fun getRide(user: AuthUser?) {
        var token = "Bearer ${user?.access_token}"
//        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//        val date = df.format(Calendar.getInstance().time)
        var startTime = "2017-12-01T21:04:22Z"
        var call: Call<UserRidesHistory> = lyftService.userRidesHistory(token, startTime)
        call.enqueue(object : Callback<UserRidesHistory> {
            override fun onResponse(call: Call<UserRidesHistory>?, response: Response<UserRidesHistory>?) {
                //todo handle current ride
            }

            override fun onFailure(call: Call<UserRidesHistory>?, t: Throwable?) {
                Log.d("test", "Failure fetching rides")
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var authCode = data?.getStringExtra(QUERY_PARAMETER_CODE)
        Log.e("LOGG", "code = $authCode")
    }
}