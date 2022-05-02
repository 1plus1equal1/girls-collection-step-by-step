package dev.tsnanh.android.girlscollection.ui.main.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import dev.tsnanh.android.girlscollection.models.Post

class PostAdapter : ListAdapter<Post, PostViewHolder>(PostDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
