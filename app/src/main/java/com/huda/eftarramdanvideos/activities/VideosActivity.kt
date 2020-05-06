package com.huda.eftarramdanvideos.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.HttpUtils
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.huda.eftarramdanvideos.Adapters.QuestionsAdapter
import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.Models.QuestionModel
import com.huda.eftarramdanvideos.Models.VideoResponse
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.activity_video.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideosActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val RECOVERY_REQUEST = 1
    private val youTubeView: YouTubePlayerView? = null
    private val list = arrayListOf<QuestionModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var url: String


    private val myPlaybackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onBuffering(arg0: Boolean) {}

        override fun onPaused() {}

        override fun onPlaying() {
            Log.i("hhhh", "Palying")

        }

        override fun onSeekTo(arg0: Int) {}

        override fun onStopped() {}
    }


    private val myPlayerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Log.i("hhhh", "started")
            Toast.makeText(this@VideosActivity, "started", Toast.LENGTH_LONG).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Log.i("hhhh", "ended")
            Toast.makeText(this@VideosActivity, "ended", Toast.LENGTH_LONG).show()

        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            Log.i("hhhh", "error")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_video)
        setClickListener()
        initRecyclerView()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            getQuestions(accessToken)
        }
        if (accessToken != null) {
            getVideos(accessToken)
        }

    }

    private fun setClickListener() {
        recyclerView = findViewById(R.id.questionsRecycler)!!
        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider()?.initialize("AIzaSyD3YZnVeXC-oq28vugcH2hukUaYoTYSB4E", this)
        }
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        p2: Boolean
    ) {
        // val youTubePlayer = player
        player?.setPlayerStateChangeListener(myPlayerStateChangeListener)
        player?.setPlaybackEventListener(myPlaybackEventListener)
        //player?.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT)


        if (!p2) {
            if (url != null) {
                player?.loadVideo("fhWaJi1Hsfo")

            }
            // player?.cueVideo("fhWaJi1Hsfo") // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }


    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getYouTubePlayerProvider(): YouTubePlayerView? {
        return youTubeView
    }

    private fun initRecyclerView() {
        val layoutManager =
            LinearLayoutManager(this@VideosActivity, LinearLayoutManager.VERTICAL, false)
        questionsAdapter = QuestionsAdapter(list)
        questionsRecycler.layoutManager = layoutManager
        questionsRecycler.adapter = questionsAdapter
        questionsAdapter.setOnItemListener(object : QuestionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
//                bundle.putInt("question_id", list[position].id)

            }
        })

    }

    fun getVideos(accessToken: String) {
        Webservice.getInstance().api.getVideos(accessToken)
            .enqueue(object : Callback<VideoResponse> {
                override fun onResponse(
                    call: Call<VideoResponse>, response: Response<VideoResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.data != null) {
                            val videos_list = response.body()?.data
                            url = videos_list?.get(0)?.url.toString()
                            intializevideo()


                        }
                    } else {
                        Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun intializevideo() {
        youtube_view.initialize("AIzaSyD3YZnVeXC-oq28vugcH2hukUaYoTYSB4E", this)

    }


    fun getQuestions(accessToken: String) {
        questionProgressBar.visibility = View.VISIBLE
        Webservice.getInstance().api.getVideoQuestions(accessToken)
            .enqueue(object : Callback<QuestionData> {
                override fun onResponse(
                    call: Call<QuestionData>, response: Response<QuestionData>
                ) {
                    if (response.isSuccessful) {
                        questionProgressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE

                        if (response.body()?.data != null) {
                            val question_list = response.body()?.data
                            list.clear()
                            for (data in question_list!!) {
                                list.add(data)
                            }
                            questionsAdapter.notifyDataSetChanged()
                        }
                    } else {
                        recyclerView.visibility = View.GONE
                        Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                    recyclerView.visibility = View.GONE
                    questionProgressBar.visibility = View.GONE
                    Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                        .show()

                }
            })
    }

}


private class MyPlayerStateChangeListener : YouTubePlayer.PlayerStateChangeListener {
    override fun onAdStarted() {
    }

    override fun onLoading() {
    }

    override fun onVideoStarted() {
        Log.i("hhhh", "started")
    }

    override fun onLoaded(p0: String?) {
    }

    override fun onVideoEnded() {
        Log.i("hhhh", "ended")
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {
        Log.i("hhhh", "error")
    }


}

private class MyPlaybackEventListener : YouTubePlayer.PlaybackEventListener {
    override fun onSeekTo(p0: Int) {
    }

    override fun onBuffering(p0: Boolean) {
    }

    override fun onPlaying() {
    }

    override fun onStopped() {
        Log.i("hhhh", "stopped")

    }

    override fun onPaused() {
    }


}
