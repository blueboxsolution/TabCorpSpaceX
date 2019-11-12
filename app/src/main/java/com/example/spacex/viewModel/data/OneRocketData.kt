package com.example.spacex.viewModel.data

import com.example.spacex.repository.model.RocketModel

data class OneRocketData(val rocket: RocketModel, val message: String, val error: Throwable? = null)