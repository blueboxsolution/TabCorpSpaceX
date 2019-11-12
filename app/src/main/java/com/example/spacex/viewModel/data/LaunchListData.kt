package com.example.spacex.viewModel.data

import com.example.spacex.repository.model.LaunchModel

data class LaunchListData(val launches: List<LaunchModel>, val message: String, val error: Throwable? = null)