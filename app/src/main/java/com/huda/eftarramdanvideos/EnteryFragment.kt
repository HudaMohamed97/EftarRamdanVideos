package com.huda.eftarramdanvideos

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.elarning_videos_frament.*
import kotlinx.android.synthetic.main.home_fragment.back_button


class EnteryFragment : Fragment() {
    private lateinit var root: View
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.elarning_videos_frament, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        showViews()
    }

    private fun setListeners() {

        back_button.setOnClickListener {
            activity!!.moveTaskToBack(true)
            activity!!.finish()
        }

        Videos_button.setOnClickListener {
            hideViews()
            findNavController().navigate(R.id.action_entery_to_Videos_ListFragment)

        }
        e_learning_button.setOnClickListener {
            findNavController().navigate(R.id.action_entery_to_elearning)

        }

    }

    private fun showViews() {
        Videos_button.visibility = View.VISIBLE
        e_learning_button.visibility = View.VISIBLE
    }


    private fun hideViews() {
        Videos_button.visibility = View.GONE
        e_learning_button.visibility = View.GONE

    }


}