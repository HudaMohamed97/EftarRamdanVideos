package com.huda.eftarramdanvideos.ParticapationFragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.eftarramdanvideos.R
import com.imagin.myapplication.LoginFragment.RegisterViewModel
import kotlinx.android.synthetic.main.pdf_fragment.*


class HowToParticapateFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.pdf_fragment, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        back_button.setOnClickListener {
            findNavController().navigateUp()
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textPdf.text = Html.fromHtml(
                "<p>1- First you will receive an email invitation accompanied by links to our program website and mobile app.</p>\n" +
                        "<br>\n" +
                        "<p>2- Once you receive the invitation, register on program website and Follow the link in the email to download the app. (IOS/Android)</p>\n" +
                        "<br>\n" +
                        "<p> 3- Welcome to our program platforms! Know more about the program and our scoring system, by checking (HOW TO PARTICIPATE) tabs.</p>\n" +
                        "<br>\n" +
                        "<p>4- During the program, you&rsquo;ll have to complete; <br>\n" +
                        "- Four 45-minute videos, each video will be followed by 5 quiz questions that must be answered. <br>\n  - Five E-learning sessions, which will be followed by five 5 engaging activities.</p>\n <br>\n" +
                        "<br>\n"+
                        "<p>5- To get the highest score, you must complete all the 5 E-Learning sessions and fully-watch the 4 videos. <br>\n &ndash; Remember the more you score, the bigger your prize will be.</p>\n" +
                        "<br>\n" +
                        "<p>6- Scoring System: </p>\n &bull; Registration = 50 pts <br>\n " +
                        " &bull; Completing a video = 100 Pts &ndash; Answering the question after each video = 20 Pts " + " <br>\n" +
                        " &bull; Completing an E-learning session = 100 Pts &ndash; Answering the question after each session = 20 Pts <br>\n " +
                        " &bull; The 1st place will win an X1 carbon laptop  &bull; Each participant will eventually receive an E-voucher depending on his score &#8230;. So shape up and get Ready, Set, SCORE!</p>\n  <br>\n" +
                        "<br>\n" +
                        "<p> 7- At the end of the program, a detailed report on each user&rsquo;s score and activities during the entire time will be announced to determine the winner.</p>\n" +
                        "<br>\n" +
                        "<p></p>", Html.FROM_HTML_MODE_COMPACT
            );
        } else {
            textPdf.text = Html.fromHtml(
                "<p>1- First you will receive an email invitation accompanied by links to our program website and mobile app.</p>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p>2- Once you receive the invitation, register on program website and Follow the link in the email to download the app. (IOS/Android)</p>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p> 3- Welcome to our program platforms! Know more about the program and our scoring system, by checking (HOW TO PARTICIPATE) tabs.</p>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p>4- During the program, you&rsquo;ll have to complete; <br>\\n\" +\n" +
                        "                        \"- Four 45-minute videos, each video will be followed by 5 quiz questions that must be answered. <br>\\n  - Five E-learning sessions, which will be followed by five 5 engaging activities.</p>\\n <br>\\n\" +\n" +
                        "                        \"<br>\\n\"+\n" +
                        "                        \"<p>5- To get the highest score, you must complete all the 5 E-Learning sessions and fully-watch the 4 videos. <br>\\n &ndash; Remember the more you score, the bigger your prize will be.</p>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p>6- Scoring System: </p>\\n &bull; Registration = 50 pts <br>\\n \" +\n" +
                        "                        \" &bull; Completing a video = 100 Pts &ndash; Answering the question after each video = 20 Pts \" + \" <br>\\n\" +\n" +
                        "                        \" &bull; Completing an E-learning session = 100 Pts &ndash; Answering the question after each session = 20 Pts <br>\\n \" +\n" +
                        "                        \" &bull; The 1st place will win an X1 carbon laptop  &bull; Each participant will eventually receive an E-voucher depending on his score &#8230;. So shape up and get Ready, Set, SCORE!</p>\\n  <br>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p> 7- At the end of the program, a detailed report on each user&rsquo;s score and activities during the entire time will be announced to determine the winner.</p>\\n\" +\n" +
                        "                        \"<br>\\n\" +\n" +
                        "                        \"<p></p>\""
            )
        }


    }


}