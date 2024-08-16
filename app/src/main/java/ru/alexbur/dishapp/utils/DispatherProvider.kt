package ru.alexbur.dishapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}

internal class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun computation(): CoroutineDispatcher = Dispatchers.Default
    override fun main(): CoroutineDispatcher = Dispatchers.Main
}