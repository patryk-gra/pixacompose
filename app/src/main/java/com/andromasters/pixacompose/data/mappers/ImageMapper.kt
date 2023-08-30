package com.andromasters.pixacompose.data.mappers

import com.andromasters.pixacompose.data.remote.dto.ImageDto
import com.andromasters.pixacompose.domain.model.ImageModel

fun ImageDto.toImageModel(): ImageModel {
    return ImageModel(
        id = this.id,
        userName = this.user.orEmpty(),
        userImageUrl = this.userImageURL.orEmpty(),
        tags = this.tags.orEmpty(),
        likesCount = this.likes ?: 0,
        downloadsCount = this.downloads ?: 0,
        commentsCount = this.comments ?: 0,
        imageUrl = this.previewURL.orEmpty(),
        largeImageUrl = this.largeImageURL.orEmpty()
    )
}