package ru.alexbur.dishapp.presentation.dishes

import ru.alexbur.dishapp.presentation.dishes.entity.DishEntity

data class DishesViewState(
    val isLoading: Boolean = false,
    val dishes: List<DishEntity> = emptyList(),
    val isEnableRemoveButton: Boolean = false,
    val errorText: String? = null
)