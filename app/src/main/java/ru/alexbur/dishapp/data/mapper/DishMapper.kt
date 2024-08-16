package ru.alexbur.dishapp.data.mapper

import ru.alexbur.dishapp.data.models.response.DishResponse
import ru.alexbur.dishapp.data.models.response.DishesResponse
import ru.alexbur.dishapp.domain.models.Dish
import ru.alexbur.dishapp.domain.models.Dishes
import javax.inject.Inject

class DishMapper @Inject constructor() {

    fun map(response: DishesResponse) = Dishes(
        dishes = response.dishes.map { map(it) }
    )

    fun map(response: DishResponse) = Dish(
        id = response.id,
        name = response.name,
        description = response.description,
        pictureUrl = response.pictureUrl,
        price = response.price
    )
}