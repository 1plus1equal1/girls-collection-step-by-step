package dev.tsnanh.android.girlscollection.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponse(
    val meta: Meta,
    val response: Response
)

@Serializable
data class Meta(
    val status: Int,
    val msg: String,
)

@Serializable
data class Response(
    @SerialName("total_posts")
    val totalPosts: Int,
    @SerialName("_links")
    val links: Links,
    val posts: List<Post>
)

@Serializable
data class Links(
    val next: Link
)

@Serializable
data class Link(
    val href: String,
    val method: String,
    @SerialName("query_params")
    val queryParams: QueryParams,
)

@Serializable
data class QueryParams(
    val tumblelog: String,
    @SerialName("page_number")
    val pageNumber: String
)

@Serializable
data class Post(
    val type: String,
    @SerialName("blog_name")
    val blogName: String,
    val blog: Blog,
    val id: Long,
    @SerialName("id_string")
    val idString: String,
    @SerialName("post_url")
    val postUrl: String,
    val slug: String,
    val date: String,
    val timestamp: Long,
    val state: String,
    val format: String,
    @SerialName("reblog_key")
    val reblogKey: String,
    val tags: List<String>,
    val photos: List<Photo>
)

@Serializable
data class Blog(
    val name: String,
    val title: String,
    val description: String,
    val url: String,
    val uuid: String,
    val updated: Int
)

@Serializable
data class Photo(
    val caption: String,
    @SerialName("original_size")
    val originalSize: Image
)

@Serializable
data class Image(
    val url: String,
)