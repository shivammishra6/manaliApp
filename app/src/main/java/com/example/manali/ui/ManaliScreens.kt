package com.example.manali.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.manali.R
import com.example.manali.data.ManaliItem


@Composable
fun ManaliListItem(
    item: ManaliItem,
    onClick: (ManaliItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.big)),
            onClick = { onClick(item) },
            modifier = Modifier.width(600.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(item.image),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.width(dimensionResource(id = R.dimen.image_width))
                )
                Column(
                    Modifier
                        .weight(2f)
                        .padding(
                            vertical = dimensionResource(id = R.dimen.big),
                            horizontal = dimensionResource(id = R.dimen.large)
                        )
                ) {
                    Text(
                        text = stringResource(id = item.title),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = item.detail), overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ManaliList(
    list: List<ManaliItem>,
    onClick: (ManaliItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.medium))
    ) {
        items(list) {
            ManaliListItem(
                item = it,
                onClick = onClick,
                Modifier.height(dimensionResource(id = R.dimen.card_height))
            )
        }
    }
}

@Composable
fun ManaliScreen(
    list: List<ManaliItem>,
    onClick: (ManaliItem) -> Unit,
    uiState: ManaliUiState,
    modifier: Modifier = Modifier,
    contentType: ManaliContentType = ManaliContentType.LIST_ONLY,
    viewModel: ManaliViewModel,
    detailBackButtonClickedOnListOnly: () -> Unit,
    detailBackButtonClickedOnListAndDetail: () -> Unit
) {
    if (contentType == ManaliContentType.LIST_ONLY) {
        if (uiState.isShowingHomePage) {
            ManaliList(
                list = list,
                onClick = onClick,
                modifier = modifier
            )
        } else
            DetailPage(
                item = uiState.currentItem,
                modifier = modifier
            ) { detailBackButtonClickedOnListOnly() }
    } else {
        ManaliListAndDetail(
            uiState = uiState,
            viewModel=viewModel,
            list = list,
            detailBackButtonClickedOnListAndDetail=detailBackButtonClickedOnListAndDetail,
            modifier = modifier
        )
    }
}

@Composable
fun ManaliListAndDetail(
    uiState: ManaliUiState,
    list: List<ManaliItem>,
    viewModel: ManaliViewModel,
    detailBackButtonClickedOnListAndDetail: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        ManaliList(
            list = list,
            onClick = { viewModel.updateCurrentItem(it) },
            modifier = Modifier.weight(1f)
        )
        DetailPage(
            item = uiState.currentItem,
            modifier = Modifier.weight(1f)
        ) { detailBackButtonClickedOnListAndDetail() }
    }
}

@Composable
fun DetailPage(
    item: ManaliItem,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {

    BackHandler {
        onBackPress()
    }

    Column(modifier.verticalScroll(rememberScrollState())) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        )
        Text(
            text = stringResource(id = item.detail),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.big))
        )
    }
}


@Composable
fun StartPage(
    thingsClicked: () -> Unit,
    placesClicked: () -> Unit,
    foodClicked: () -> Unit,
    hotelsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .width(500.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_image),
                contentDescription = stringResource(id = R.string.app_name),
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.manali),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.large))
            )
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { thingsClicked() },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.button_width))
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.very_small))
                    ) {
                        Text(text = stringResource(id = R.string.things))
                    }
                    Button(
                        onClick = { placesClicked() },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.button_width))
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.very_small))
                    ) {
                        Text(text = stringResource(id = R.string.places))
                    }
                }
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { foodClicked() },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.button_width))
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.very_small))
                    ) {
                        Text(text = stringResource(id = R.string.food))
                    }
                    Button(
                        onClick = { hotelsClicked() },
                        modifier = Modifier
                            .width(dimensionResource(id = R.dimen.button_width))
                            .weight(1f)
                            .padding(horizontal = dimensionResource(id = R.dimen.very_small))
                    ) {
                        Text(text = stringResource(id = R.string.hotels))
                    }
                }
            }
        }
    }
}



