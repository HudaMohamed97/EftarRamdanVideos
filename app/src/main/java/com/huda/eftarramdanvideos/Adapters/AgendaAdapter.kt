package com.huda.eftarramdanvideos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.Models.Agenda
import com.huda.eftarramdanvideos.R


class AgendaAdapter(modelFeedArrayList: List<Agenda>) :
    RecyclerView.Adapter<AgendaAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return questionArrayList.size
    }

    var questionArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.aganda_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val questionModel = questionArrayList[position]
        holder.agendatitle.text = questionModel.name
        holder.event_time.text = questionModel.time_from + " - " + questionModel.time_to
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var agendatitle: TextView = itemView.findViewById(R.id.question_title)
        var event_time: TextView = itemView.findViewById(R.id.event_time)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}


