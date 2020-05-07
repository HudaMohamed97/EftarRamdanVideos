package com.huda.eftarramdanvideos.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.huda.eftarramdanvideos.Adapters.QuestionsAdapter
import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.Models.QuestionModel
import com.huda.eftarramdanvideos.Models.SubmitModel
import com.huda.eftarramdanvideos.Models.VideoResponse
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.activity_video.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class VideosActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val RECOVERY_REQUEST = 1
    private val youTubeView: YouTubePlayerView? = null
    private val list = arrayListOf<QuestionModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsAdapter: QuestionsAdapter
    private lateinit var loginPreferences: SharedPreferences
    private lateinit var url: String
    private var videoId: Int = -1


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
            val accessToken = loginPreferences.getString("accessToken", "")
            if (accessToken != null && videoId != -1) {
                submitStartVideo(videoId, accessToken)
            }
            Toast.makeText(this@VideosActivity, "started", Toast.LENGTH_LONG).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Log.i("hhhh", "ended")
            val accessToken = loginPreferences.getString("accessToken", "")
            if (accessToken != null && videoId != -1) {
                submitEndVideo(videoId, accessToken)
            }
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
        /*if (accessToken != null) {
            getQuestions(accessToken)
        }*/
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
                val videoId = getYouTubeId(url)
                player?.loadVideo(videoId)

            }
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
                val i = Intent(this@VideosActivity, QuestionActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("question_id", list[position].id)
                i.putExtras(bundle)
                startActivity(i)

            }
        })

    }

    private fun getYouTubeId(youTubeUrl: String): String {
        val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group()
        } else {
            "error"
        }
    }


    fun getVideos(accessToken: String) {
        Webservice.getInstance().api.getVideos(accessToken)
            .enqueue(object : Callback<VideoResponse> {
                override fun onResponse(
                    call: Call<VideoResponse>, response: Response<VideoResponse>
                ) {
                    if (response.isSuccessful) {
                        questionProgressBar.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        if (response.body()?.data != null) {
                            val videos_list = response.body()?.data
                            url = videos_list?.url.toString()
                            videoId = videos_list?.id!!
                            initializeVideo()
                            val question_list = videos_list.questions
                            list.clear()
                            for (data in question_list) {
                                list.add(data)
                            }
                            questionsAdapter.notifyDataSetChanged()

                        }
                    } else {
                        questionProgressBar.visibility = View.GONE
                        Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    questionProgressBar.visibility = View.GONE
                    Toast.makeText(this@VideosActivity, "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun initializeVideo() {
        youtube_view.initialize("AIzaSyD3YZnVeXC-oq28vugcH2hukUaYoTYSB4E", this)

    }

    fun submitStartVideo(
        videoId: Int,
        accessToken: String
    ) {
        Webservice.getInstance().api.startVideos(videoId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        Log.i("hhhhh", "startVideoSuccess")

                    } else {
                        Log.i("hhhhh", "startVideoFailed")

                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                }
            })
        return
    }


    fun submitEndVideo(
        videoId: Int,
        accessToken: String
    ) {
        Webservice.getInstance().api.endVideos(videoId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        Log.i("hhhhh", "endVideoSuccess")
                    } else {
                        Log.i("hhhhh", "endVideoFailed")

                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                }
            })
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
