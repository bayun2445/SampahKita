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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class LoginActivity: AppCompatActivity() {
    private val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(
        "https://sampah-kita-default-rtdb.firebaseio.com/"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setButtonListener()
    }

    private fun setButtonListener() {
        tv_create_account.setOnClickListener {
            Intent(this@LoginActivity, SignupActivity::class.java).also {
                startActivity(it)
            }
        }

        btn_login.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()

            if (username.isNotBlank() && password.isNotBlank()){

                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(username) && snapshot.child(username).child("profile").child("password").value == password) {
                            Intent(this@LoginActivity, MainActivity::class.java).also {
                                it.putExtra("username", username)
                                startActivity(it)
                                finish()
                            }
                        }
                        else {
                            Toast.makeText(this@LoginActivity, "Username salah!", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })

//                //Start ProgressBar first (Set visibility VISIBLE)
//                val handler = Handler(Looper.getMainLooper())
//                handler.post {
//                    //Starting Write and Read data with URL
//                    //Creating array for parameters
//                    val field = arrayOfNulls<String>(2)
//                    field[0] = "username"
//                    field[1] = "password"
//                    //Creating array for data
//                    val data = arrayOfNulls<String>(2)
//                    data[0] = username
//                    data[1] = password
//                    val putData = PutData(
//                        "http://192.168.85.254/LoginRegister/login.php",
//                        "POST",
//                        field,
//                        data
//                    )
//                    if (putData.startPut()) {
//                        if (putData.onComplete()) {
//                            val result = putData.result
//                            if (result.equals("Login Success")){
//                                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_LONG)
//                                    .show()
//
//                                Intent(this@LoginActivity, MainActivity::class.java).also {
//                                    it.putExtra("username", username)
//                                    startActivity(it)
//                                }
//
//                                super.finish()
//                            }
//                            else {
//                                Toast.makeText(this, result, Toast.LENGTH_LONG)
//                                    .show()
//                            }
//                        }
//                    }
//                    //End Write and Read data with URL
//                }
            }
            else {
                Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}