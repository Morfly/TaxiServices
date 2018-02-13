package com.morfly.redirecturitest.controllers

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.morfly.redirecturitest.TaxiService
import com.uber.sdk.android.core.auth.AccessTokenManager
import com.uber.sdk.android.core.auth.AuthenticationError
import com.uber.sdk.android.core.auth.LoginCallback
import com.uber.sdk.android.core.auth.LoginManager
import com.uber.sdk.core.auth.AccessToken
import com.uber.sdk.core.auth.Scope
import com.uber.sdk.core.client.SessionConfiguration
import com.uber.sdk.rides.client.UberRidesApi
import com.uber.sdk.rides.client.error.ErrorParser
import com.uber.sdk.rides.client.model.Ride
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created by Alex on 2/12/18.
 */
class UberController(var activity: Activity) : TaxiService {

    private lateinit var accessTokenStorage: AccessTokenManager
    private lateinit var loginManager: LoginManager

    companion object {
        const val CLIENT_ID = "QNfnW4IDlddulKnKjEJMftCMbtLl--F4"
        const val SERVER_TOKEN = "QM1RF3ZMw2nCcr0xzP0809Nr4GLrTz50kLEC_yWx"
        const val REDIRECT_URI = "myapp://returnap"
        const val CUSTOM_BUTTON_REQUEST_CODE = 1113
    }

    init {
        val configuration = SessionConfiguration.Builder()
                .setClientId(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.REQUEST, Scope.ALL_TRIPS))
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build()

        accessTokenStorage = AccessTokenManager(activity)
        loginManager = LoginManager(accessTokenStorage,
                SampleLoginCallback(),
                configuration,
                CUSTOM_BUTTON_REQUEST_CODE)
    }

    private inner class SampleLoginCallback : LoginCallback {

        override fun onLoginCancel() {
            Toast.makeText(activity, "onLoginCancel", Toast.LENGTH_LONG).show()
        }

        override fun onLoginError(error: AuthenticationError) {
            Toast.makeText(activity,
                    "onLoginError = ${error.name}", Toast.LENGTH_LONG)
                    .show()
        }

        override fun onLoginSuccess(accessToken: AccessToken) {
            Toast.makeText(activity, "onLoginSuccess", Toast.LENGTH_LONG).show()
            getRideInfo()
        }

        override fun onAuthorizationCodeReceived(authorizationCode: String) {
            Toast.makeText(activity, "onAuthorizationCodeReceived = $authorizationCode",
                    Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun authorize() {
        loginManager.login(activity)
    }

    override fun getRideInfo() {
        val session = loginManager.session
        session.let {
            val service = UberRidesApi.with(it).build().createService()
//            service.userProfile
//                    .enqueue(object : Callback<UserProfile> {
//                        override fun onFailure(call: Call<UserProfile>?, t: Throwable?) {
//                            Toast.makeText(this@MainActivity, "onUserProfileFailure", Toast.LENGTH_LONG).show()
//                        }
//
//                        override fun onResponse(call: Call<UserProfile>?, response: Response<UserProfile>?) {
//                            if (response?.isSuccessful == true) {
//                                Toast.makeText(this@MainActivity, "Hello ${response.body()?.firstName}", Toast.LENGTH_LONG).show()
//                            } else {
//                                val error = ErrorParser.parseError(response!!)
//                                Toast.makeText(this@MainActivity, error!!.clientErrors[0].title, Toast.LENGTH_LONG).show()
//                            }
//                        }
//
//                    })
            service.currentRide
                    .enqueue(object : Callback<Ride> {
                        override fun onFailure(call: Call<Ride>?, t: Throwable?) {
                            Toast.makeText(activity, "onUserProfileFailure", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Ride>?, response: Response<Ride>?) {
                            if (response?.isSuccessful == true) {
                                Toast.makeText(activity, "Hello ${response.body()?.vehicle?.model}", Toast.LENGTH_LONG).show()
                            } else {
                                val error = ErrorParser.parseError(response!!)
                                Toast.makeText(activity, error!!.clientErrors[0].title, Toast.LENGTH_LONG).show()
                            }
                        }

                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginManager.onActivityResult(activity, requestCode, resultCode, data)
    }

}