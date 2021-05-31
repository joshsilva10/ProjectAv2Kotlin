package com.example.projectav2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_conta.*
import kotlinx.android.synthetic.main.nav_header_main.*

class ContaActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private var userId:String? =null

    private var TAG = "ContaActivity"
    private var nome : String? = null
    private var email : String? = null
    private var cep : String? = null
    private var telefone : String? = null
    private var endereco : String? = null
    private var numeroRes : String? = null
    private var complemento : String? = null
    private var drawer_layout1:DrawerLayout? = null

    //---------

    private var mDatabaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    var storage : FirebaseStorage? = null
    var storageReference:StorageReference? = null

    private var imaget:Uri? =null
    private var imaget1:Bitmap? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)

        userId = intent.getStringExtra("userid")



        var nameacc : TextView = findViewById(R.id.tv_nomeconta)
        var adress : TextView = findViewById(R.id.tv_ender)
        var comp   : TextView = findViewById(R.id.Complemento)
        var num : TextView = findViewById(R.id.tv_number)
        var cep : TextView = findViewById(R.id.cep)
        var tel : TextView = findViewById(R.id.telephone)


        initialize()

         drawer_layout1 = findViewById<DrawerLayout>(R.id.drawer_layout_conta)
        var img = findViewById<ImageView>(R.id.logo_principal)

        img.setOnClickListener {
            drawer_layout1!!.open()
        }
        nav_view.setNavigationItemSelectedListener(this)

        //---------PEGANDO DADOS DO BANCO--------------\
        var currentUserDb = mDatabaseReference!!.child(userId!!)
        currentUserDb.child("Name").get().addOnSuccessListener {
            tv_nomeprincipal.text = it.value.toString()
            tv_emailprincipal1.text = mAuth?.currentUser?.email.toString()
        }
        //var currentImageDb = storageReference!!.child(userId!!)
        currentUserDb.child("Name").get().addOnSuccessListener {
            nameacc.text = getString(R.string.name)+": "+it.value.toString()
        }
        currentUserDb.child("Endereco").get().addOnSuccessListener {
            adress.text = getString(R.string.endereco_hint)+": "+it.value.toString()

        }
        currentUserDb.child("Complemento").get().addOnSuccessListener {
            comp.text = getString(R.string.complemento_hint)+": "+it.value.toString()

        }
        currentUserDb.child("Numero").get().addOnSuccessListener {
            num.text = getString(R.string.numero_hint)+": "+it.value.toString()

        }
        currentUserDb.child("CEP").get().addOnSuccessListener {
            cep.text = getString(R.string.cep_hint)+": "+it.value.toString()

        }
        currentUserDb.child("Telefone").get().addOnSuccessListener {
            tel.text = getString(R.string.cep_hint)+": "+it.value.toString()

        }

        btn_camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,10)
        }




        btn_galery.setOnClickListener {
            /*val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(Intent.createChooser(intent,"Escolha uma Imagem"), 11)*/

            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type= "image/*"
                startActivityForResult(it,11)
            }
        }

        btn_confirmphoto.setOnClickListener {

            //val trans: ByteArrayOutputStream? = null

           // currentImageDb.child("ImgUser").putFile(imaget!!)

        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 11){
                if (data != null){
                    val uri = data.data
                    imaget = uri
                    img_conta.setImageURI(uri)
                }
            }else if (requestCode == 10){
                    if (data != null){
                         val uri = data?.extras?.get("data") as Bitmap
                         imaget1 = uri
                         img_conta.setImageBitmap(uri)
                }

        }

        }




    }

    private fun initialize(){
        mDataBase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDataBase?.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference!!.child("ImageUser")





    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_Account -> {
                drawer_layout1!!.close()

                true
            }
            R.id.nav_history -> {
                Toast.makeText(this,"History", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.nav_option ->{
                Toast.makeText(this,"Options", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.nav_about ->{
                Toast.makeText(this,"About", Toast.LENGTH_SHORT).show()

                true
            }
            R.id.nav_exit ->{

                intent=Intent(this,InitialActivity2::class.java)
                startActivity(intent)
                finishAffinity()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}