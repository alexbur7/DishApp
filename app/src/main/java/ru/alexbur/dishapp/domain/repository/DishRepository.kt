package ru.alexbur.dishapp.domain.repository

import ru.alexbur.dishapp.domain.models.Dish
import ru.alexbur.dishapp.domain.models.Dishes

interface DishRepository {
    suspend fun getDishes(): Dishes
    suspend fun getDishById(id: String): Dish
    suspend fun removeDishes(ids: List<String>): Dishes
}