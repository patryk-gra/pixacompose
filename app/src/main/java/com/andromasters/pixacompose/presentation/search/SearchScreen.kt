package com.andromasters.pixacompose.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andromasters.pixacompose.R
import com.andromasters.pixacompose.domain.model.ImageModel
import com.andromasters.pixacompose.presentation.theme.PixabayAppTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onItemClick: (model: ImageModel) -> Unit
) {

    val pattern = remember { Regex("[\\p{L}\\s]*") }
    val images by viewModel.images.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        TextField(
            value = viewModel.state.searchText,
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            singleLine = true,
            onValueChange = {
                if (it.matches(pattern)) {
                    viewModel.onSearchTextChange(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = stringResource(R.string.search_hint))
            },
        )

        Spacer(modifier = Modifier.height(14.dp))
        if (viewModel.state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else if (viewModel.state.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = viewModel.state.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            ImageList(
                images = images,
                onRowClick = onItemClick
            )
        }
    }
}

@Composable
fun ImageList(
    images: List<ImageModel>,
    onRowClick: (imageModel: ImageModel) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(images) { model ->
            ImageRowWithDialog(
                photoModel = model,
                onShowDetails = {
                    onRowClick(model)
                }
            )
        }
    }
}


@Composable
fun ImageRowWithDialog(
    photoModel: ImageModel,
    onShowDetails: (id: ImageModel) -> Unit
) {
    var dialogClicked by rememberSaveable {
        mutableStateOf(false)
    }
    if (dialogClicked) {
        CustomAlertDialog(
            imageUrl = photoModel.imageUrl,
            onDismiss = {
                dialogClicked = false
            },
            onAccepted = {
                onShowDetails(photoModel)
                dialogClicked = false
            }
        )

    }
    ImageRow(photoModel) {
        dialogClicked = true
    }
}

@Composable
fun ImageRow(
    photoModel: ImageModel,
    onItemClick: () -> Unit
) {
    val painterPlaceholder = rememberVectorPainter(image = Icons.Default.Image)

    Row(modifier = Modifier
        .padding(all = 6.dp)
        .clickable { onItemClick() }
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoModel.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = photoModel.userName,
            contentScale = ContentScale.Crop,
            placeholder = painterPlaceholder,
            modifier = Modifier
                .size(65.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = photoModel.userName,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = photoModel.tags,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    imageUrl: String,
    onDismiss: () -> Unit,
    onAccepted: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Red.copy(alpha = 0.8F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                }

                Text(
                    text = stringResource(R.string.dialog_popup_title),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 20.sp
                )

                Text(
                    text = stringResource(R.string.dialog_popup_message),
                    modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = stringResource(R.string.no).uppercase())
                    }


                    Button(
                        onClick = { onAccepted() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = stringResource(R.string.yes).uppercase())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetails() {
    PixabayAppTheme {
        ImageList(
            images = listOf(
                ImageModel(
                    id = 1,
                    userName = "Markus Twain",
                    tags = "dog, landscape, puppy",
                    likesCount = 11,
                    downloadsCount = 2,
                    commentsCount = 2,
                    imageUrl = "https://cdn.pixabay.com/photo/2023/08/18/19/44/dog-8199216_1280.jpg",
                    largeImageUrl = "https://cdn.pixabay.com/photo/2023/08/18/19/44/dog-8199216_1280.jpg",
                    userImageUrl = "https://cdn.pixabay.com/user/2021/07/22/08-49-04-239_250x250.png"
                ),
                ImageModel(
                    id = 2,
                    userName = "Kris Fronz",
                    tags = "bird, beak, eagle",
                    likesCount = 112,
                    downloadsCount = 2,
                    commentsCount = 3,
                    imageUrl = "https://cdn.pixabay.com/photo/2023/04/26/18/05/bird-7953069_1280.jpg",
                    largeImageUrl = "https://cdn.pixabay.com/photo/2023/04/26/18/05/bird-7953069_1280.jpg",
                    userImageUrl = "https://cdn.pixabay.com/user/2021/07/22/08-49-04-239_250x250.png"
                ),
            )
        ) {

        }
    }
}