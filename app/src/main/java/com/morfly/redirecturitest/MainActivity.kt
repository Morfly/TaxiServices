package com.morfly.redirecturitest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.uber.sdk.android.core.auth.AuthenticationError
import com.uber.sdk.android.core.auth.LoginButton
import com.uber.sdk.android.core.auth.LoginCallback
import com.uber.sdk.core.auth.AccessToken
import com.uber.sdk.core.auth.Scope
import com.uber.sdk.core.client.SessionConfiguration
import com.uber.sdk.rides.client.UberRidesApi
import com.uber.sdk.rides.client.error.ErrorParser
import com.uber.sdk.rides.client.model.Ride
import com.uber.sdk.rides.client.model.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val CLIENT_ID = "QNfnW4IDlddulKnKjEJMftCMbtLl--F4"
        const val SERVER_TOKEN = "QM1RF3ZMw2nCcr0xzP0809Nr4GLrTz50kLEC_yWx"
        const val REDIRECT_URI = "myapp://returnap"
    }

    private lateinit var loginButtonBlack: LoginButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val configuration = SessionConfiguration.Builder()
                .setClientId(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .setScopes(Arrays.asList(Scope.PROFILE, Scope.REQUEST, Scope.ALL_TRIPS))
                .setEnvironment(SessionConfiguration.Environment.SANDBOX)
                .build()

        loginButtonBlack = findViewById(R.id.loginButtonBlack)
        loginButtonBlack.setCallback(SampleLoginCallback())
                .setSessionConfiguration(configuration)
    }

    private inner class SampleLoginCallback : LoginCallback {

        override fun onLoginCancel() {
            Toast.makeText(this@MainActivity, "onLoginCancel", Toast.LENGTH_LONG).show()
        }

        override fun onLoginError(error: AuthenticationError) {
            Toast.makeText(this@MainActivity,
                    "onLoginError = ${error.name}", Toast.LENGTH_LONG)
                    .show()
        }

        override fun onLoginSuccess(accessToken: AccessToken) {
            Toast.makeText(this@MainActivity, "onLoginSuccess", Toast.LENGTH_LONG).show()
            loadProfileInfo()
        }

        override fun onAuthorizationCodeReceived(authorizationCode: String) {
            Toast.makeText(this@MainActivity, "onAuthorizationCodeReceived = $authorizationCode",
                    Toast.LENGTH_LONG)
                    .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButtonBlack.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadProfileInfo() {
        val session = loginButtonBlack.loginManager?.session
        session?.let {
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
                            Toast.makeText(this@MainActivity, "onUserProfileFailure", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<Ride>?, response: Response<Ride>?) {
                            if (response?.isSuccessful == true) {
                                Toast.makeText(this@MainActivity, "Hello ${response.body()?.vehicle?.model}", Toast.LENGTH_LONG).show()
                            } else {
                                val error = ErrorParser.parseError(response!!)
                                Toast.makeText(this@MainActivity, error!!.clientErrors[0].title, Toast.LENGTH_LONG).show()
                            }
                        }

                    })

        }
    }
}
