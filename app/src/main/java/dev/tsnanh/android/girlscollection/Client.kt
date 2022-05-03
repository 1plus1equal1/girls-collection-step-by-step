package dev.tsnanh.android.girlscollection

import dev.tsnanh.android.girlscollection.models.PostsResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

object Client {
    private const val POSTS_URL = "https://api.tumblr.com/v2/blog/gaixinhchonloc/posts?api_key=rpyJ4MNH5MnXpEuz40kuVkeG3DPGQZE03aISFSwGx7pNv4zLoi"
    private val httpClient = HttpClient(Android) {
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    suspend fun getPostResponse(): PostsResponse {
        return httpClient.get(POSTS_URL)
    }
}
