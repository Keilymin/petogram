package com.burlakov.petogram.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.presenter.SingUpPresenter
import com.burlakov.petogram.utils.EmailValidator
import com.burlakov.petogram.utils.LocalizeUtil
import com.burlakov.petogram.view.SingUpView
import com.rengwuxian.materialedittext.MaterialEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter


class SingUpActivity : MvpAppCompatActivity(), SingUpView {

    lateinit var email: MaterialEditText
    lateinit var password: MaterialEditText
    lateinit var singUp: Button

    private val singUpPresenter by moxyPresenter { SingUpPresenter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        email = findViewById(R.id.editTextEmailAddress)
        password = findViewById(R.id.editTextPassword)
        singUp = findViewById(R.id.buttonSingUp)

        EmailValidator.setValidateMaterialEditView(email, this)
        singUp.setOnClickListener {
            if (email.validate()) {
                if (password.length()>=6) {

                    singUp.isEnabled = false
                    singUpPresenter.singUp(email.text.toString(), password.text.toString())

                } else showMessage(getString(R.string.password_error_message), false)
            } else showMessage(getString(R.string.email_error_message), false)
        }
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(LocalizeUtil.localize(message,this), isPositive)
        dialog.show(supportFragmentManager, "message")
    }

    override fun enableButton(isPositive: Boolean) {
        singUp.isEnabled = isPositive
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isEnabled", singUp.isEnabled)
        Log.e("B", singUp.isEnabled.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        singUp.isEnabled = savedInstanceState.getBoolean("isEnabled")
    }
}