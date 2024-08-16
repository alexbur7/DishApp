package ru.alexbur.dishapp.presentation.dishes.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.alexbur.dishapp.presentation.dishes.entity.DishEntity

class DishesAdapter(
    private val clickCheckBox: (String, Boolean) -> Unit,
    private val clickItem: (String) -> Unit
) : ListAdapter<DishEntity, DishViewHolder>(diffUtl) {

    private companion object {
        val diffUtl = object : DiffUtil.ItemCallback<DishEntity>() {
            override fun areItemsTheSame(oldItem: DishEntity, newItem: DishEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DishEntity, newItem: DishEntity): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: DishEntity, newItem: DishEntity): Any? {
                return if (oldItem.isSelected != newItem.isSelected) newItem.isSelected else null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        return DishViewHolder.create(parent).apply {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                clickCheckBox(getItem(absoluteAdapterPosition).id, isChecked)
            }
            itemView.setOnClickListener {
                clickItem(getItem(absoluteAdapterPosition).id)
            }
        }
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val isSelected = payloads[0] as? Boolean ?: return
            holder.onBindWithPayload(isSelected)
            return
        }
        super.onBindViewHolder(holder, position, payloads)
    }
}