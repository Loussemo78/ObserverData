package com.example.observerdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.internal.*
import kotlinx.coroutines.internal.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _liveData = MutableLiveData("Hello World")
    val liveData:LiveData<String> = _liveData

    private val _stateFlow = MutableStateFlow("Hello World")
    val stateFlow = _stateFlow.asStateFlow()


    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun triggerLiveData(){
        _liveData.value = "LiveData"
    }

    fun triggerFlow(): Flow<String> {
        return flow {
            repeat(5){
                emit("Item $it")
                delay(1000L)
            }
        }
    }

    fun triggerStateFlow(){
        _stateFlow.value = "StateFlow"
    }

    fun triggerSharedFlow(){
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }
}