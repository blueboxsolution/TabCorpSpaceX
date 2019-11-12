package com.example.spacex.viewModel

import android.util.Log
import com.example.spacex.repository.LaunchRepository
import com.example.spacex.repository.model.LaunchModel
import com.example.spacex.viewModel.data.LaunchListData
import com.example.spacex.viewModel.data.OneLaunchData
import io.reactivex.Observable
import java.util.*
import java.util.Optional.empty

class LaunchListViewModel(val launchRepository: LaunchRepository) {

    private var isAsc : Boolean = false
    private var isMission : Boolean = false
    private val TAG = "Logs"
    private val ERROR = "Error"


    fun getLaunches(): Observable<LaunchListData> {

        //Create the data for your UI, the launch lists and maybe some additional data needed as well
        return launchRepository.getLaunches()
            .map {
                Log.d(TAG, "Mapping launches to UIData...")
                LaunchListData(it.take(20), "Top 20 Launches")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                LaunchListData(emptyList(), "An error occurred", it)
            }
    }

    fun sortByDate(): Observable<LaunchListData> {

        //Create the data for your UI, the launch lists and maybe some additional data needed as well
        return launchRepository.getLaunches()
            .map {
                Log.d(TAG, "Mapping launches to UIData...")
                var sortedList = it.take(20)

                if(isAsc){
                    isAsc = false
                    sortedList = it.sortedWith(compareBy({ it.launch_year }))
                }else{
                    isAsc = true
                    sortedList = it.sortedWith(compareByDescending({ it.launch_year }))
                }



                LaunchListData(sortedList, "Top 20 Launches")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                LaunchListData(emptyList(), "An error occurred", it)
            }
    }

    fun sortByMission(): Observable<LaunchListData> {

        //Create the data for your UI, the launch lists and maybe some additional data needed as well
        return launchRepository.getLaunches()
            .map {
                Log.d(TAG, "Mapping launches to UIData...")
                var sortedList = it.take(20)

                if(isMission){
                    isMission = false
                    sortedList = it.sortedWith(compareBy({ it.mission_name }))
                }else{
                    isMission = true
                    sortedList = it.sortedWith(compareByDescending({ it.mission_name }))
                }

                LaunchListData(sortedList, "Top 20 Launches")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                LaunchListData(emptyList(), "An error occurred", it)
            }
    }

    fun getSuccessLaunches(): Observable<LaunchListData> {

        //Create the data for your UI, the launch lists and maybe some additional data needed as well
        return launchRepository.getLaunches()
            .map {
                Log.d(TAG, "Mapping launches to UIData...")
                LaunchListData( it.filter { s -> s.launch_success == true }, "Top 20 Launches")
            }
            .onErrorReturn {
                Log.d("Error","An error occurred $it")
                LaunchListData(emptyList(), "An error occurred", it)
            }
    }

    fun getFaileLaunches(): Observable<LaunchListData> {

        //Create the data for your UI, the launch lists and maybe some additional data needed as well
        return launchRepository.getLaunches()
            .map {
                Log.d(TAG, "Mapping launches to UIData...")
                var list = it.take(20)

                LaunchListData( it.filter { s -> s.launch_success == false }, "Top 20 Launches")
            }
            .onErrorReturn {
                Log.d(ERROR,"An error occurred $it")
                LaunchListData(emptyList(), "An error occurred", it)
            }
    }


}