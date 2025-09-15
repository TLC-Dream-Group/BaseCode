package com.appnew.basecode.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.appnew.basecode.data.local.entities.Category
import com.appnew.basecode.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val items: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.VH>() {

    private var selectedPos = 0

    inner class VH(val b: ItemCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        init {
            b.root.setOnClickListener {
                val pos = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                val old = selectedPos
                selectedPos = pos
                notifyItemChanged(old)
                notifyItemChanged(selectedPos)
                onClick(items[pos])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        return VH(ItemCategoryBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.b) {
            tvCategory.text = item.name
            ivCategory.setImageResource(item.iconRes)
            // hiệu ứng selected đơn giản
            selectedBg.isVisible = (position == selectedPos)
        }
    }

    override fun getItemCount() = items.size
}