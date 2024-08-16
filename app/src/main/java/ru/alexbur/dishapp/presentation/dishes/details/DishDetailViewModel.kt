package ru.alexbur.dishapp.presentation.dishes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alexbur.dishapp.domain.interactor.DishInteractor
import ru.alexbur.dishapp.utils.ViewEvent

class DishDetailViewModel @AssistedInject constructor(
    @Assisted
    private val id: String,
    private val interactor: DishInteractor,
) : ViewModel() {

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun create(id: String, factory: Factory): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(id) as T
                }
            }
        }
    }

    private val _viewState = MutableStateFlow(DishDetailViewState())
    val viewState: StateFlow<DishDetailViewState> = _viewState.asStateFlow()

    private val _viewEvent = Channel<ViewEvent>()
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    init {
        loadDish()
    }

    fun obtainAction(action: DishDetailAction) {
        when (action) {
            DishDetailAction.ExitClick -> {
                viewModelScope.launch {
                    _viewEvent.send(ViewEvent.PopBackStack())
                }
            }
        }
    }

    private fun loadDish() {
        viewModelScope.launch {
            setupLoadingState(isLoading = true)
            interactor.getDishById(id).onSuccess { dish ->
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        content = DishDetailViewState.Content(
                            pictureUrl = dish.pictureUrl,
                            name = dish.name,
                            description = dish.description,
                            price = dish.price
                        )
                    )
                }
            }.onFailure {
                _viewEvent.send(ViewEvent.ShowSnackBar(it.message.orEmpty()))
                setupLoadingState(isLoading = false)
            }
        }
    }

    private fun setupLoadingState(isLoading: Boolean) {
        _viewState.update { it.copy(isLoading = isLoading) }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: String): DishDetailViewModel
    }
}