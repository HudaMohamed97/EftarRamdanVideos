package com.huda.eftarramdanvideos.ElearningFragment

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
import com.huda.eftarramdanvideos.Adapters.ElarningQuestionsAdapter
import com.huda.eftarramdanvideos.Adapters.ElarningQuestionsLinks
import com.huda.eftarramdanvideos.Models.QuestionModel
import com.huda.eftarramdanvideos.R
import kotlinx.android.synthetic.main.activity_video.questionsRecycler
import kotlinx.android.synthetic.main.elarning_frament.*


class ElearningFragment : Fragment() {
    private lateinit var root: View
    private lateinit var elarningViewModel: ElearningViewModel
    private lateinit var loginPreferences: SharedPreferences
    private val list = arrayListOf<QuestionModel>()
    private val listLinks = arrayListOf<LinkModel>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var linksRecycler: RecyclerView
    private lateinit var questionsAdapter: ElarningQuestionsAdapter
    private lateinit var questionsAdapterLinks: ElarningQuestionsLinks


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
        listLinks.clear()
        initRecyclerLinks()
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val layoutManagerLinks = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        questionsAdapter = ElarningQuestionsAdapter(list)
        questionsAdapterLinks = ElarningQuestionsLinks(listLinks)
        questionsRecycler.layoutManager = layoutManager
        questionsRecycler.adapter = questionsAdapter
        linksRecycler.layoutManager = layoutManagerLinks
        linksRecycler.adapter = questionsAdapterLinks
        questionsAdapter.setOnItemListener(object : ElarningQuestionsAdapter.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putInt("questionId", list[position].id)
                findNavController().navigate(R.id.action_Elearning_to_Answer, bundle)

            }
        })
        questionsAdapterLinks.setOnItemListener(object :
            ElarningQuestionsLinks.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                val bundle = Bundle()
                bundle.putString("url", listLinks[position].link)
                findNavController().navigate(R.id.action_Elearning_to_WebViewFragment, bundle)

            }
        })

    }

    private fun initRecyclerLinks() {
        listLinks.add(
            LinkModel(
                "Introduction to Lenovo Products for SMB",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mC4wQAE&scormUrl=https://dcgedownload.lenovo.com/lgpe/OPSW113/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "ThinkBook Portfolio",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mCEbQAM&scormUrl=https://dcgedownload.lenovo.com/lgpe/STBW001/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "Workstation P Series Portfolio",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mCTXQA2&scormUrl=https://dcgedownload.lenovo.com/lgpe/PTSW120/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "What is ThinkShield?",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mIfYQAU&scormUrl=https://dcgedownload.lenovo.com/lgpe/SSCW001/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "Introduction to the Nano IoT",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQIq1gCfnb.i44gHJ9fwhqKOLjLlZNfxtystRbl3eoR9H8bt1.F6Ryrkvm2gfF1bcP8aM_mLZKEs6jwiXgN0wGQCcN9zq&myCourseId=a2r0X000002mIfxQAE&scormUrl=https://dcgedownload.lenovo.com/lgpe/STCW051/EEN/CourseFiles/index_lms.html"
            )
        )
    }

    private fun showProgress() {
        eQuestionProgressBar.visibility = View.VISIBLE

    }

    private fun hideProgress() {
        eQuestionProgressBar.visibility = View.GONE

    }

    private fun callElearningQuestions() {
        val accessToken = loginPreferences.getString("accessToken", "")
        if (accessToken != null) {
            elarningViewModel.getElarningQuestion(accessToken)
        }
        elarningViewModel.getData().observe(this, Observer {
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