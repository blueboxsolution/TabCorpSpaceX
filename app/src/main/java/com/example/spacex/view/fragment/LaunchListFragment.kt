package com.example.spacex.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacex.App
import com.example.spacex.R
import com.example.spacex.view.adapter.LaunchListAdapter
import com.example.spacex.viewModel.data.LaunchListData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_launch_list.*
import java.net.ConnectException
import java.net.UnknownHostException

class LaunchListFragment : BaseFragment() {

    private val launchListViewModel = App.injectLaunchListViewModel()
    private val TAG = "Logs"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launch_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        subscribe(launchListViewModel.getLaunches()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Received LaunchModel $it launches.")
                showLaunches(it)
            }, {
                showError()
            }))
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return (when(item!!.itemId) {
            R.id.action_date -> {
                subscribe(launchListViewModel.sortByDate()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received LaunchModel $it launches.")
                        showLaunches(it)
                    }, {
                        showError()
                    }))
                true
            }
            R.id.action_mission -> {
                subscribe(launchListViewModel.sortByMission()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received LaunchModel $it launches.")
                        showLaunches(it)
                    }, {
                        showError()
                    }))
                true
            }
            R.id.action_success -> {
                subscribe(launchListViewModel.getSuccessLaunches()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received LaunchModel $it launches.")
                        showLaunches(it)
                    }, {
                        showError()
                    }))
                true
            }
            R.id.action_failed -> {
                subscribe(launchListViewModel.getFaileLaunches()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received LaunchModel $it launches.")
                        showLaunches(it)
                    }, {
                        showError()
                    }))
                true
            }
            R.id.action_default -> {
                subscribe(launchListViewModel.getLaunches()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "Received LaunchModel $it launches.")
                        showLaunches(it)
                    }, {
                        showError()
                    }))
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        })
    }

    fun showLaunches(data: LaunchListData) {
        if (data.error == null) {

            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            val searchAdapter = LaunchListAdapter(activity!!.applicationContext, data.launches)
            searchAdapter.setOnItemClickListener(object : LaunchListAdapter.OnItemClickListener{
                override fun onItemClick(position: Int, id: Int?) {
                    var bundle = Bundle()
                    bundle.putInt("id", id!!)
                    var fragment = DetailFragment()
                    fragment.arguments = bundle
                    openNewFragment(fragment, activity!!.supportFragmentManager)
                }
            })
            lvLaunchList.layoutManager = layoutManager
            lvLaunchList.adapter = searchAdapter

        } else if (data.error is ConnectException || data.error is UnknownHostException) {
            Log.d(TAG,"No connection")
        } else {
            showError()
        }
    }

    fun showError() {
        Toast.makeText(context, "An error occurred :(", Toast.LENGTH_SHORT).show()
    }

    fun openNewFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}