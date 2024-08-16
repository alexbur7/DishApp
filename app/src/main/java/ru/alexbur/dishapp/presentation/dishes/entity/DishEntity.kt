package ru.alexbur.dishapp.presentation.dishes.entity

data class DishEntity(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)