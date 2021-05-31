//Logica da criação de um novo usuario



package com.example.projectav2

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    //Elementos da UI
    public var userConta:String? = null


    private var editTextNome : EditText? = null
    private var editTextEmail : EditText? = null
    private var editTextCep : EditText? = null
    private  var editTextTelefone : EditText? = null
    private var editTextEndereco : EditText? = null
    private var editTextNumeroRes : EditText? = null
    private var editTextComplemento : EditText? = null
    private var editTextPassword : EditText? = null
    private var editTextPasswordConfirm : EditText? = null
    private var btnConfirme : Button? = null
    private  var mProgressBar: ProgressDialog? = null

    //referencias ao Banco

    private var mDatabaseReference: DatabaseReference? = null
    private var mDataBase:FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "RegisterActivity"

    //variaveis globais
    private var nome : String? = null
    private var email : String? = null
    private var cep : String? = null
    private var telefone : String? = null
    private var endereco : String? = null
    private var numeroRes : String? = null
    private var complemento : String? = null
    private var password : String? = null
    private var passwordConfirm : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialize()
    }

    private fun initialize(){
        editTextNome = findViewById(R.id.editTextNome) as EditText
        editTextEmail = findViewById(R.id.editTextEmail) as EditText
        editTextCep = findViewById(R.id.editTextCEP) as EditText
        editTextTelefone = findViewById(R.id.editTextTelefone) as EditText
        editTextEndereco = findViewById(R.id.editTextEndereco) as EditText
        editTextNumeroRes = findViewById(R.id.editTextNumeroRes) as EditText
        editTextComplemento = findViewById(R.id.editTextComplemento) as EditText
        editTextPassword = findViewById(R.id.editTextPassword) as EditText
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm) as EditText
        btnConfirme = findViewById(R.id.bt_ConfirmarCad) as Button
        mProgressBar = ProgressDialog(this)

        mDataBase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDataBase?.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnConfirme!!.setOnClickListener { createNewAccount() }


    }

    private fun createNewAccount(){
        nome = editTextNome?.text.toString()
        email = editTextEmail?.text.toString()
        telefone=editTextTelefone?.text.toString()
        cep = editTextCep?.text.toString()
        endereco = editTextEndereco?.text.toString()
        numeroRes = editTextNumeroRes?.text.toString()
        complemento = editTextComplemento?.text.toString()
        password = editTextPassword?.text.toString()
        passwordConfirm = editTextPasswordConfirm?.text.toString()

        if(TextUtils.isEmpty(nome)){
            Toast.makeText(this, "Nome não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(telefone)){
            Toast.makeText(this, "Cep não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(cep)){
            Toast.makeText(this, "Endereço não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(endereco)){
            Toast.makeText(this, "Telefone não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(numeroRes)){
            Toast.makeText(this, "Numero não preenchido", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Senha não preenchida", Toast.LENGTH_SHORT).show()

        }else if(TextUtils.isEmpty(passwordConfirm)){
            Toast.makeText(this, "Senha não preenchida", Toast.LENGTH_SHORT).show()

        }else if(password == passwordConfirm) {
            if(password!!.length>6) {
                Toast.makeText(this, "dados confirmados", Toast.LENGTH_SHORT).show()
                mProgressBar?.setMessage("Registrando Usuario")
                mProgressBar?.show()
                mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        mProgressBar!!.hide()
                        if (task.isSuccessful) {
                            Log.d(TAG, "CreateUserWhithEmail:Sucess")

                            val userId = mAuth!!.currentUser!!.uid
                            userConta = userId.toString()
                            // verificar email
                            verifyEmail()
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("Name").setValue(nome)
                            currentUserDb.child("CEP").setValue(cep)
                            currentUserDb.child("Endereco").setValue(endereco)
                            currentUserDb.child("Numero").setValue(numeroRes)
                            currentUserDb.child("Complemento").setValue(complemento)
                            currentUserDb.child("Telefone").setValue(telefone)


                            //atualizar banco
                            updateUserInfoanUi()
                        } else {
                            Log.w(TAG, "CreateUserWhithEmail:Failure", task.exception)
                            Toast.makeText(
                                this@RegisterActivity,
                                "A autenticação falhou.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }else{
                Toast.makeText(this, "Senhas com menos de 6 caracteres", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this, "Senhas não conferem", Toast.LENGTH_SHORT).show()
        }


    }
    private fun updateUserInfoanUi(){
        //Iniciar nova atividade

        val intent = Intent (this@RegisterActivity, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification().addOnCompleteListener(this){
            task ->
            if(task.isSuccessful){
                Toast.makeText(this@RegisterActivity,"Verification email sent to" + mUser.getEmail(),
                Toast.LENGTH_SHORT).show()
            }else{
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@RegisterActivity, "Failed to Send Verification Email",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }


}