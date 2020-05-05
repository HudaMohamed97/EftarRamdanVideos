package com.huda.eftarramdanvideos.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.activity_video.*

class VideosActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val RECOVERY_REQUEST = 1
    private val youTubeView: YouTubePlayerView? = null


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
        youtube_view.initialize("AIzaSyD3YZnVeXC-oq28vugcH2hukUaYoTYSB4E", this)
        button.setOnClickListener {
            Toast.makeText(this, "please Choose filter Type", Toast.LENGTH_SHORT).show()

        }

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
            // player?.cueVideo("fhWaJi1Hsfo") // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            player?.loadVideo("fhWaJi1Hsfo")
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
