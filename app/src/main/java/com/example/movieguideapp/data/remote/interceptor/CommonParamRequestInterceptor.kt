package com.example.movieguideapp.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CommonParamRequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val originUrl = originRequest.url

        val url = originUrl.newBuilder()
            .addQueryParameter("api_key","b4541d9b67f1298ea256c81612566495")
            .build()

        val newRequest = originRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}