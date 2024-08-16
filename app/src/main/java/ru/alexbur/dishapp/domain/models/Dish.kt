package ru.alexbur.dishapp.domain.models

data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val pictureUrl: String,
    val price: String,
)