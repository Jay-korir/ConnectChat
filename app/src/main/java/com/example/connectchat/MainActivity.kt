package com.example.connectchat


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.connectchat.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button = findViewById<Button>(R.id.registerButton)
        button.setOnClickListener {

            performRegister()


        }

        val ahAccount = findViewById<TextView>(R.id.haveAccount)
        ahAccount.setOnClickListener {
            Log.d("MainActivity", "Try to show login activity")

            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

    }

      private fun performRegister() {

          val email = findViewById<EditText>(R.id.editTextTextEmailAddress)

          val em: String = email.text.toString()
          val password = findViewById<EditText>(R.id.editTextTextPassword)
          val pass: String = password.text.toString()

          //TO CHECK IF USER REGISTeRS WITH A BLANK STILL NOT FUNCTIONAL
          if (email.equals("") || password.equals("")) {
              Toast.makeText(this, "Please enter the text email/pwd", Toast.LENGTH_SHORT).show()
              return
          }

          Log.d("MainActivity", "Email is : $em")
          Log.d("MainActivity", "Password: $pass")

          FirebaseAuth.getInstance().createUserWithEmailAndPassword(em, pass)

              .addOnCompleteListener {
                  if (!it.isSuccessful) {
                      // Sign in success, update UI with the signed-in user's information
                      Log.d(TAG, "createUserWithEmail:success")
                      Toast.makeText(
                          baseContext, "Successfully registered",
                          Toast.LENGTH_SHORT
                      ).show()
                      return@addOnCompleteListener



                  } else {
                      // If sign in fails, display a message to the user.
                      Log.w(TAG, "createUserWithEmail:failure", it.exception)
                      Toast.makeText(
                          baseContext, "Authentication failed.",
                          Toast.LENGTH_SHORT
                      ).show()

                  }

              }
          saveUserToFirebaseDatabase()
      }

//code to save user to database
    private fun saveUserToFirebaseDatabase() {
    val uid = FirebaseAuth.getInstance().uid ?: ""
      val ref=  FirebaseDatabase.getInstance().getReference("users/$uid")

    val username = findViewById<EditText>(R.id.editUserName)
  val user = User(uid, username.text.toString())
   ref.setValue(user)
       .addOnSuccessListener {
           Log.d("MainActivity","Finally user saved to Firebase Databse")

           val intent = Intent(this, LatestMessagesActivity::class.java)
           intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
           startActivity(intent)

       }
    }


        }

        //user object
        class User(val uid: String, val username: String)



