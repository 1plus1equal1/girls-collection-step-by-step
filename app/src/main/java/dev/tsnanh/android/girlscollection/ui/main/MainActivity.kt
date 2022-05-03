package dev.tsnanh.android.girlscollection.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.tsnanh.android.girlscollection.databinding.ActivityMainBinding
import dev.tsnanh.android.girlscollection.ui.main.recyclerview.PostAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val postAdapter = PostAdapter()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.response.observe(this) { postResponse ->
            postAdapter.submitList(postResponse.response.posts)
        }

        with(binding.mainRecyclerview) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = postAdapter
        }
    }
}
