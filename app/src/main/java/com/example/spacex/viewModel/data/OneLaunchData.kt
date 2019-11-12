package com.example.spacex.viewModel.data

import com.example.spacex.repository.model.LaunchModel

data class OneLaunchData(val launch: LaunchModel, val message: String, val error: Throwable? = null)