package com.appnew.basecode.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appnew.basecode.data.local.entities.Banner
import com.appnew.basecode.databinding.ItemBannerBinding
import com.bumptech.glide.Glide

class BannerAdapter(
    private val items: List<Banner>
) : RecyclerView.Adapter<BannerAdapter.VH>() {

    inner class VH(val b: ItemBannerBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inf = LayoutInflater.from(parent.context)
        return VH(ItemBannerBinding.inflate(inf, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.b) {
            Glide.with(image.context).load(item.imageRes).into(image)
            title.text = item.title
        }
    }

    override fun getItemCount() = items.size
}