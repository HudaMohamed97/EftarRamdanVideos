package com.huda.eftarramdanvideos.ResetPasswordFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.huda.eftarramdanvideos.R
import com.huda.eftarramdanvideos.utilies.Validation
import com.imagin.myapplication.LoginFragment.RegisterViewModel
import kotlinx.android.synthetic.main.reset_fragment.*


class ResetPasswrdFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var email: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.reset_fragment, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        email = root.findViewById(R.id.input_CorporateEmail)

        setListeners()
    }

    private fun setListeners() {
        register_back.setOnClickListener {
            findNavController().navigateUp()
        }
        register_login.setOnClickListener {
            hideKeyboard()
            if (Validation.validateEmail(email.text.toString())) {
                callForgetRequest()
            } else {
                Toast.makeText(
                    activity,
                    "please Enter valid Email,Thanks ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    private fun callForgetRequest() {
        resetProgressBar.visibility = View.VISIBLE
        registerViewModel.resetPass(email.text.toString())
        registerViewModel.getResetPassData().observe(this, Observer {
            resetProgressBar.visibility = View.GONE
            if (it != null) {
                if (it.title == "NoAccount" && it.type == "NoAccount")
                    Toast.makeText(
                        activity,
                        "No Account for this Email",
                        Toast.LENGTH_SHORT
                    ).show()
                else if (it.title == "NotValid" && it.type == "NotValid") {
                    Toast.makeText(
                        activity,
                        "Account must Be Valid Email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(activity, "Reset Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun hideKeyboard() {
        val view = activity?.currentFocus
        if (view != null) {
            val imm =
                context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}