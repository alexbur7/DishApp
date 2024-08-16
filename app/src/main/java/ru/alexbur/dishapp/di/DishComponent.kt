package ru.alexbur.dishapp.di

import dagger.Component
import ru.alexbur.dishapp.presentation.dishes.DishesFragment
import ru.alexbur.dishapp.presentation.dishes.details.DishDetailFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DishModule::class,
        DishBindsModule::class,
        UtilsModule::class
    ]
)
interface DishComponent {
    fun inject(fragment: DishesFragment)
    fun inject(fragment: DishDetailFragment)

    @Component.Factory
    interface Factory {
        fun create(): DishComponent
    }

    companion object {
        private var component: DishComponent? = null

        fun getComponent(): DishComponent {
            if (component != null) return component!!
            synchronized(this) {
                if (component != null) return component!!
                return DaggerDishComponent.factory()
                    .create()
                    .also {
                        component = it
                    }
            }
        }
    }
}