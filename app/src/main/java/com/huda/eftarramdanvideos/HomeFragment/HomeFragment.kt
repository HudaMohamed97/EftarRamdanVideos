package com.huda.eftarramdanvideos.HomeFragment

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.eftarramdanvideos.R
import com.imagin.myapplication.LoginFragment.RegisterViewModel
import kotlinx.android.synthetic.main.home_fragment.*


class HomeFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.home_fragment, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setListeners()
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        callScoreData()
    }

    override fun onResume() {
        super.onResume()
        showViews()
    }

    private fun showViews() {
        howToCard.visibility = View.VISIBLE
        score_card.visibility = View.VISIBLE
        elearning_Card.visibility = View.VISIBLE
        webinarCard.visibility = View.VISIBLE
        event_card.visibility = View.VISIBLE


    }


    private fun hideViews() {
        howToCard.visibility = View.GONE
        elearning_Card.visibility = View.GONE
        webinarCard.visibility = View.GONE
        score_card.visibility = View.GONE
        event_card.visibility = View.GONE

    }

    override fun onStart() {
        super.onStart()
    }

    private fun setListeners() {

        back_button.setOnClickListener {
            activity!!.moveTaskToBack(true)
            activity!!.finish()
        }



        webinarCard.setOnClickListener {
            hideViews()
            findNavController().navigate(R.id.action_Register_to_videos)

        }
        howToCard.setOnClickListener {
            findNavController().navigate(R.id.action_home_howtoFragment)

        }
        elearning_Card.setOnClickListener {
            findNavController().navigate(R.id.action_home_ElearningFragment)

        }
        score_card.setOnClickListener {
            callScoreData()
        }
        event_card.setOnClickListener {
            findNavController().navigate(R.id.action_home_EventFragment)
        }
    }


    private fun callScoreData() {
        scoreProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            registerViewModel.getScore(accessToken)
        }
        registerViewModel.getScoreData().observe(this, Observer {
            scoreProgressBar.visibility = View.GONE
            if (it != null) {
                score.visibility = View.VISIBLE
                score.text = it.data.score.toString()
            } else {
                score.visibility = View.GONE
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}