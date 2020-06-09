package com.huda.eftarramdanvideos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.Models.QuestionModel
import com.huda.eftarramdanvideos.Models.VideoData
import com.huda.eftarramdanvideos.R


class VideosListAdapter(modelFeedArrayList: List<VideoData>) :
    RecyclerView.Adapter<VideosListAdapter.MyViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener
    private var context: Context? = null


    override fun getItemCount(): Int {
        return questionArrayList.size
    }

    var questionArrayList = modelFeedArrayList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.video_row, parent, false)
        context = parent.context
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val questionModel = questionArrayList[position]
        if (questionArrayList[position].title != null) {
            holder.video_title.text = questionArrayList[position].title
        }
        if (questionArrayList[position].date != null) {
            holder.video_date.text = questionArrayList[position].date

        }

        holder.redirect.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClicked(position)
            }
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var video_title: TextView = itemView.findViewById(R.id.video_title)
        var video_date: TextView = itemView.findViewById(R.id.video_date)
        var redirect: ImageView = itemView.findViewById(R.id.redirect)


    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int)

    }

    fun setOnItemListener(setOnItemListener: OnItemClickListener) {
        this.onItemClickListener = setOnItemListener
    }

}


