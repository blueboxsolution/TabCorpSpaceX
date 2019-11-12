package com.example.spacex

import android.app.Application
import com.example.spacex.repository.LaunchRepository
import com.example.spacex.repository.api.LaunchApi
import com.example.spacex.viewModel.DetailViewModel
import com.example.spacex.viewModel.LaunchListViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var launchApi: LaunchApi
        private lateinit var launchRepository: LaunchRepository
        private lateinit var launchListViewModel: LaunchListViewModel
        private lateinit var detailViewModel: DetailViewModel

        fun injectLaunchListViewModel() = launchListViewModel

        fun injectDetailViewModel() = detailViewModel

    }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.spacexdata.com/v3/")
            .build()

        launchApi = retrofit.create(LaunchApi::class.java)
        launchRepository = LaunchRepository(launchApi)
        launchListViewModel = LaunchListViewModel(launchRepository)
        detailViewModel = DetailViewModel(launchRepository)
    }
}