package com.andromasters.pixacompose.presentation.nav

import com.andromasters.pixacompose.domain.model.ImageModel
import com.andromasters.pixacompose.presentation.utils.encode

sealed class Destinations {
    abstract val route: String

    object Splash : Destinations() {
        override val route: String
            get() = "Splash"
    }

    object Search : Destinations() {
        override val route: String
            get() = "Search"
    }

    object Details : Destinations() {
        private const val NAME = "Details"

        enum class Fields {
            UserName,
            UserImageUrl,
            Tags,
            LikesCount,
            DownloadsCount,
            CommentsCount,
            LargeImageUrl
        }

        override val route: String
            get() = NAME +
                    "?${Fields.UserName}={${Fields.UserName}}" +
                    "&${Fields.UserImageUrl}={${Fields.UserImageUrl}}" +
                    "&${Fields.Tags}={${Fields.Tags}}" +
                    "&${Fields.LikesCount}={${Fields.LikesCount}}" +
                    "&${Fields.DownloadsCount}={${Fields.DownloadsCount}}" +
                    "&${Fields.CommentsCount}={${Fields.CommentsCount}}" +
                    "&${Fields.LargeImageUrl}={${Fields.LargeImageUrl}}"

        fun getRouteWithData(imageModel: ImageModel) =
            NAME +
                    "?${Fields.UserName}=${imageModel.userName}" +
                    "&${Fields.UserImageUrl}=${imageModel.userImageUrl.encode()}" +
                    "&${Fields.Tags}=${imageModel.tags.encode()}" +
                    "&${Fields.LikesCount}=${imageModel.likesCount}" +
                    "&${Fields.DownloadsCount}=${imageModel.downloadsCount}" +
                    "&${Fields.CommentsCount}=${imageModel.commentsCount}" +
                    "&${Fields.LargeImageUrl}=${imageModel.largeImageUrl.encode()}"
    }

}