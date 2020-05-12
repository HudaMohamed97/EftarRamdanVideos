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
                "<ul>\n" + "\t<p>1- First you will receive an email invitation accompanied by links \n" +
                        "\tto our program website and mobile app. </li>\n" + "<br>\n" +
                        "\t<p>2- Once you receive the invitation, register on program website \n" +
                        "\tand Follow the link in the email to download the app. (IOS/Android)</li>\n" + "<br>\n" +
                        "\t<p> 3- Welcome to our program platforms! Know more about the program \n" +
                        "\tand our scoring system, by checking (HOW TO PARTICIPATE) tabs. </li>\n" + "<br>\n" +
                        "\t<p>4- During the program, you’ll have to complete: <br>\n" +"<br>\n"+
                        "- Four 45-minute videos, each video will be followed by 5 quiz questions \n" +
                        "\tthat must be answered. <br>\n" + "<br>\n" +
                        "- Five E-learning sessions, which will be followed by five 5 engaging \n" +
                        "\tactivities. </li>\n" + "<br>\n" +
                        "\t<p>5- To get the highest score, you must complete all the five" +
                        "\tE-Learning sessions and fully-watch the 4 videos. </li>\n" + "<br>\n" +
                        "\t<p>6- Scoring System: - <br>\n" +
                        "\t. Registration = 50 pts <br>\n" +
                        "\t. Completing a video = 100 Pts – Answering the question after each video = \n" +
                        "\t20 Pts <br>\n" +
                        "\t. Completing an E-learning session = 100 Pts – Answering the question after \n" +
                        "\teach session = 20 Pts <br>\n" +
                        "\t. The 1st place will win an X1 carbon laptop <br>\n" +
                        "\t. Each participant will eventually receive an E-voucher depending on his \n" +
                        "\tscore …. So shape up and get Ready, Set, SCORE! </li>\n" +
                        "\t<br>\n" +
                        "\t<p> 7- At the end of the program, a detailed report on each user’s \n" +
                        "\tscore and activities during the entire time will be announced to determine \n" +
                        "\tthe winner.</li>\n" +
                        "</ul>", Html.FROM_HTML_MODE_COMPACT
            );
        } else {
            textPdf.text = Html.fromHtml(
                "<ul>\n" + "\t<li>\n" + "\t<p>First you will receive an email invitation accompanied by links \n" + "\tto our program website and mobile app. </li>\n" + "\t<li>\n" + "\t<p>Once you receive the invitation, register on program website \n" + "\tand Follow the link in the email to download the app. (IOS/Android)</li>\n" + "\t<li>\n" + "\t<p>Welcome to our program platforms! Know more about the program \n" + "\tand our scoring system, by checking (HOW TO PARTICIPATE) tabs. </li>\n" + "\t<li>\n" +
                        "\t<p>During the program, you’ll have to complete; <br>\n" +
                        "\t- Four 45-minute videos, each video will be followed by 5 quiz questions \n" +
                        "\tthat must be answered. <br>\n" +
                        "\t- Five E-learning sessions, which will be followed by five 5 engaging \n" +
                        "\tactivities. </li>\n" +
                        "\t<li>\n" +
                        "\t<p>To get the highest score, you must complete all the 5 \n" +
                        "\tE-Learning sessions and fully-watch the 4 videos. </li>\n" +
                        "\t<li>\n" +
                        "\t<p>Scoring System: - <br>\n" +
                        "\t- Registration = 50 pts <br>\n" +
                        "\t· Completing a video = 100 Pts – Answering the question after each video = \n" +
                        "\t20 Pts <br>\n" +
                        "\t· Completing an E-learning session = 100 Pts – Answering the question after \n" +
                        "\teach session = 20 Pts <br>\n" +
                        "\t· The 1st place will win an X1 carbon laptop <br>\n" +
                        "\t· Each participant will eventually receive an E-voucher depending on his \n" +
                        "\tscore …. So shape up and get Ready, Set, SCORE! </li>\n" +
                        "\t<li>\n" +
                        "\t<p>At the end of the program, a detailed report on each user’s \n" +
                        "\tscore and activities during the entire time will be announced to determine \n" +
                        "\tthe winner.</li>\n" +
                        "</ul>"
            )
        }


    }


}