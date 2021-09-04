package com.burlakov.petogram.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.presenter.LogInPresenter
import com.burlakov.petogram.presenter.SingUpPresenter
import com.burlakov.petogram.utils.EmailValidator
import com.burlakov.petogram.view.SingInView
import com.rengwuxian.materialedittext.MaterialEditText
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class LogInActivity : MvpAppCompatActivity(), SingInView {

    private val logInPresenter by moxyPresenter { LogInPresenter() }

    private lateinit var singUp: TextView
    private lateinit var logIn: Button

    private lateinit var email: MaterialEditText
    private lateinit var password: MaterialEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.editTextEmailAddress)
        password = findViewById(R.id.editTextPassword)
        EmailValidator.setValidateMaterialEditView(email, this)

        singUp = findViewById(R.id.textViewSingUp)
        singUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
        logIn = findViewById(R.id.buttonLogIn)
        logIn.setOnClickListener {
            if (email.validate()) {
                if (password.length() >= 6) {
                    logInPresenter.logIn(email.text.toString(), password.text.toString())

                } else showMessage(getString(R.string.password_error_message), false)
            } else showMessage(getString(R.string.email_error_message), false)
        }

    }

    override fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun enableButton(isPositive: Boolean) {
        logIn.isEnabled = isPositive
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(message, isPositive)
        dialog.show(supportFragmentManager, "message")
    }
}