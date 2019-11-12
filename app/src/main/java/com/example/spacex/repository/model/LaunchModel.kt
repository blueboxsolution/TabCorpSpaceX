package com.example.spacex.repository.model

data class LaunchModel(
    val flight_number : Int? = null,
    val mission_name : String? = null,
    val launch_year : String? = null,
    val launch_success : Boolean? = null,
    val mission_id : ArrayList<String>? = null,
    val launch_date_unix : String? = null,
    val launch_date_utc : String? = null,
    val launch_date_local : String? = null,
    val is_tentative : Boolean? = null,
    val tentative_max_precision : String? = null,
    val tbd : Boolean? = null,
    val launch_window : String? = null,
    val rocket : LaunchRocket = LaunchRocket()
)

data class LaunchRocket(
    val rocket_id : String? = null
    )