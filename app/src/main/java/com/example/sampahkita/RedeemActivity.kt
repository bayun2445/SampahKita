package com.example.sampahkita

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_redeem.*
import java.time.LocalDateTime

class RedeemActivity: AppCompatActivity() {
    private val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
        "https://sampah-kita-default-rtdb.firebaseio.com/"
    )
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redeem)

        loadData()
        setDropDownMenu()
        setButtonListener()
    }

    private fun loadData() {
        username = intent.getStringExtra("username").toString()
        databaseReference
            .child("users")
            .child(username)
            .child("balances")
            .child("reward_balance")
            .addListenerForSingleValueEvent(
                object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        tv_balance.text = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                }
            )
    }

    private fun setButtonListener() {
        btn_back.setOnClickListener{
            super.finish()
        }

        btn_redeem.setOnClickListener {
            val provider = spinner_ewallet.selectedItem.toString()
            val account = et_ewallet_account.text.toString()
            val nominal = et_redeem_nominal.text.toString().replace(".","")

            if (account.isNotBlank() && nominal.isNotBlank()) {
                if (nominal.toInt() >= 50000
                    &&
                    tv_balance.text.toString().toInt() >= nominal.toInt()
                ){
                    val updatedBalance = tv_balance.text.toString().toInt() - nominal.toInt()
                    databaseReference
                        .child("users")
                        .child(username)
                        .child("balances")
                        .child("reward_balance")
                        .setValue(updatedBalance)

                    postRedeemData(provider, account, nominal)

                } else {
                    Toast.makeText(this, "Nominal/Saldo tidak cukup.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Data belum lengkap!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun postRedeemData(provider: String, account: String, nominal: String) {
        val redeemId = generateID()
        val date = LocalDateTime.now().toString()

        val newRedeemPath = databaseReference
            .child("users")
            .child(username)
            .child("redeem_history")
            .child(redeemId)

        newRedeemPath.child("datetime").setValue(date)
        newRedeemPath.child("provider").setValue(provider)
        newRedeemPath.child("account").setValue(account)
        newRedeemPath.child("nominal").setValue(nominal)

    }

    private fun generateID(): String {
        val source = "0123456789"
        return (1..7)
            .map{ source.random() }
            .joinToString("")
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