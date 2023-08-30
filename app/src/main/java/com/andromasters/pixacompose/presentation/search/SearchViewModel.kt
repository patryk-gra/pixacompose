package com.andromasters.pixacompose.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andromasters.pixacompose.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        onSearchTextChange(FIRST_SEARCH)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val images = searchText
        .debounce(1000L)
        .onEach {
            setError("")
            setLoading(true)
        }
        .flatMapLatest { text ->
            if (text.isBlank()) {
                flowOf(emptyList())
            } else {
                getImagesUseCase(text)
                    .onStart { delay(1500L) }
                    .catch { ex ->
                        setError(ex.message ?: "")
                    }
            }
        }
        .onEach { setLoading(false) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchTextChange(text: String) {
        state = state.copy(
            searchText = text
        )
        _searchText.value = text
    }

    private fun setLoading(value: Boolean) {
        state = state.copy(
            isLoading = value
        )
    }

    private fun setError(value: String) {
        state = state.copy(
            isLoading = false,
            error = value
        )
    }

    companion object {
        const val FIRST_SEARCH = "fruits"
    }
}