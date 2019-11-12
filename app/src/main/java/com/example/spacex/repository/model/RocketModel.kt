package com.example.spacex.repository.model

data class RocketModel(
    val id : Int? = null,
    val rocket_id : String? = null,
    val rocket_name : String? = null,
    val rocket_type : String? = null,
    val description : String? = null,
    val wikipedia : String? = null,
    val active : Boolean? = null,
    val stages : Int? = null,
    val boosters : Int? = null,
    val cost_per_launch : String? = null,
    val success_rate_pct : String? = null,
    val first_flight : String? = null,
    val country : String? = null,
    val company : String? = null,

    val tentative_max_precision : String? = null,
    val tbd : Boolean? = null,
    val launch_window : String? = null,
    val rocket : LaunchRocket = LaunchRocket(),
    val height: RocketHeight = RocketHeight()
)

data class RocketHeight(
    val meters : String? = null,
    val feet : String? = null
)
