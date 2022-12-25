package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_redeem.*

class RedeemActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        setDropDownMenu()
        setButtonListener()
    }

    private fun setButtonListener() {
        btn_back.setOnClickListener{
            super.finish()
        }
    }

    private fun setDropDownMenu() {
        ArrayAdapter.createFromResource(
            this,
            R.array.e_wallet,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_ewallet.adapter = adapter
        }
    }
}