package com.atilsamancioglu.flows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val countDownTimerFlow = flow<Int> {
        val countDownFrom = 10
        var counter = countDownFrom
        emit(countDownFrom)
        while(counter > 0) {
            delay(1000)
            counter--
            emit(counter)
        }
    }

    //Collect flow values within VM

    init {
        collectInViewModel()
    }

    private fun collectInViewModel() {
        viewModelScope.launch {
            countDownTimerFlow
                .filter {
                    it %3 == 0
                }
                .map {
                    it + it
                }
                .collect {
                println("counter is: ${it}")
            }
        /*
            countDownTimerFlow.collectLatest {
                delay(2000)
                println("counter is: ${it}")
            }

         */

        }

        //Same with collect ->
        /*
        countDownTimerFlow.onEach {
            println(it)
        }.launchIn(viewModelScope)
         */
    }

    //LiveData comparison

    private val _liveData = MutableLiveData<String>("KotlinLiveData")
    val liveData : LiveData<String> = _liveData

    fun changeLiveDataValue() {
        _liveData.value = "Live Data"
    }

    private val _stateFlow = MutableStateFlow("KotlinStateFlow")
    val stateFlow = _stateFlow.asStateFlow()

    //SharedFlow is highly configurable version of stateFlow.
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun changeStateFlowValue() {
        _stateFlow.value = "State Flow"
    }

    fun changeSharedFlowValue() {
        viewModelScope.launch {
            _sharedFlow.emit("Shared Flow")
        }
    }

}