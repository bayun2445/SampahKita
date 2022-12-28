package com.example.sampahkita

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var username: String
    private val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
        "https://sampah-kita-default-rtdb.firebaseio.com/"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonListener()
        loadData()
    }

    private fun loadData() {
        username = intent.getStringExtra("username").toString()
        tv_username.text = username

        databaseReference
            .child("users")
            .child(username)
            .child("balances")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    balance.text = snapshot.child("reward_balance").value.toString()
                    revenue.text = snapshot.child("total_revenue").value.toString()
                    sold.text = snapshot.child("total_sold_kg").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
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