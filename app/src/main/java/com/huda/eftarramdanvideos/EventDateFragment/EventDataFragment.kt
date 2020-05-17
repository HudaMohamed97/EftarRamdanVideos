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
import kotlinx.android.synthetic.main.activity_video.questionsRecycler
import kotlinx.android.synthetic.main.elarning_frament.back_button
import kotlinx.android.synthetic.main.event_fragment.*
import java.util.concurrent.TimeUnit
import android.content.Intent
import android.net.Uri
import com.huda.eftarramdanvideos.Adapters.AgendaAdapter


class EventDataFragment : Fragment() {
    private lateinit var root: View
    private lateinit var elarningViewModel: EventViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val list = arrayListOf<String>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var agendaAdapter: AgendaAdapter


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
        setCounter()
        initRecyclerView()
        // callElearningQuestions()
    }

    private fun setListeners() {
        recyclerView = root.findViewById(R.id.agendaRecycler)!!


        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        back_button.setOnClickListener {
            findNavController().navigateUp()
        }

        event_button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stackoverflow.com"))
            startActivity(intent)
        }

    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
        list.add("welcom hsjhjsfds")
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
        val duration = 30000 //4   //3 600 000 millisecond per hour
        object : CountDownTimer(duration.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
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


/*
    private fun callElearningQuestions() {
        questionProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            elarningViewModel.getElarningQuestion(accessToken)
        }
        elarningViewModel.getData().observe(this, Observer {
            questionProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                agendaAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
*/


}