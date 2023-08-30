package com.andromasters.pixacompose.presentation.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andromasters.pixacompose.presentation.theme.PixabayAppTheme

@Composable
fun DetailsScreen(
    userName: String?,
    userImageUrl: String?,
    tags: String?,
    likesCount: Int?,
    downloadsCount: Int?,
    commentsCount: Int?,
    largeImageUrl: String?,
) {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, Color.White),
                    startX = 10.0f,
                    endX = Float.POSITIVE_INFINITY,
                    tileMode = TileMode.Mirror
                )
            )
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp)
        ) {
            ImageDetails(
                userName = userName,
                userImageUrl = userImageUrl,
                tags = tags
            )
        }
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp)
        ) {
            ImageStats(
                likesCount = likesCount,
                downloadsCount = downloadsCount,
                commentsCount = commentsCount
            )
        }
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .let {
                    if (isPortrait) {
                        it.fillMaxWidth()
                    } else {
                        it.fillMaxHeight()
                    }
                }
                .padding(all = 4.dp)
                .weight(1f)
        ) {
            MainImage(url = largeImageUrl)
        }
    }
}

@Composable
fun ImageDetails(
    userName: String?,
    userImageUrl: String?,
    tags: String?,
) {

    RoundImage(
        imageUrl = userImageUrl.orEmpty(),
        userName = userName.orEmpty(),
        modifier = Modifier
            .padding(all = 8.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .wrapContentHeight(align = Alignment.Top)
            .size(50.dp)
    )

    Column(
        modifier = Modifier
            .wrapContentWidth(align = Alignment.End)
            .wrapContentHeight(align = Alignment.Bottom)
            .padding(all = 8.dp)
    ) {
        Row(verticalAlignment = CenterVertically) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
            Text(
                text = userName ?: "",
                modifier = Modifier.padding(all = 2.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.height(2.dp))
        Row(verticalAlignment = CenterVertically) {
            Icon(
                imageVector = Icons.Default.Tag,
                contentDescription = null
            )
            Text(
                text = tags ?: "",
                modifier = Modifier.padding(all = 2.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MainImage(
    url: String?
) {
    val painterPlaceholder = rememberVectorPainter(image = Icons.Default.Image)
    val painterError = rememberVectorPainter(image = Icons.Default.BrokenImage)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true).build(),
        contentDescription = null,
        contentScale = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ContentScale.FillWidth
        } else {
            ContentScale.FillHeight
        },

        placeholder = painterPlaceholder,
        error = painterError
    )
}

@Composable
fun ImageStats(
    likesCount: Int?,
    downloadsCount: Int?,
    commentsCount: Int?,
) {
    Row(
        verticalAlignment = CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = likesCount.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
    Row(verticalAlignment = CenterVertically) {
        Icon(
            imageVector = Icons.Default.Download,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = downloadsCount.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Spacer(modifier = Modifier.width(8.dp))
    Row(verticalAlignment = CenterVertically) {
        Icon(
            imageVector = Icons.Default.Comment,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = commentsCount.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RoundImage(
    imageUrl: String,
    userName: String,
    modifier: Modifier = Modifier
) {
    val painterPlaceholder = rememberVectorPainter(image = Icons.Default.Person)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = userName,
        placeholder = painterPlaceholder,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Preview(
    showSystemUi = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 1024,
    heightDp = 768
)
@Composable
fun PreviewDetails() {
    PixabayAppTheme {
        DetailsScreen(
            userName = "Mike Satoshi",
            tags = "red, fruits",
            likesCount = 220,
            downloadsCount = 5,
            commentsCount = 3,
            userImageUrl = "",
            largeImageUrl = ""
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDetailsPortrait() {
    PixabayAppTheme {
        DetailsScreen(
            userName = "Mike Satoshi",
            tags = "red, fruits",
            likesCount = 220,
            downloadsCount = 5,
            commentsCount = 3,
            userImageUrl = "",
            largeImageUrl = ""
        )
    }
}