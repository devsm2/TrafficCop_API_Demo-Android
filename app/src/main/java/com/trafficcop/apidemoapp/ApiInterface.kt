package com.trafficcop.apidemoapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @POST("https://tc.pubguru.net/v1")
    @Headers("Authorization: Token ADD_TOKEN_HERE")
    suspend fun checkIvtScore(@Body trafficCopRequestBody: TrafficCopRequestBody): Response<TrafficCopResponse>

}