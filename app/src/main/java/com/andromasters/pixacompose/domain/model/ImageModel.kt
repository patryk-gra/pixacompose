package com.andromasters.pixacompose.domain.model

data class ImageModel(
    val id: Long,
    val userName: String,
    val tags: String,
    val likesCount: Int,
    val downloadsCount: Int,
    val commentsCount: Int,
    val imageUrl: String,
    val largeImageUrl: String,
    val userImageUrl: String,
)
