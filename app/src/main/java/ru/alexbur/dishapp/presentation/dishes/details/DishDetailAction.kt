package ru.alexbur.dishapp.presentation.dishes.details

sealed class DishDetailAction {
    data object ExitClick : DishDetailAction()
}