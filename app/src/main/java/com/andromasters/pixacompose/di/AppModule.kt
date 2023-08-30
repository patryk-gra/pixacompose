package com.andromasters.pixacompose.di

import android.content.Context
import com.andromasters.pixacompose.data.remote.PixabayApi
import com.andromasters.pixacompose.data.remote.LoggingInterceptor
import com.andromasters.pixacompose.data.repository.ImagesRepositoryImpl
import com.andromasters.pixacompose.domain.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val API_URL = "https://pixabay.com/"
    private const val MEGABYTE = 1024 * 1024
    private const val CACHE_SIZE_MB = 15L
    private const val CACHE_SIZE_BYTES = CACHE_SIZE_MB * MEGABYTE

    @Singleton
    @Provides
    fun provideImagesRepository(
        apiService: PixabayApi
    ): ImagesRepository = ImagesRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideCache(
        @ApplicationContext ctx: Context
    ) = Cache(ctx.cacheDir, CACHE_SIZE_BYTES)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(LoggingInterceptor.INSTANCE)
            retryOnConnectionFailure(true)
            cache(cache)
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        httpClient: OkHttpClient
    ): PixabayApi {
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
            .create(PixabayApi::class.java)
    }
}
