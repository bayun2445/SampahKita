package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setButtonListener()
    }

    private fun setButtonListener() {
        tv_to_login.setOnClickListener {
            super.finish()
        }
    }
}