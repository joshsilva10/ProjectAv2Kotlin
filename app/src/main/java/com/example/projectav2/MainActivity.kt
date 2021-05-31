@file:Suppress("DEPRECATION")

package com.example.projectav2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private val TAG = "MainActivity"
    private val passa :Bundle? = null
    public var userConta:String? = null

    // V globais

    private var email: String? = null
    private var password:String? = null

    //elementi ui
    private var forgotPassword: TextView? =null
    private var login: EditText? =null
    private var pass: TextView? =null
    private var btnLogin: Button? =null
    private var cadastro: TextView? =null
    private var mProgressBar: ProgressDialog? =null


    //references DB

    private var mAuth: FirebaseAuth? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val cad: TextView = findViewById(R.id.textCadastro)
        //cad.setMovementMethod(LinkMovementMethod.getInstance())

        //Toast.makeText(this,status.toString(),Toast.LENGTH_LONG).show()

        initialize()
    }


    private fun initialize(){
        forgotPassword = findViewById(R.id.textResgateSenha) as TextView
        login = findViewById(R.id.editTextLogin) as EditText
        pass = findViewById(R.id.editTextSenha) as EditText
        btnLogin = findViewById(R.id.btn_acessar) as Button
        cadastro = findViewById(R.id.textCadastro) as TextView
        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        forgotPassword!!
            .setOnClickListener { startActivity(Intent(this@MainActivity, ForgotActivity::class.java)) }


        cadastro!!
            .setOnClickListener { startActivity(Intent(this@MainActivity,RegisterActivity::class.java)) }

        btnLogin!!.setOnClickListener { loginUser() }


    }



    private fun loginUser(){
        email = login?.text.toString()
        password =pass?.text.toString()


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgressBar!!.setMessage("Verificando Usuario")
            mProgressBar!!.show()
            Log.d(TAG, "Login do usuario")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener(this){
                task->
                mProgressBar!!.hide()
                var userId = mAuth!!.currentUser!!.uid

                userConta = userId.toString()
               //Toast.makeText(this,""+userConta,Toast.LENGTH_LONG).show()

                if(task.isSuccessful){
                    Log.d(TAG,"Logado com sucesso")
                    updateUi()
                    login?.setText("")
                    pass?.setText("")
                }else{
                    Log.e(TAG, "Erro no Login", task.exception)
                    Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this@MainActivity,"Email ou Senha não inserido",Toast.LENGTH_SHORT).show()


        }

    }

    private fun updateUi(){
        //passa?.putSerializable("teste",userConta)
        //pass?.toString()
        //passa?.putString("idUser",userConta)
        //Toast.makeText(this,""+passa?.get("teste"),Toast.LENGTH_LONG).show()
        val intent = Intent(this@MainActivity, PrincipalActivity::class.java)
        //intent.putExtras(pass!!)
        intent.putExtra("idUser",userConta)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}