package com.huda.eftarramdanvideos.ElearningFragment

import android.content.Context
import android.content.SharedPreferences
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
import com.huda.eftarramdanvideos.Adapters.ElarningQuestionsAdapter
import com.huda.eftarramdanvideos.Models.QuestionModel
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.home_fragment.*


class ElearningFragment : Fragment() {
    private lateinit var root: View
    private lateinit var elarningViewModel: ElearningViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val list = arrayListOf<QuestionModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var linksRecycler: RecyclerView
    private lateinit var questionsAdapter: ElarningQuestionsAdapter
    private lateinit var questionsAdapter1: ElarningQuestionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.elarning_frament, container, false)
        elarningViewModel = ViewModelProviders.of(this).get(ElearningViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initRecyclerView()
        callElearningQuestions()
    }

    private fun setListeners() {
        recyclerView = root.findViewById(R.id.questionsRecycler)!!
        linksRecycler = root.findViewById(R.id.linksRecycler)!!
        loginPreferences = activity!!.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        back_button.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val layoutManager1 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        questionsAdapter = ElarningQuestionsAdapter(list)
        questionsAdapter1 = ElarningQuestionsAdapter(list)
        questionsRecycler.layoutManager = layoutManager
        questionsRecycler.adapter = questionsAdapter
        /*linksRecycler.layoutManager = layoutManager1
        linksRecycler.adapter = questionsAdapter1*/
        questionsAdapter.setOnItemListener(object : ElarningQuestionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putInt("questionId", list[position].id)
                findNavController().navigate(R.id.action_Elearning_to_Answer, bundle)

            }
        })

    }

    private fun callElearningQuestions() {
        questionProgressBar.visibility = View.VISIBLE
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            elarningViewModel.getElarningQuestion(accessToken)
        }
        elarningViewModel.getData().observe(this, Observer {
            questionProgressBar.visibility = View.GONE
            if (it != null) {
                list.clear()
                for (data in it.data) {
                    list.add(data)
                }
                questionsAdapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}