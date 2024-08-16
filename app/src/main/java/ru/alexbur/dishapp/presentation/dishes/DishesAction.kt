package ru.alexbur.dishapp.presentation.dishes

sealed class DishesAction {
    data object RemoveClick : DishesAction()
    class ClickItem(val id: String) : DishesAction()
    class ClickCheckBox(val id: String, val isChecked: Boolean) : DishesAction()
}