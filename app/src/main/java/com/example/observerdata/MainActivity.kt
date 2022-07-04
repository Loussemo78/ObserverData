package com.example.observerdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.observerdata.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.btnLiveData.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }

        subscribeToObservables()
    }

    private fun subscribeToObservables(){
      viewModel.liveData.observe(this){
          binding.tvLiveData.text = it
      }
      lifecycleScope.launchWhenCreated {
          viewModel.stateFlow.collectLatest {
              binding.tvStateFlow.text = it
          }
         
      }
    }
}