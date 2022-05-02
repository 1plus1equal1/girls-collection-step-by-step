package dev.tsnanh.android.girlscollection.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tsnanh.android.girlscollection.models.PostsResponse

class MainViewModel : ViewModel() {
    private val _response = MutableLiveData<PostsResponse>()
    val response: LiveData<PostsResponse> get() = _response

    fun getPosts() {

    }
}