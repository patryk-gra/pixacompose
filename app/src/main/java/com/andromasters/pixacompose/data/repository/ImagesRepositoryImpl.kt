package com.andromasters.pixacompose.data.repository

import com.andromasters.pixacompose.data.mappers.toImageModel
import com.andromasters.pixacompose.data.remote.PixabayApi
import com.andromasters.pixacompose.domain.model.ImageModel
import com.andromasters.pixacompose.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val apiService: PixabayApi
) : ImagesRepository {
    override suspend fun getImages(query: String): List<ImageModel> =
        apiService.getImages(query).hits.map { it.toImageModel() }
}