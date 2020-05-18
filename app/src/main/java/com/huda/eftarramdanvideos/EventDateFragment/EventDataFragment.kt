package com.huda.eftarramdanvideos.EventDateFragment

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.elarning_frament.back_button
import kotlinx.android.synthetic.main.event_fragment.*
import java.util.concurrent.TimeUnit
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.huda.eftarramdanvideos.Adapters.AgendaAdapter
import com.huda.eftarramdanvideos.Models.Agenda
import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*


class EventDataFragment : Fragment() {
    private lateinit var root: View
    private lateinit var elarningViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val list = arrayListOf<Agenda>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var agendaAdapter: AgendaAdapter
    private lateinit var uri: String
    private var timeRemaining: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.event_fragment, container, false)
        elarningViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setListeners()
        initRecyclerView()
       // getRemainngtime()
        callAgenda()
    }

    private fun setListeners() {
        recyclerView = root.findViewById(R.id.agendaRecycler)!!


        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        back_button.setOnClickListener {
            findNavController().navigateUp()
        }

        event_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        agendaAdapter = AgendaAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = agendaAdapter
        agendaAdapter.setOnItemListener(object : AgendaAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {

            }
        })

    }


    private fun setCounter() {
        event_button.isEnabled = false
        event_button.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        val duration = timeRemaining //4   //3 600 000 millisecond per hour
        object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.i("hhhh", "tick" + millisUntilFinished)
                var millisUntilFinished = millisUntilFinished
                val days = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))
                val hours = (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) -
                        TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)))
                val minute = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)))
                val seconds = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(
                                millisUntilFinished
                            )
                        ))

                if (day != null) {
                    day.text = days.toString()
                }
                if (hour != null) {
                    hour.text = hours.toString()
                }
                if (minutes != null) {
                    minutes.text = minute.toString()
                }
                if (secondsText != null) {
                    secondsText.text = seconds.toString()
                }
            }

            override fun onFinish() {
                event_button.isEnabled = true
                event_button.backgroundTintList = ColorStateList.valueOf(Color.rgb(255, 128, 0))

                if (day != null) {
                    day.text = "0"
                }
                if (hour != null) {
                    hour.text = "0"
                }
                if (minutes != null) {
                    minutes.text = "0"
                }
                if (secondsText != null) {
                    secondsText.text = "0"
                }
            }
        }.start()
    }


    private fun callAgenda() {
        agendaProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            elarningViewModel.getAgenda()
        }
        elarningViewModel.getData().observe(this, Observer {
            agendaProgressBar.visibility = View.GONE
            countDownLayout.visibility = View.VISIBLE
            if (it != null) {
                list.clear()
                uri = it.setting.extra_link
                timeRemaining = it.setting.remaining_time
                setCounter()
                textEventDate.text = "EventDate: " + it.setting.date
                textEventTime.text = "EventTime: " + it.setting.time
                textEventSpeakers.text = "EventSpeaker: " + it.setting.speaker
                for (data in it.agenda) {
                    list.add(data)
                }
                agendaAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("NewApi")
    fun getRemainngtime() {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        formatter.isLenient = false

        val curDate = Date()
        val curMillis = curDate.time
        val curTime = formatter.format(curDate)

        val oldTime = "2020-05-18 10:00"
        val oldDate = formatter.parse(oldTime)
        val oldMillis = oldDate.time

        Log.i("hhhhhhhh", "old" + oldMillis + "cur" + curMillis)
    }

}