package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vishnusivadas.advanced_httpurlconnection.PutData
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

        btn_signup.setOnClickListener {
            val username = et_sign_username.text.toString()
            val password = et_sign_password.text.toString()
            val email = et_sign_email.text.toString()


            if (username.isNotBlank() && password.isNotBlank() && email.isNotBlank()){
                //Start ProgressBar first (Set visibility VISIBLE)
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    val field = arrayOfNulls<String>(3)
                    field[0] = "username"
                    field[1] = "password"
                    field[2] = "email"
                    //Creating array for data
                    val data = arrayOfNulls<String>(3)
                    data[0] = username
                    data[1] = password
                    data[2] = email
                    val putData = PutData(
                        "http://192.168.122.254/LoginRegister/signup.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            if (result.equals("Sign Up Success")){
                                Toast.makeText(this, "Sign Up Berhasil", Toast.LENGTH_LONG)
                                    .show()

                                Intent(this@SignupActivity, LoginActivity::class.java).also {
                                    startActivity(it)
                                }

                                super.finish()
                            }
                            else {
                                Toast.makeText(this, result, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                    //End Write and Read data with URL
                }
            }
            else {
                Toast.makeText(this, "Data belum lengkap", Toast.LENGTH_LONG)
                    .show()
            }

        }
    }
}