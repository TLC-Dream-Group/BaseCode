package com.appnew.basecode.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appnew.basecode.R
import com.appnew.basecode.data.local.entities.Wallpaper
import com.appnew.basecode.databinding.ItemWallpaperBinding
import com.bumptech.glide.Glide

class WallpaperAdapter(
    private val items: MutableList<Wallpaper>,
    private val onFav: (Wallpaper) -> Unit,
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.VH>() {

    inner class VH(val b: ItemWallpaperBinding) : RecyclerView.ViewHolder(b.root) {
        init {
            b.btnFavorite.setOnClickListener {
                val pos = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                val w = items[pos]
                w.favorite = !w.favorite
                notifyItemChanged(pos)
                onFav(w)
            }
            b.root.setOnClickListener {
                val pos = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return@setOnClickListener
                onClick(items[pos])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        return VH(ItemWallpaperBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.b) {
            Glide.with(ivWallpaper.context).load(item.imageRes).into(ivWallpaper)
            btnFavorite.setImageResource(if (item.favorite) R.drawable.ic_favorite_border else R.drawable.ic_favorite_border)
        }
    }

    override fun getItemCount() = items.size

    fun submit(newList: List<Wallpaper>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}