package ru.alexbur.dishapp.di

import dagger.Module
import dagger.Provides
import ru.alexbur.dishapp.utils.DispatcherProvider
import ru.alexbur.dishapp.utils.DispatcherProviderImpl
import javax.inject.Singleton

@Module
object UtilsModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }
}