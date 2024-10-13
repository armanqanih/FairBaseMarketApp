package org.lotka.xenon.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.lotka.xenon.domain.usecase.explorer.SearchItemUseCase
import org.lotka.xenon.domain.util.Resource
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchItemUseCase: SearchItemUseCase
) : ViewModel() {

  private val _state = MutableStateFlow(SearchState())
  val state = _state.asStateFlow()

  fun onEvent(event: SearchEvent) {
    when (event) {
      SearchEvent.SearchMovies -> {
        searchItems(_state.value.searchQuery)
      }
      is SearchEvent.UpdateSearchQuery -> {
        _state.value = state.value.copy(searchQuery = event.query)
      }
    }
  }

  private fun searchItems(query: String) {
    viewModelScope.launch {
      _state.value = state.value.copy(isLoading = true)

      searchItemUseCase(query).collect { result ->
        when (result) {
          is Resource.Success -> {
            _state.value = state.value.copy(
              searchList = result.data ?: emptyList(),
              isLoading = false
            )
          }
          is Resource.Error -> {
            _state.value = state.value.copy(
              error = result.message,
              isLoading = false
            )
          }
          is Resource.Loading -> {
            _state.value = state.value.copy(
              isLoading = result.isLoading
            )
          }
        }
      }
    }
  }
}
