package com.example.spacex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R
import com.example.spacex.repository.model.LaunchModel

class LaunchListAdapter(val mcontext: Context, private val itemList: List<LaunchModel>?) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>(){

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int, id: Int?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return itemList!!.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvFlightNo.text = itemList!!.get(position).flight_number.toString()
        holder.tvMission.text = itemList!!.get(position).mission_name
        holder.tvYear.text = itemList!!.get(position).launch_year
        holder.tvLaunch.text = itemList!!.get(position).launch_success.toString()
        holder.clHolder.setOnClickListener {
            onItemClickListener.onItemClick(position, itemList!!.get(position).flight_number)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)

        return ViewHolder(inflatedView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvFlightNo: TextView = itemView.findViewById(R.id.tv_flight_no) as TextView
        var tvMission: TextView = itemView.findViewById(R.id.tv_mission) as TextView
        var tvYear: TextView = itemView.findViewById(R.id.tv_year) as TextView
        var tvLaunch : TextView = itemView.findViewById(R.id.tv_launch) as TextView
        var clHolder : LinearLayout = itemView.findViewById(R.id.clHolder) as LinearLayout


    }


}