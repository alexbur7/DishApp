package ru.alexbur.dishapp.presentation.dishes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alexbur.dishapp.domain.interactor.DishInteractor
import ru.alexbur.dishapp.domain.models.Dishes
import ru.alexbur.dishapp.presentation.dishes.entity.DishEntity
import ru.alexbur.dishapp.presentation.screen.DishesScreen
import ru.alexbur.dishapp.utils.ViewEvent
import javax.inject.Inject

class DishesViewModel(
    private val interactor: DishInteractor
) : ViewModel() {

    private val _viewState = MutableStateFlow(DishesViewState())
    val viewState: StateFlow<DishesViewState> = _viewState.asStateFlow()

    private val _viewEvent = Channel<ViewEvent>()
    val viewEvent: Flow<ViewEvent> = _viewEvent.receiveAsFlow()

    private val dishes = mutableListOf<DishEntity>()

    init {
        loadDishes()
    }

    fun obtainAction(action: DishesAction) {
        when (action) {
            DishesAction.RemoveClick -> removeClick()
            is DishesAction.ClickItem -> clickItem(action.id)
            is DishesAction.ClickCheckBox -> clickCheckBox(action.id, action.isChecked)
        }
    }

    private fun clickItem(id: String) {
        viewModelScope.launch {
            _viewEvent.send(ViewEvent.NavigateTo(DishesScreen.DishDetails(id)))
        }
    }

    private fun loadDishes() {
        viewModelScope.launch {
            setupLoadingState()

            interactor.getDishes().onSuccess {
                updateDishes(it)
                setupStateWithData()
            }.onFailure { error ->
                _viewState.update {
                    it.copy(isLoading = false, errorText = error.message)
                }
            }
        }
    }

    private fun removeClick() {
        viewModelScope.launch {
            setupLoadingState()

            interactor.removeDishes(dishes.mapNotNull { if (it.isSelected) it.id else null }).onSuccess {
                updateDishes(it)
                setupStateWithData()
            }.onFailure { error ->
                _viewEvent.send(ViewEvent.ShowSnackBar(error.message.orEmpty()))
                _viewState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    private fun setupLoadingState() {
        _viewState.update { it.copy(isLoading = true) }
    }

    private fun setupStateWithData() {
        _viewState.update {
            it.copy(
                isLoading = false,
                dishes = dishes.toList(),
                errorText = null,
                isEnableRemoveButton = dishes.any { dish -> dish.isSelected }
            )
        }
    }

    private fun clickCheckBox(key: String, isChecked: Boolean) {
        updateDishes(dishes.map { dish -> if (dish.id == key) dish.copy(isSelected = isChecked) else dish })
        setupStateWithData()
    }

    private fun updateDishes(it: Dishes) {
        updateDishes(it.dishes.map { dish -> DishEntity(dish.id, dish.name) })
    }

    private fun updateDishes(data: List<DishEntity>) {
        dishes.clear()
        dishes.addAll(data)
    }

    class Factory @Inject constructor(
        private val interactor: DishInteractor
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DishesViewModel(interactor) as T
        }
    }
}