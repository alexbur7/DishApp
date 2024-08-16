package ru.alexbur.dishapp.presentation.screen

import androidx.fragment.app.Fragment
import ru.alexbur.dishapp.presentation.dishes.details.DishDetailFragment
import ru.alexbur.dishapp.utils.Screen

sealed class DishesScreen : Screen {

    class DishDetails(
        private val id: String
    ) : DishesScreen() {
        override val destination: Fragment
            get() = DishDetailFragment.newInstance(id)
    }
}