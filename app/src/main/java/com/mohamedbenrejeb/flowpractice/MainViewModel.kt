package com.mohamedbenrejeb.flowpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class MainViewModel: ViewModel() {

    private val _isLoading = MutableStateFlow(false) // Mutable private
    val isLoading get() = _isLoading.asStateFlow() // Immutable public

    private val _data = MutableStateFlow<Post?>(null)
    val data get() = _data.asStateFlow()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            // Loading...
            _isLoading.value = true

            val post = getDataFromApi()

            // Success
            _isLoading.value = false
            _data.value = post
        }
    }

    /*
    val flow = flow<String> {
        var count = 0
        while (count < 5) {
            count++
            emit("Count $count")
            delay(1000)
        }
        emit("Finished")
    }
    */

}

suspend fun getDataFromApi(): Post {
    delay(3000)
    return Post(id = "randomid", title = "title")
}

data class Post(
    val id: String,
    val title: String
)

fun main() {
    val flow = flow<String> {
        var count = 0
        while (count < 5) {
            count++
            println("emit: Count $count")
            emit("Count $count")
            delay(1000)
        }
        emit("Finished")
    }

    runBlocking {
        flow.collect {
            println("collect $it")
            delay(10000)
            println("end collect delay")
        }
    }

    /*val test = Test()
    test.value = "new"
    repeat(10) {
        println(test.randomGet)
    }*/
}

class Test() {
    var value = ""
    val random = (0..10).random()
    val randomGet get() = (0..10).random()
}