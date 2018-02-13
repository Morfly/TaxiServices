package com.morfly.redirecturitest.controllers.lyft

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by Alex on 2/13/18.
 */
interface LyftService {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun authorization(
            @Header("Basic ") base64ClientIdSecret: String,
            @Field("grant_type") grandType: String,
            @Field("code") authorizationCode: String): Call<AuthUser>

    @GET("/v1/rides")
    fun userRidesHistory(
            // "Authorization: Bearer <access_token>" \
            // 'https://api.lyft.com/v1/rides?start_time=2015-12-01T21:04:22Z&end_time=2015-12-04T21:04:22Z&limit=10'
            // or 'https://api.lyft.com/v1/rides?start_time=2015-12-01T21:04:22Z'
            @Header("Authorization:") accessToken: String,
            @Query("start_time") startTime: String,
            @Query("end_time") endTime: String,
            @Query("limit") limit: String): Call<UserRidesHistory>

    @POST("/oauth/token")
    fun refreshToken(
            @Header("Basic ") base64ClientIdSecret: String,
            @Field("grant_type") grandType: String,
            @Field("refresh_token") refreshToken: String): Call<AuthUser>

    companion object {
        val retrofit: Retrofit
            get() = Retrofit.Builder()
                    .baseUrl("https://api.lyft.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
    }
}