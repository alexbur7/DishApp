package ru.alexbur.dishapp.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.dishapp.data.api.DishApi
import ru.alexbur.dishapp.data.mapper.DishMapper
import ru.alexbur.dishapp.domain.models.Dish
import ru.alexbur.dishapp.domain.models.Dishes
import ru.alexbur.dishapp.domain.repository.DishRepository
import ru.alexbur.dishapp.utils.DispatcherProvider
import javax.inject.Inject

internal class DishRepositoryImpl @Inject constructor(
    private val mapper: DishMapper,
    private val api: DishApi,
    private val dispatcherProvider: DispatcherProvider
) : DishRepository {

    override suspend fun getDishes(): Dishes = withContext(dispatcherProvider.io()) {
        mapper.map(api.getDishes())
    }

    override suspend fun getDishById(id: String): Dish = withContext(dispatcherProvider.io()) {
        mapper.map(api.getById(id))
    }

    override suspend fun removeDishes(ids: List<String>): Dishes = withContext(dispatcherProvider.io()) {
        mapper.map(api.removeDishes(ids))
    }
}