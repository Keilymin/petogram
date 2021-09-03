package com.burlakov.petogram.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.burlakov.petogram.R

class LogInActivity : AppCompatActivity() {
    private lateinit var singUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        singUp = findViewById(R.id.textViewSingUp)
        singUp.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        })
    }
}