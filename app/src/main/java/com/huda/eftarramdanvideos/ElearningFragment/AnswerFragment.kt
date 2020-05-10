package com.huda.eftarramdanvideos.ElearningFragment

import Answers
import SingelQuestionData
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.Adapters.QuestionRatingAdapter
import com.huda.eftarramdanvideos.Models.SubmitModel
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.question_fragment.*
import kotlinx.android.synthetic.main.question_fragment.back
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AnswerFragment : Fragment() {
    private lateinit var root: View
    private var questionId: Int = 0
    private var answerId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<Answers>()
    private lateinit var questionAdapter: QuestionRatingAdapter
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.question_fragment, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setClickListener()
        initRecyclerView()
        questionId = arguments?.getInt("questionId")!!
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            getSingelQuestion(accessToken)
        }
    }

    private fun setClickListener() {
        recyclerView = root.findViewById(R.id.answersRecycler)!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        submitFragmentQuestion.setOnClickListener {
            if (answerId == -1) {
                Toast.makeText(
                    activity,
                    "Please Select Answer,Thanks ",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                val accessToken = loginPreferences.getString("accessToken", "")
                if (accessToken != null) {
                    submitAnswer(accessToken)
                }
            }
        }
        logOutButton.setOnClickListener {
            activity!!.finish()
        }
        back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        questionAdapter = QuestionRatingAdapter(list)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = questionAdapter
        questionAdapter.setOnItemListener(object : QuestionRatingAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                answerId = list[position].id
            }
        })
    }

    fun getSingelQuestion(accessToken: String) {
        OptionsFragmentProgressBar.visibility = View.VISIBLE
        Webservice.getInstance().api.getSingelQuestion(questionId, accessToken)
            .enqueue(object : Callback<SingelQuestionData> {
                override fun onResponse(
                    call: Call<SingelQuestionData>, response: Response<SingelQuestionData>
                ) {
                    OptionsFragmentProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (response.body()?.data != null) {
                            questionFragmentTitle.text = data?.title
                            for (answer in data!!.answers!!) {
                                list.add(answer)
                            }
                            questionAdapter.notifyDataSetChanged()


                        }
                    } else {
                        Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<SingelQuestionData>, t: Throwable) {
                    OptionsFragmentProgressBar.visibility = View.GONE
                    Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    fun submitAnswer(
        accessToken: String
    ) {
        val body = mapOf(
            "answer_id" to answerId.toString()
        )
        OptionsFragmentProgressBar.visibility = View.VISIBLE
        Webservice.getInstance().api.submitQuestion(questionId, body, accessToken)
            .enqueue(object : Callback<SubmitModel> {

                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    OptionsFragmentProgressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "Submitted Successfully,Thanks",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (response.code() == 400) {
                            Toast.makeText(
                                activity,
                                "You Already Submit this Question Before",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                activity,
                                "Network Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    OptionsFragmentProgressBar.visibility = View.GONE
                    Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return
    }


}