package com.mohamedbenrejeb.flowpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mohamedbenrejeb.flowpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(owner = this)[MainViewModel::class.java]

        binding.tryAgain.setOnClickListener {
            viewModel.getData()
        }

        lifecycleScope.launch {

            launch {
                viewModel.isLoading.collectLatest { isLoading ->
                    if (isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

            launch {
                viewModel.data.collectLatest { data ->
                    data?.let { post ->
                        binding.textView.text = post.title
                    }
                }
            }

        }

    }
}