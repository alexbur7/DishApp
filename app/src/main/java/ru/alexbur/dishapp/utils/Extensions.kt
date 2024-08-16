package ru.alexbur.dishapp.utils

import java.util.concurrent.CancellationException

inline fun <R> safeRunCatching(block: () -> R): Result<R> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Result.failure(e)
}