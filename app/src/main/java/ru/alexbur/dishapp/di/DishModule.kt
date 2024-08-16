package ru.alexbur.dishapp.di

import dagger.Module
import dagger.Provides
import ru.alexbur.dishapp.data.api.DishApi
import ru.alexbur.dishapp.data.mapper.DishMapper
import ru.alexbur.dishapp.data.repository.DishRepositoryImpl
import ru.alexbur.dishapp.domain.repository.DishRepository
import ru.alexbur.dishapp.utils.DispatcherProvider

@Module
object DishModule {

    @Provides
    internal fun provideDishRepository(
        mapper: DishMapper,
        apiMock: DishApi,
        dispatcherProvider: DispatcherProvider
    ): DishRepository {
        return DishRepositoryImpl(
            mapper = mapper,
            api = apiMock,
            dispatcherProvider = dispatcherProvider
        )
    }
}