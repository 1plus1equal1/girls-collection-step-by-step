package dev.tsnanh.android.girlscollection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tsnanh.android.girlscollection.Client
import dev.tsnanh.android.girlscollection.models.PostsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    init {
        getPosts()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                val postsResponse = withContext(Dispatchers.IO) {
                    Client.getPostResponse()
                }
                _response.value = postsResponse
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}
