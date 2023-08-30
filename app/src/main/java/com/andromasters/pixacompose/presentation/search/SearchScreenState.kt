package com.andromasters.pixacompose.presentation.search

import com.andromasters.pixacompose.domain.model.ImageModel

data class SearchScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchText: String = "",
    val imagesDownloaded: List<ImageModel> = listOf()
)