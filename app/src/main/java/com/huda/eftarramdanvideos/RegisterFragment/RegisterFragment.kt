package com.huda.eftarramdanvideos.RegisterFragment

import android.content.Context
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
import com.imagin.myapplication.Models.RegisterRequestModel
import kotlinx.android.synthetic.main.register_fragment.*


class RegisterFragment : Fragment() {
    private lateinit var root: View
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var email: EditText
    private lateinit var passwordEt: EditText
    private lateinit var rePasswordEt: EditText
    private lateinit var name: EditText
    private lateinit var company: EditText
    private lateinit var inputTitle: EditText
    private lateinit var inputNumber: EditText
    private lateinit var emailText: String
    private lateinit var passwordText: String
    private lateinit var rePasswordText: String
    private lateinit var phoneText: String
    private lateinit var companyText: String
    private lateinit var titleText: String
    private lateinit var nameText: String
    private lateinit var FromFragment: String
    private var validName = false
    private var validPhone = false
    private var emailFlag = false
    private var matched = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.register_fragment, container, false)
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FromFragment = arguments?.getString("fromFragment").toString()
        email = root.findViewById(R.id.input_CorporateEmail)
        passwordEt = root.findViewById(R.id.input_password)
        name = root.findViewById(R.id.input_name)
        company = root.findViewById(R.id.input_Company)
        inputTitle = root.findViewById(R.id.input_Title)
        inputNumber = root.findViewById(R.id.input_Number)
        setListeners()
    }

    private fun setListeners() {
        register_back.setOnClickListener {
            findNavController().navigateUp()
        }
        register_login.setOnClickListener {
            checkErrorEnabled()
            hideKeyboard()
            if (registerViewModel.validateDataInfo(
                    emailText
                    , passwordText
                ) && (nameText.isNotEmpty() && companyText.isNotEmpty() &&
                        phoneText.isNotEmpty() && titleText.isNotEmpty() && passwordEt.length() >= 6 &&
                        inputNumber.length() >= 11)
            ) {
                callRegisterRequest()
            }


        }
    }

    private fun callRegisterRequest() {
        progressBar.visibility = View.VISIBLE
        val requestModel = RegisterRequestModel(
            emailText,
            passwordText,
            nameText,
            companyText,
            titleText,
            phoneText
        )
        registerViewModel.register(requestModel)
        registerViewModel.getData().observe(this, Observer {
            progressBar.visibility = View.GONE
            if (it != null) {
                if (it.access_token != "") {
                    Toast.makeText(activity, "Register Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    var error = it.token_type.replace("[", "")
                    error = error.replace("]", "")
                    Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "The email has already been taken", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun checkErrorEnabled() {
        getUserInputData()
        validate()
    }

    private fun isPasswordMatched() {
        matched = passwordText == rePasswordText
    }


    private fun validate() {
        if (!Validation.validate(emailText)) {
            Toast.makeText(activity, "empty Email please fill it", Toast.LENGTH_SHORT).show()
        } else if (!Validation.validateEmail(emailText)) {
            Toast.makeText(
                activity,
                "Invalid Email Format Please enter valid mail",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!Validation.validate(passwordText)) {
            Toast.makeText(activity, "empty password please fill it", Toast.LENGTH_SHORT).show()

        } else if (phoneText.isEmpty()) {
            Toast.makeText(activity, "empty phone please fill it", Toast.LENGTH_SHORT).show()

        } else if (companyText.isEmpty()) {
            Toast.makeText(activity, "empty company please fill it", Toast.LENGTH_SHORT).show()

        } else if (companyText.isEmpty()) {
            Toast.makeText(activity, "empty title please fill it", Toast.LENGTH_SHORT).show()

        } else if (nameText.isEmpty()) {
            Toast.makeText(activity, "empty name please fill it", Toast.LENGTH_SHORT).show()

        } else if (passwordEt.length() < 6) {
            Toast.makeText(activity, "password must be at least 6 characters", Toast.LENGTH_LONG)
                .show()
        } else if (inputNumber.length() < 11) {
            Toast.makeText(activity, "phone must be at least 11 characters", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getUserInputData() {
        emailText = email.text.toString()
        passwordText = passwordEt.text.toString()
        nameText = name.text.toString()
        phoneText = inputNumber.text.toString()
        companyText = company.text.toString()
        titleText = inputTitle.text.toString()

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