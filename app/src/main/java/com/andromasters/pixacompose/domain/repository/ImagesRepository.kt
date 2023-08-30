package com.andromasters.pixacompose.domain.repository

import com.andromasters.pixacompose.domain.model.ImageModel

interface ImagesRepository {
    suspend fun getImages(query: String): List<ImageModel>
}