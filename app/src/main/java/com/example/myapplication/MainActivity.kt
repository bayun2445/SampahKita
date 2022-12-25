package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonListener()
        loadData()
    }

    private fun loadData() {
        val username = intent.getStringExtra("username")

        tv_username.text = username
    }

    private fun setButtonListener() {
        btn_request.setOnClickListener {
            Intent(this@MainActivity, RequestActivity::class.java).also{
                startActivity(it)
            }
        }

        btn_redeem.setOnClickListener {
            Intent(this@MainActivity, RedeemActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}