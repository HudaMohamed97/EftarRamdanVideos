package com.huda.eftarramdanvideos

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.Adapters.VideosListAdapter
import com.huda.eftarramdanvideos.ElearningFragment.ElearningViewModel
import com.huda.eftarramdanvideos.Models.VideoData
import kotlinx.android.synthetic.main.videos_list_frament.*
import kotlinx.android.synthetic.main.videos_list_frament.back_button


class VideosListFragment : Fragment() {
    private lateinit var root: View
    private lateinit var elarningViewModel: ElearningViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val list = arrayListOf<VideoData>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var videosAdapter: VideosListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.videos_list_frament, container, false)
        elarningViewModel = ViewModelProviders.of(this).get(ElearningViewModel::class.java)
        return root
    }

    override fun onResume() {
        super.onResume()
        showViews()
    }

    private fun showViews() {
        VideosView.visibility = View.VISIBLE
        header.visibility = View.VISIBLE
    }


    private fun hideViews() {
        VideosView.visibility = View.GONE
        header.visibility = View.GONE

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setListeners()
        initRecyclerView()
        elarningViewModel.getIsLoading().observe(this, Observer {
            if (it) {
                showProgress()
            } else {
                hideProgress()
            }
        })
        callVideosQuestions()
    }

    private fun setListeners() {
        recyclerView = root.findViewById(R.id.videosRecycler)!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        back_button.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val layoutManagerLinks = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        videosAdapter = VideosListAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = videosAdapter

        videosAdapter.setOnItemListener(object : VideosListAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putInt("questionId", list[position].id)
                hideViews()
                //  findNavController().navigate(R.id.action_Elearning_to_Answer, bundle)
                findNavController().navigate(R.id.action_entery_to_videos)

            }
        })


    }


    private fun showProgress() {
        videosProgressBar.visibility = View.VISIBLE

    }

    private fun hideProgress() {
        videosProgressBar.visibility = View.GONE

    }

    private fun callVideosQuestions() {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            elarningViewModel.getAllVideos(accessToken)
        }
        elarningViewModel.getVideosData().observe(this, Observer {
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                videosAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}