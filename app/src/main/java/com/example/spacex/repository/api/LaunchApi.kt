package com.example.spacex.repository.api

import com.example.spacex.repository.model.LaunchModel
import com.example.spacex.repository.model.RocketModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface LaunchApi{

    @GET("launches?limit=20")
    fun getLaunches(): Observable<List<LaunchModel>>

    @GET("launches/{id}")
    fun getOneLaunch(@Path("id") id : Int): Observable<LaunchModel>

    @GET("rockets/{id}")
    fun getOneRocket(@Path("id") id : String): Observable<RocketModel>

}
