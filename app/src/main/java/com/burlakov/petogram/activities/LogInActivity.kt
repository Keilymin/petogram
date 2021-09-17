package com.burlakov.petogram.activities


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.burlakov.petogram.PetogramApplication
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.ForgotPasswordDialog
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.model.User
import com.burlakov.petogram.presenter.LogInPresenter
import com.burlakov.petogram.utils.EmailValidator
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.utils.UserUtil
import com.burlakov.petogram.view.LogInView
import com.rengwuxian.materialedittext.MaterialEditText


class LogInActivity : AppCompatActivity(), LogInView {

    private lateinit var logInPresenter: LogInPresenter

    private lateinit var singUp: TextView
    private lateinit var forgotPassword: TextView
    private lateinit var logIn: Button

    private lateinit var email: MaterialEditText
    private lateinit var password: MaterialEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        logInPresenter = LogInPresenter(this)

        email = findViewById(R.id.editTextEmailAddress)
        password = findViewById(R.id.editTextPassword)
        forgotPassword = findViewById(R.id.textViewForgotPassword)
        EmailValidator.setValidateMaterialEditView(email, this)

        singUp = findViewById(R.id.textViewSingUp)
        singUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
        forgotPassword.setOnClickListener {
            val dialog = ForgotPasswordDialog()
            dialog.show(supportFragmentManager, "forgot")
        }
        logIn = findViewById(R.id.buttonLogIn)
        logIn.setOnClickListener {
            if (email.validate()) {
                if (password.length() >= 6) {
                    logIn.isEnabled = false
                    logInPresenter.logIn(email.text.toString(), password.text.toString())

                } else showMessage(getString(R.string.password_error_message), false)
            } else showMessage(getString(R.string.email_error_message), false)
        }
        val ms = intent.extras?.getString("message")
        if (ms != null) {
            showMessage(ms, false)
        }

    }

    override fun goToMain(user: User) {
        UserUtil.saveUser(user, this)
        PetogramApplication.user = user

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun resetPassword(email: String) {
        logInPresenter.resetPassword(email)
    }

    override fun enableButton(isPositive: Boolean) {
        logIn.isEnabled = isPositive
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(LocalizeUtil.localize(message, this), isPositive)
        dialog.show(supportFragmentManager, "message")
    }
}