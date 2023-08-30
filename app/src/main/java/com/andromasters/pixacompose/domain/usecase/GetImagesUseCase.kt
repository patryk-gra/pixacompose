package com.andromasters.pixacompose.domain.usecase

import com.andromasters.pixacompose.domain.model.ImageModel
import com.andromasters.pixacompose.domain.repository.ImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImagesRepository
) {
    operator fun invoke(query: String): Flow<List<ImageModel>> = flow {
        emit(repository.getImages(query))
    }.flowOn(Dispatchers.IO)
}