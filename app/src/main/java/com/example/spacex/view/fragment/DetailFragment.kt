package com.example.spacex.view.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.App

import com.example.spacex.R
import com.example.spacex.view.adapter.LaunchListAdapter
import com.example.spacex.viewModel.data.LaunchListData
import com.example.spacex.viewModel.data.OneLaunchData
import com.example.spacex.viewModel.data.OneRocketData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_launch_list.*
import java.net.ConnectException
import java.net.UnknownHostException

class DetailFragment : BaseFragment() {

    private var launchId : Int? = null
    val detailViewModel = App.injectDetailViewModel()
    private val TAG = "Logs"

    override fun onCreate(savedInstanceState: Bundle?) {
        val args = arguments
        launchId = args!!.getInt("id",0)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onStart() {
        super.onStart()
        subscribe(detailViewModel.getOneLaunch(launchId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Received LaunchModel $it launch.")
                showDetail(it)

                subscribe(detailViewModel.getOneRocket(it.launch.rocket.rocket_id!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received RocketModel $it rocket.")
                        showRocketDetail(it)
                    }, {
                        showError()
                    }))
            }, {
                showError()
            }))
    }

    fun showDetail(data: OneLaunchData) {
        if (data.error == null) {

            tvFlightNo.text = data.launch.flight_number.toString()
            tvMissionName.text = data.launch.mission_name
            tvMissionID.text = data.launch.mission_id.toString()
            tvYear.text = data.launch.launch_year
            tvDateUnix.text = data.launch.launch_date_unix
            tvDateUtc.text = data.launch.launch_date_utc
            tvDateLocal.text = data.launch.launch_date_local
            tvDateTentative.text = data.launch.is_tentative.toString()
            tvMax.text = data.launch.tentative_max_precision
            tvTbd.text = data.launch.tbd.toString()
            tvLaunchWindow.text = data.launch.launch_window

        } else if (data.error is ConnectException || data.error is UnknownHostException) {
            Log.d("Logs","No connection")
        } else {
            showError()
        }
    }

    fun showRocketDetail(data: OneRocketData) {
        if (data.error == null) {

            tvRocketName.text = data.rocket.rocket_name
            tvRocketType.text = data.rocket.rocket_type
            tvWIkipedia.text = setTextUnderlinedSpannable(data.rocket.wikipedia!!)
            tvCountry.text = data.rocket.country
            tvCompany.text = data.rocket.company
            tvDescription.text = data.rocket.description

            tvWIkipedia.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(data.rocket.wikipedia)
                startActivity(openURL)
            }


        } else if (data.error is ConnectException || data.error is UnknownHostException) {
            Log.d(TAG,"No connection")
        } else {
            showError()
        }
    }

    fun showError() {
        Toast.makeText(context, "An error occurred :(", Toast.LENGTH_SHORT).show()
    }

    fun setTextUnderlinedSpannable(text: String): SpannableString {
        val spannableContent = SpannableString(text)
        spannableContent.setSpan(UnderlineSpan(), 0,  text.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        return spannableContent
    }



}
