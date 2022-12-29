package com.example.sampahkita

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity: AppCompatActivity() {
    private val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
        "https://sampah-kita-default-rtdb.firebaseio.com/"
    )

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
            val username = intent.getStringExtra("username")
            val address = et_address.text.toString()
            val isOrganic = checkbox_organic.isChecked.toString()
            val isNonOrganic = checkbox_non_organic.isChecked.toString()
            val notes =
                if (et_notes.text.isNotBlank()) {
                    et_notes.text.toString()
                } else {
                    "-"
                }
            val transactionID = generateID() +"_" + username

            if (address.isNotBlank()) {
                databaseReference.child("transactions").child(transactionID).child("address")
                    .setValue(address)
                databaseReference.child("transactions").child(transactionID).child("is_organic")
                    .setValue(isOrganic)
                databaseReference.child("transactions").child(transactionID).child("is_non_organic")
                    .setValue(isNonOrganic)
                databaseReference.child("transactions").child(transactionID).child("notes")
                    .setValue(notes)

                Toast.makeText(this, "Request berhasil!", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this@RequestActivity, "Alamat wajib diisi!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun generateID(): String {
        val source = "0123456789"
        return (1..7)
            .map{ source.random() }
            .joinToString("")
    }
}