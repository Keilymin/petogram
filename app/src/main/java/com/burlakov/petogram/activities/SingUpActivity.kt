package com.burlakov.petogram.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.burlakov.petogram.R
import com.burlakov.petogram.dialogs.MessageDialog
import com.burlakov.petogram.presenter.SingUpPresenter
import com.burlakov.petogram.utils.EmailValidator
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
            enableSingUpButton(false)
            singUpPresenter.singUp("1", "1")
        }
    }

    override fun showMessage(message: String, isPositive: Boolean) {
        val dialog = MessageDialog().newInstance(message, isPositive)
        dialog.show(supportFragmentManager, "message")
    }

    override fun enableSingUpButton(isPositive: Boolean) {
        singUp.isEnabled = isPositive
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean("isEnabled", singUp.isEnabled)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        singUp.isEnabled = savedInstanceState.getBoolean("isEnabled")
    }
}