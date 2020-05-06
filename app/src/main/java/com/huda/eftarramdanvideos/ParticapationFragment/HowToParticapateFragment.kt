package com.huda.eftarramdanvideos.ParticapationFragment

import android.os.Bundle
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
        back_button.setOnClickListener {
            findNavController().navigateUp()
        }
        pdf_viewer.fromAsset("Application.pdf").load()
    }


}