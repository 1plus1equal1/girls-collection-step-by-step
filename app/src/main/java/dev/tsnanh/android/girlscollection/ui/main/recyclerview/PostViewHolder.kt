package dev.tsnanh.android.girlscollection.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.tsnanh.android.girlscollection.databinding.ItemRecyclerviewBinding
import dev.tsnanh.android.girlscollection.models.Post

class PostViewHolder private constructor(private val binding: ItemRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): PostViewHolder {
            val binding =
                ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding)
        }
    }

    fun bind(post: Post) {
        with(binding) {
            val photo = post.photos.first()
            textCaption.text = photo.caption
            Glide.with(image.context).load(photo.originalSize.url).into(image)
        }
    }

}

