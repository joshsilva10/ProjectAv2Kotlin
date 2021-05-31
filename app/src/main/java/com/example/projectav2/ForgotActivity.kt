package com.example.projectav2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {


    private var TAG = "ForgotActivity"

    private var editEmail: EditText? = null
    private var btnSubmit: Button? = null

    private var mAuth: FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        inicialize()
    }

    private fun inicialize(){
        editEmail = findViewById(R.id.etEmail) as EditText
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener { sendPasswordEmail() }

    }

    private fun sendPasswordEmail(){
        val email = editEmail?.text.toString()

        if(!TextUtils.isEmpty(email)){
            mAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            val mensagem = "E-mail enviado."
                            Log.d(TAG, mensagem)
                            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                            updateUI()
                        }else{
                            Log.w(TAG, task.exception!!)
                            Toast.makeText(this, "Usuario Invalido", Toast.LENGTH_SHORT).show()


                        }

                    }

        }else{
            Toast.makeText(this, "Entre com um E-mail Valido", Toast.LENGTH_SHORT).show()

        }

    }

    private fun updateUI(){
        val intent = Intent(this@ForgotActivity,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }
}