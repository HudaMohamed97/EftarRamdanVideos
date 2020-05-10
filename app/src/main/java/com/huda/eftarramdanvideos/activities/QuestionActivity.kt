package com.huda.eftarramdanvideos.activities

import Answers
import SingelQuestionData
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huda.eftarramdanvideos.Adapters.QuestionRatingAdapter
import com.huda.eftarramdanvideos.Models.SubmitModel
import com.huda.eftarramdanvideos.Models.VideoResponse
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.question_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess

class QuestionActivity : AppCompatActivity() {
    private var questionId: Int = 0
    private var answerId: Int = -1
    private lateinit var recyclerView: RecyclerView
    private val list = arrayListOf<Answers>()
    private lateinit var questionAdapter: QuestionRatingAdapter
    private lateinit var loginPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_activity)
        val bundle = intent.extras
        questionId = bundle!!.getInt("question_id")
        setClickListener()
        initRecyclerView()
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            getSingelQuestion(accessToken)
        }


    }

    private fun setClickListener() {
        recyclerView = findViewById(R.id.answersRecycler)!!
        loginPreferences = this.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        submitQuestion.setOnClickListener {
            if (answerId == -1) {
                Toast.makeText(
                    this@QuestionActivity,
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
            this.finish()
        }
        back.setOnClickListener {
            this.moveTaskToBack(true)
            this.finish()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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
        OptionsProgressBar.visibility = View.VISIBLE
        Webservice.getInstance().api.getSingelQuestion(questionId, accessToken)
            .enqueue(object : Callback<SingelQuestionData> {
                override fun onResponse(
                    call: Call<SingelQuestionData>, response: Response<SingelQuestionData>
                ) {
                    OptionsProgressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (response.body()?.data != null) {
                            questionTitle.text = data?.title
                            for (answer in data!!.answers!!) {
                                list.add(answer)
                            }
                            questionAdapter.notifyDataSetChanged()


                        }
                    } else {
                        Toast.makeText(this@QuestionActivity, "Network Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<SingelQuestionData>, t: Throwable) {
                    OptionsProgressBar.visibility = View.GONE
                    Toast.makeText(this@QuestionActivity, "Network Error", Toast.LENGTH_SHORT)
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
        OptionsProgressBar.visibility = View.VISIBLE
        Webservice.getInstance().api.submitQuestion(questionId, body, accessToken)
            .enqueue(object : Callback<SubmitModel> {

                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    OptionsProgressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@QuestionActivity,
                            "Submitted Successfully,Thanks",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (response.code() == 400) {
                            Toast.makeText(
                                this@QuestionActivity,
                                "You Already Submit this Question Before",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@QuestionActivity,
                                "Network Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    OptionsProgressBar.visibility = View.GONE
                    Toast.makeText(this@QuestionActivity, "Network Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        return
    }

}