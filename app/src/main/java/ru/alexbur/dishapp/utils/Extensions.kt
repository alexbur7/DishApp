package ru.alexbur.dishapp.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import java.util.concurrent.CancellationException

inline fun <R> safeRunCatching(block: () -> R): Result<R> = try {
    Result.success(block())
} catch (e: CancellationException) {
    throw e
} catch (e: Throwable) {
    Result.failure(e)
}

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}