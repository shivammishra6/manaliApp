package com.example.manali.ui

import androidx.lifecycle.ViewModel
import com.example.manali.data.ManaliItem
import com.example.manali.data.things
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ManaliViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        ManaliUiState(currentItem = things[0])
    )
    val uiState = _uiState.asStateFlow()

    fun updateCurrentItem(selectedItem: ManaliItem) {
        _uiState.update {
            it.copy(currentItem = selectedItem)
        }
    }

    fun navigateToDetailPage(){
        _uiState.update {
            it.copy(isShowingHomePage = false)
        }
    }

    fun navigateToHomePage(){
        _uiState.update {
            it.copy(isShowingHomePage = true)
        }
    }
}