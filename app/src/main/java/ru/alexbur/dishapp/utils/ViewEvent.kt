package ru.alexbur.dishapp.utils

interface ViewEvent {

    class PopBackStack(val data: Any? = null) : ViewEvent
    class NavigateTo(val screen: Screen) : ViewEvent
    class ShowSnackBar(val message: String) : ViewEvent
}