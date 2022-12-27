package com.example.sampahkita

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity: AppCompatActivity() {
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
        "https://sampah-kita-default-rtdb.firebaseio.com/"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setButtonListener()
    }

    private fun setButtonListener() {
        tv_to_login.setOnClickListener {
            super.finish()
        }

        btn_signup.setOnClickListener {
            val username = et_sign_username.text.toString()
            val email = et_sign_email.text.toString()
            val password = et_sign_password.text.toString()

            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {

                databaseReference.addListenerForSingleValueEvent (object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(username)){
                            Toast.makeText(this@SignupActivity, "Username tidak tersedia.",Toast.LENGTH_LONG).show()
                        } else {
                            databaseReference.child(username).child("profile").child("password").setValue(password)
                            databaseReference.child(username).child("profile").child("email").setValue(email)

                            databaseReference.child(username).child("balances").child("reward_balance").setValue(0)
                            databaseReference.child(username).child("balances").child("total_revenue").setValue(0)
                            databaseReference.child(username).child("balances").child("total_sold_kg").setValue(0)

                            Toast.makeText(this@SignupActivity, "Sign Up behasil", Toast.LENGTH_LONG).show()

                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            }
            else {
                Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}