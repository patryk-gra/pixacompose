package com.andromasters.pixacompose.data.remote

import com.andromasters.pixacompose.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object LoggingInterceptor {

    val INSTANCE: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)

            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }

            return httpLoggingInterceptor
        }
}