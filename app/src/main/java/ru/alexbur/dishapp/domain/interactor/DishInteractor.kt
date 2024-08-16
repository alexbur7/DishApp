package ru.alexbur.dishapp.domain.interactor

import ru.alexbur.dishapp.domain.repository.DishRepository
import ru.alexbur.dishapp.utils.safeRunCatching
import javax.inject.Inject

class DishInteractor @Inject constructor(
    private val repository: DishRepository
) {
    suspend fun getDishes() = safeRunCatching { repository.getDishes() }
    suspend fun getDishById(id: String) = safeRunCatching { repository.getDishById(id) }
    suspend fun removeDishes(ids: List<String>) = safeRunCatching { repository.removeDishes(ids) }
}