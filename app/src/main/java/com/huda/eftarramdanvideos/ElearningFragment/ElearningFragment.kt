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
import kotlinx.android.synthetic.main.activity_video.questionProgressBar
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
                "A introductory-level, scenario-based course, that uses Lenovo’s" + " SMB market segmentation research to show how to align our products, services and accessories with SMB customers",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mC4wQAE&scormUrl=https://dcgedownload.lenovo.com/lgpe/OPSW113/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "For the first time in over 15 years, Lenovo is introducing a new brand to the PC community. As the workplace transforms, we are seeing more millennials and generation Z joining the workforce. In this course, you will learn about the features and benefits of the ThinkBook Portfolio that are designed specifically to meet the needs of the generations that make up nearly 60% of the new workforce",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mCEbQAM&scormUrl=https://dcgedownload.lenovo.com/lgpe/STBW001/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "The Workstation P Series portfolio provides an advanced level of performance with a variety of form factors to transform productivity and meet the consumers’ demanding workflows. This course will cover both ThinkPad and ThinkStation products",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mCTXQA2&scormUrl=https://dcgedownload.lenovo.com/lgpe/PTSW120/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "With the threat of security breaches constantly looming in the business world, IT departments are looking for a trusted supplier that will help keep their business secure. Lenovo has branded our security offerings as ThinkShield, which is the most comprehensive end-to-end security approach in the PC market. In this introductory course, we will explore what ThinkShield is and learn about the four categories that fall under our end-to-end security approach",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQEeKOvBQOpDEk8eqISsAbgk6eSdgV5mk7aK2xRAGnLjtxknexe5mGtUifX_Jn7KMezCXkqcUnVVr0pLniTnm_EXmql01&myCourseId=a2r0X000002mIfYQAU&scormUrl=https://dcgedownload.lenovo.com/lgpe/SSCW001/EEN/CourseFiles/index_lms.html"
            )
        )
        listLinks.add(
            LinkModel(
                "In this course, you will learn the basics of IoT and how Lenovo’s secure edge gateway is ahead of the curve when it comes to IoT devices. This course specifically covers the Nano IoT device and how it can provide a solution for multiple vertaicals",
                "https://dcgedownload.lenovo.com/lgpe/SCORMParentPage.html?instanceUrl=https://lenovoemea.my.salesforce.com&sessionId=00Dd0000000dz2o!AR4AQIq1gCfnb.i44gHJ9fwhqKOLjLlZNfxtystRbl3eoR9H8bt1.F6Ryrkvm2gfF1bcP8aM_mLZKEs6jwiXgN0wGQCcN9zq&myCourseId=a2r0X000002mIfxQAE&scormUrl=https://dcgedownload.lenovo.com/lgpe/STCW051/EEN/CourseFiles/index_lms.html"
            )
        )
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