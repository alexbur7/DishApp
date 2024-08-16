package ru.alexbur.dishapp.presentation.dishes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexbur.dishapp.databinding.ItemDishBinding
import ru.alexbur.dishapp.presentation.dishes.entity.DishEntity
import ru.alexbur.dishapp.utils.loadImage

class DishViewHolder(
    private val binding: ItemDishBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): DishViewHolder {
            val binding = ItemDishBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DishViewHolder(binding)
        }
    }

    val checkBox get() = binding.checkbox

    fun onBind(entity: DishEntity) = with(binding) {
        tvDishName.text = entity.name
        checkbox.isChecked = entity.isSelected
        tvPrice.text = entity.price
        ivPicture.loadImage(entity.url)
    }

    fun onBindWithPayload(isSelected: Boolean) = with(binding) {
        if (checkbox.isChecked == isSelected) return@with
        checkbox.isChecked = isSelected
    }
}