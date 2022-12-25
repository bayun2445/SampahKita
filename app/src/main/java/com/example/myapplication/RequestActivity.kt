package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_request.btn_request

class RequestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        setButtonListener()
    }

    private fun setButtonListener() {
        btn_back.setOnClickListener {
            super.finish()
        }
        btn_request.setOnClickListener {
            Toast.makeText(this, "Try Listener", Toast.LENGTH_LONG)
                .show()
        }
    }
}