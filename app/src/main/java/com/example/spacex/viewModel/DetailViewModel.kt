package com.example.spacex.viewModel

import android.util.Log
import com.example.spacex.repository.LaunchRepository
import com.example.spacex.viewModel.data.OneLaunchData
import com.example.spacex.viewModel.data.OneRocketData
import io.reactivex.Observable

class DetailViewModel(val launchRepository: LaunchRepository) {

    private val TAG = "Logs"
    private val ERROR = "Error"

    fun getOneLaunch(id : Int): Observable<OneLaunchData> {

        //Create the data for your UI, the launch and maybe some additional data needed as well
        return launchRepository.getOneLaunchFromApi(id)
            .map {
                Log.d(TAG, "Mapping launch to UIData...")

                OneLaunchData(it, "Launch By Id")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                OneLaunchData(null!!, "An error occurred", it)
            }
    }

    fun getOneRocket(id : String): Observable<OneRocketData> {

        //Create the data for your UI, the rocket and maybe some additional data needed as well
        return launchRepository.getOneRocketFromApi(id)
            .map {
                Log.d(TAG, "Mapping rockt to UIData...")

                OneRocketData(it, "Rocket By Id")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                OneRocketData(null!!, "An error occurred", it)
            }
    }
}