package com.andromasters.pixacompose.data.remote

import com.andromasters.pixacompose.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query
import com.andromasters.pixacompose.data.remote.dto.ImagesResponseDto

const val DEFAULT_IMAGE_TYPE = "photo"

interface PixabayApi {
    @GET("api/")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("image_type") imageType: String = DEFAULT_IMAGE_TYPE,
        @Query("key") apiKey: String = BuildConfig.API_KEY,
    ): ImagesResponseDto
}