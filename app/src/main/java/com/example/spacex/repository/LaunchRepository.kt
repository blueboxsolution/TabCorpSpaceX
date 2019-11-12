package com.example.spacex.repository

import android.util.Log
import com.example.spacex.repository.api.LaunchApi
import com.example.spacex.repository.model.LaunchModel
import com.example.spacex.repository.model.RocketModel
import io.reactivex.Observable

class LaunchRepository(val launchApi: LaunchApi) {

    val TAG = "Logs"

    fun getLaunches(): Observable<List<LaunchModel>> {
        return Observable.concatArray(
            getLaunchesFromApi())
    }

    fun getLaunchesFromApi(): Observable<List<LaunchModel>> {
        return launchApi.getLaunches()
            .doOnNext {
                Log.e(TAG,"From API : ${it.size}")
            }
    }

    fun getOneLaunchFromApi(id : Int): Observable<LaunchModel> {
        return launchApi.getOneLaunch(id)
            .doOnNext {
                Log.e(TAG,"From API : ${it}")
            }
    }

    fun getOneRocketFromApi(id : String): Observable<RocketModel> {
        return launchApi.getOneRocket(id)
            .doOnNext {
                Log.e(TAG,"From API : ${it}")
            }
    }


}