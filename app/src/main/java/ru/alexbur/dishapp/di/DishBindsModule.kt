package ru.alexbur.dishapp.di

import dagger.Binds
import dagger.Module
import ru.alexbur.dishapp.data.api.DishApi
import ru.alexbur.dishapp.data.api.DishApiMock
import javax.inject.Singleton

@Module
internal interface DishBindsModule {

    @Binds
    @Singleton
    fun bindDishApi(api: DishApiMock): DishApi
}