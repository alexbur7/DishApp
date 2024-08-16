package ru.alexbur.dishapp.presentation.dishes.details

data class DishDetailViewState(
    val isLoading: Boolean = false,
    val content: Content? = null
) {
    data class Content(
        val pictureUrl: String,
        val name: String,
        val description: String,
        val price: String
    )
}