package com.example.manali.ui

import com.example.manali.data.ManaliItem

data class ManaliUiState(
    val isListOnly:Boolean=true,
    val isShowingHomePage: Boolean = true,
    val currentItem: ManaliItem
)
