package dev.tsnanh.android.girlscollection.ui.main.recyclerview

import androidx.recyclerview.widget.DiffUtil
import dev.tsnanh.android.girlscollection.models.Post

class PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}