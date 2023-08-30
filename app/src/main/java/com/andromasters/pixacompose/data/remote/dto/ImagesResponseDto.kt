package com.andromasters.pixacompose.data.remote.dto

data class ImagesResponseDto(
    val total: Int,
    val totalHits: Int,
    val hits: List<ImageDto>
)
