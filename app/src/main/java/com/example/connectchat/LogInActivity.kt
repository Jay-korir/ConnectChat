package com.example.connectchat

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.connectchat.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth

class LogInActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val logBtn = findViewById<Button>(R.id.logButton)
        logBtn.setOnClickListener {
            performLogin()
        }
        val backToRegister = findViewById<TextView>(R.id.BacktoReg)
        backToRegister.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun performLogin() {
        val email = findViewById<EditText>(R.id.logEmailAddress)
        val ema: String = email.text.toString()
        val password = findViewById<EditText>(R.id.logPassword)
        val passs: String = password.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please fill out email/pw.", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(ema, passs)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")

                    val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }


}
