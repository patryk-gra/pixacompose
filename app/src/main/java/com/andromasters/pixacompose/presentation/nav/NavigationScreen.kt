package com.andromasters.pixacompose.presentation.nav

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andromasters.pixacompose.domain.model.ImageModel
import com.andromasters.pixacompose.presentation.details.DetailsScreen
import com.andromasters.pixacompose.presentation.search.SearchScreen
import com.andromasters.pixacompose.presentation.utils.decode

@Composable
fun NavigationScreen() {
    fun slideOutAnimation() = slideOutHorizontally(targetOffsetX = { -1000 })
    fun slideInAnimation() = slideInHorizontally(initialOffsetX = { 1000 })
    fun fadeInAnimation() = fadeIn(animationSpec = tween(2000))
    fun fadeOutAnimation() = fadeOut(animationSpec = tween(2000))

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = Destinations.Search.route,
        enterTransition = {
            slideInAnimation()
        },
        exitTransition = {
            slideOutAnimation()
        }
    ) {
        composable(
            route = Destinations.Details.route,
            enterTransition = { fadeInAnimation() }
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments
            DetailsScreen(
                userName = arguments?.getString(Destinations.Details.Fields.UserName.name).orEmpty(),
                userImageUrl = arguments?.getString(Destinations.Details.Fields.UserImageUrl.name)?.decode().orEmpty(),
                tags = arguments?.getString(Destinations.Details.Fields.Tags.name)?.decode().orEmpty(),
                likesCount = arguments?.getString(Destinations.Details.Fields.LikesCount.name)?.toInt() ?: 0,
                downloadsCount = arguments?.getString(Destinations.Details.Fields.DownloadsCount.name)?.toInt() ?: 0,
                commentsCount = arguments?.getString(Destinations.Details.Fields.CommentsCount.name)?.toInt() ?: 0,
                largeImageUrl = arguments?.getString(Destinations.Details.Fields.LargeImageUrl.name)?.decode().orEmpty(),
            )
        }

        composable(
            route = Destinations.Search.route,
            exitTransition = {
                fadeOutAnimation()
            }
        ) {
            SearchScreen(
                onItemClick = {
                    navController.navigateToDetails(it)
                }
            )
        }
    }
}

fun NavController.navigateToDetails(imageModel: ImageModel) {
    this.navigate(Destinations.Details.getRouteWithData(imageModel))
}