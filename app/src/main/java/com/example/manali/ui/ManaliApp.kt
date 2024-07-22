package com.example.manali.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.manali.R
import com.example.manali.data.food
import com.example.manali.data.hotels
import com.example.manali.data.places
import com.example.manali.data.things

enum class ManaliScreens(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Things(title = R.string.things),
    Food(title = R.string.food),
    Hotel(title = R.string.hotels),
    Places(title = R.string.places)
}

enum class ManaliContentType {
    LIST_ONLY, LIST_AND_DETAIL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManaliTopAppBar(
    currentScreen: ManaliScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    uiState: ManaliUiState,
    viewModel: ManaliViewModel,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            if (uiState.isShowingHomePage)
                Text(text = stringResource(id = currentScreen.title))
            else
                Text(text = stringResource(id = uiState.currentItem.title))

        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = {
                    if (uiState.isShowingHomePage)
                        navigateUp()
                    else
                        viewModel.navigateToHomePage()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}


@Composable
fun ManaliApp(
    windowsSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = ManaliScreens.valueOf(
        backStackEntry?.destination?.route ?: ManaliScreens.Start.name
    )

    val viewModel: ManaliViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    val contentType: ManaliContentType = if (windowsSize == WindowWidthSizeClass.Expanded)
        ManaliContentType.LIST_AND_DETAIL
    else
        ManaliContentType.LIST_ONLY

    Scaffold(topBar = {
        ManaliTopAppBar(
            currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            uiState = uiState,
            viewModel = viewModel
        )
    }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = ManaliScreens.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = ManaliScreens.Start.name) {
                StartPage(
                    thingsClicked = { navController.navigate(ManaliScreens.Things.name) },
                    placesClicked = { navController.navigate(ManaliScreens.Places.name) },
                    foodClicked = { navController.navigate(ManaliScreens.Food.name) },
                    hotelsClicked = { navController.navigate(ManaliScreens.Hotel.name) }
                )
            }
            composable(route = ManaliScreens.Things.name) {
                ManaliScreen(
                    list = things,
                    onClick = {
                        viewModel.updateCurrentItem(it)
                        viewModel.navigateToDetailPage()
                    },
                    uiState = uiState,
                    contentType = contentType,
                    viewModel = viewModel,
                    detailBackButtonClickedOnListOnly = { viewModel.navigateToHomePage() }
                ) { navController.navigateUp() }
            }
            composable(route = ManaliScreens.Places.name) {
                ManaliScreen(
                    list = places,
                    onClick = {
                        viewModel.updateCurrentItem(it)
                        viewModel.navigateToDetailPage()
                    },
                    uiState = uiState,
                    contentType = contentType,
                    viewModel = viewModel,
                    detailBackButtonClickedOnListOnly = { viewModel.navigateToHomePage() }
                ) { navController.navigateUp() }

            }
            composable(route = ManaliScreens.Food.name) {
                ManaliScreen(
                    list = food,
                    onClick = {
                        viewModel.updateCurrentItem(it)
                        viewModel.navigateToDetailPage()
                    },
                    uiState = uiState,
                    contentType = contentType,
                    viewModel = viewModel,
                    detailBackButtonClickedOnListOnly = { viewModel.navigateToHomePage() }
                ) { navController.navigateUp() }
            }
            composable(route = ManaliScreens.Hotel.name) {
                ManaliScreen(
                    list = hotels,
                    onClick = {
                        viewModel.updateCurrentItem(it)
                        viewModel.navigateToDetailPage()
                    },
                    detailBackButtonClickedOnListOnly={viewModel.navigateToHomePage()},
                    detailBackButtonClickedOnListAndDetail={navController.navigateUp()},
                    uiState = uiState,
                    contentType = contentType,
                    viewModel = viewModel
                )
            }

        }
    }

}






